package com.foro.hub.domain.usuario.dto;

import com.foro.hub.domain.usuario.Usuario;

public record DatosDetallesUsuario ( Long id, String nombre, String correo){
    public DatosDetallesUsuario(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo()
        );
    }
}



