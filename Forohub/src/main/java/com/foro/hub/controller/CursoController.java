package com.foro.hub.controller;

import com.foro.hub.domain.curso.Curso;
import com.foro.hub.domain.curso.CursoRepositorio;
import com.foro.hub.domain.curso.dto.DatosActualizarCurso;
import com.foro.hub.domain.curso.dto.DatosCrearCurso;
import com.foro.hub.domain.curso.dto.DatosDetallesCurso;
import com.foro.hub.domain.curso.dto.DatosListaCursos;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")

public class CursoController {

    @Autowired
    private CursoRepositorio cursoRepositorio;


    @PostMapping
    @Transactional
    public ResponseEntity crearCurso(@RequestBody @Valid DatosCrearCurso datosCrearCurso, UriComponentsBuilder uriBuilder){

        var curso = new Curso(datosCrearCurso);
        cursoRepositorio.save(curso);

        var uri = uriBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetallesCurso(curso));

    }

    @GetMapping
    public ResponseEntity<Page<DatosListaCursos>> listar(@PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion) {
        var page = cursoRepositorio.findAll(paginacion).map(DatosListaCursos::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional

    public ResponseEntity actualizar(@RequestBody @Valid @PathVariable DatosActualizarCurso datos) {
        var curso = cursoRepositorio.getReferenceById(datos.id());
        curso.actualizarCurso(datos);
        return ResponseEntity.ok(curso);
    }
}

