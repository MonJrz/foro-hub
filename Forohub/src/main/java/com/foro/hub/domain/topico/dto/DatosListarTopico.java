package com.foro.hub.domain.topico.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.foro.hub.domain.curso.Curso;
import com.foro.hub.domain.topico.Topico;
import com.foro.hub.domain.usuario.Usuario;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

public record DatosListarTopico(Long id,
                                String titulo,
                                String mensaje,
                                String nombreAutor,
                                Curso curso,
                                @CreatedDate @JsonFormat(pattern ="dd-MM-yyyy HH:mm")
                                LocalDateTime fecha,
                                Boolean status
) {

    public DatosListarTopico(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor().getNombre(),
                topico.getCurso(),
                topico.getFecha(),
                topico.isStatus()
        );
    }
}
