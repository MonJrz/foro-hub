package com.foro.hub.domain.curso.dto;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarCurso(@NotNull Long id,
                                   String nombreCurso){
}
