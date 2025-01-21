package com.foro.hub.domain.topico.dto;

import com.foro.hub.domain.curso.Curso;
import com.foro.hub.domain.usuario.Usuario;
import jakarta.validation.constraints.NotNull;


public record DatosActualizarTopico(@NotNull Long id,
                                    String titulo,
                                    String mensaje,
                                    Curso cursoId
) {
}
