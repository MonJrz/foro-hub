package com.foro.hub.domain.usuario.dto;


import jakarta.validation.constraints.NotBlank;

public record DatosCrearUsuario(
        @NotBlank
        String nombre,
        @NotBlank
        String correo,
        @NotBlank
        String clave

) {
}
