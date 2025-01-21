package com.foro.hub.domain.curso.dto;

import jakarta.validation.constraints.NotBlank;

public record DatosCrearCurso(
        @NotBlank
        String nombre,
        @NotBlank
        String categoria
) {
}
