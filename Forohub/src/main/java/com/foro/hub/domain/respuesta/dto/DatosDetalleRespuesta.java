package com.foro.hub.domain.respuesta.dto;

import com.foro.hub.domain.respuesta.Respuesta;

import java.time.LocalDateTime;

public record DatosDetalleRespuesta(
        Long id,
        String mensaje,
        LocalDateTime fecha,
        Boolean borrado,
        Long usuarioId,
        String nombre,
        Long topicoId,
        String topico
) {


    public DatosDetalleRespuesta(Respuesta respuesta){
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFecha(),
                respuesta.getBorrado(),
                respuesta.getAutor().getId(),
                respuesta.getAutor().getNombre(),
                respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo());
    }
}
