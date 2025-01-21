package com.foro.hub.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.foro.hub.domain.curso.CursoRepositorio;
import com.foro.hub.domain.topico.Topico;
import com.foro.hub.domain.topico.TopicoRepositorio;
import com.foro.hub.domain.topico.dto.DatosActualizarTopico;
import com.foro.hub.domain.topico.dto.DatosCrearTopico;
import com.foro.hub.domain.topico.dto.DatosDetallesTopico;
import com.foro.hub.domain.topico.dto.DatosListarTopico;
import com.foro.hub.domain.usuario.UsuarioRepositorio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")

public class TopicoController {

    @Autowired
    private TopicoRepositorio topicoRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private CursoRepositorio cursoRepositorio;


    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetallesTopico> crear(@RequestBody @Valid DatosCrearTopico datos, UriComponentsBuilder uriBuilder){
        var autor = usuarioRepositorio.findById(datos.idautor())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        var curso = cursoRepositorio.findById(datos.idcurso())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        var topico = new Topico(datos, usuarioRepositorio, cursoRepositorio);
        topicoRepositorio.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetallesTopico(topico));


    }

    @GetMapping
    public Page<DatosListarTopico> ListadoTopico(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacion) {
        return topicoRepositorio.findAll(paginacion).map(DatosListarTopico::new);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        var topico = topicoRepositorio.getReferenceById(datosActualizarTopico.id());
        topico.actualizarTopico(datosActualizarTopico);

        return ResponseEntity.ok(new DatosDetallesTopico(topico));
    }

    @PutMapping("/{id}")
    public ResponseEntity actualizarTopico (@PathVariable Long id) {
        var topico = topicoRepositorio.getReferenceById(id);
        return ResponseEntity.ok(new DatosListarTopico(topico));
    }

    @PutMapping("/verificar-y-actualizar/{id}")
    @Transactional
    public ResponseEntity verificarYActualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datos) {
        var optionalTopico = topicoRepositorio.findById(id);

        if (optionalTopico.isPresent()) {
            var topicoExistente = optionalTopico.get();
            topicoExistente.actualizarTopico(datos);
            return ResponseEntity.ok(new DatosDetallesTopico(topicoExistente));
        } else {
            // Si no existe, devolver un mensaje adecuado
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar (@PathVariable Long id) {
        var topico = topicoRepositorio.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetallesTopico(topico));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        topicoRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
