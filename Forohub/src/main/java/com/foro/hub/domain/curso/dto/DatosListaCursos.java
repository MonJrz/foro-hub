package com.foro.hub.domain.curso.dto;

import com.foro.hub.domain.curso.Curso;

public record DatosListaCursos(Long id, String nombre, String categoria) {

    public DatosListaCursos(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria().toString());
    }

}


