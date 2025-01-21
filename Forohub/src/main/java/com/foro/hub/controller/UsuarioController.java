package com.foro.hub.controller;

import com.foro.hub.domain.usuario.dto.DatosListaUsuario;
import com.foro.hub.domain.usuario.Usuario;
import com.foro.hub.domain.usuario.UsuarioRepositorio;
import com.foro.hub.domain.usuario.dto.DatosCrearUsuario;
import com.foro.hub.domain.usuario.dto.DatosDetallesUsuario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.SecurityMarker;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
@Table(name= "autor")
@RestController
@RequestMapping("/usuario")

public class UsuarioController {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;


    @PostMapping
    @Transactional
    public ResponseEntity crearUsuario(@RequestBody @Valid DatosCrearUsuario datosCrearUsuario, UriComponentsBuilder uriBuilder){

        var usuario = new Usuario(datosCrearUsuario);
        usuarioRepositorio.save(usuario);

        var uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetallesUsuario(usuario));

    }

    @GetMapping
    public ResponseEntity<Page<DatosListaUsuario>> listar(@PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion) {
        var page = usuarioRepositorio.findAll(paginacion).map(DatosListaUsuario::new);
        return ResponseEntity.ok(page);
    }
}
