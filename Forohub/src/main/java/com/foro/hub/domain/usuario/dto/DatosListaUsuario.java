package com.foro.hub.domain.usuario.dto;
import com.foro.hub.domain.usuario.Usuario;

public record DatosListaUsuario( Long id, String nombre, String correo) {

    public DatosListaUsuario(Usuario usuario) {
        this(usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo());
    }

}