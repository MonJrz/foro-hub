package com.foro.hub.domain.topico.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.foro.hub.domain.curso.Curso;
import com.foro.hub.domain.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

public record DatosCrearTopico(

        @NotNull
        String titulo,
        @NotNull
        String mensaje,
        @NotNull
        Long idautor,
        @NotNull
        Long idcurso

){
}
