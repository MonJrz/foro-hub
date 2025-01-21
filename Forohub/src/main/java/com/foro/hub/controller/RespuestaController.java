package com.foro.hub.controller;


import com.foro.hub.domain.respuesta.Respuesta;
import com.foro.hub.domain.respuesta.RespuestasRepositorio;
import com.foro.hub.domain.respuesta.dto.DatosCrearRespuesta;
import com.foro.hub.domain.respuesta.dto.DatosDetalleRespuesta;
import com.foro.hub.domain.topico.Topico;
import com.foro.hub.domain.topico.TopicoRepositorio;
import com.foro.hub.domain.usuario.Usuario;
import com.foro.hub.domain.usuario.UsuarioRepositorio;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Respuesta", description = "Sólo uno puede ser la solución a el tema.")

public class RespuestaController {
    @Autowired
    private TopicoRepositorio topicoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RespuestasRepositorio respuestasRepositorio;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra una nueva respuesta en la base de datos, vinculada a un usuario y tema existente.")
    public ResponseEntity<DatosDetalleRespuesta> crearRespuesta(@RequestBody @Valid DatosCrearRespuesta datosCrearRespuesta,
                                                                UriComponentsBuilder uriBuilder){

        Usuario usuario = usuarioRepositorio.getReferenceById(datosCrearRespuesta.usuarioId());
        Topico topico = topicoRepositorio.findById(datosCrearRespuesta.topicoId()).get();

        var respuesta = new Respuesta(datosCrearRespuesta, usuario, topico);
        respuestasRepositorio.save(respuesta);

        var uri = uriBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleRespuesta(respuesta));

    }

    @GetMapping("/topico/{topicoId}")
    @Operation(summary = "Lee todas las respuestas del tema dado")
    public ResponseEntity<Page<DatosDetalleRespuesta>>
    leerRespuestaDeTopico(@PageableDefault(size = 5, sort = {"ultimaActualizacion"},
            direction = Sort.Direction.ASC) Pageable pageable, @PathVariable Long topicoId){
        var pagina = respuestasRepositorio.findAllByTopicoId(topicoId, pageable).map(DatosDetalleRespuesta::new);
        return ResponseEntity.ok(pagina);
    }




    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina una respuesta por su Id")
    public ResponseEntity<?> borrarRespuesta(@PathVariable Long id){
        Respuesta respuesta = respuestasRepositorio.getReferenceById(id);
        respuesta.eliminarRespuesta();
        return ResponseEntity.noContent().build();
    }
}
