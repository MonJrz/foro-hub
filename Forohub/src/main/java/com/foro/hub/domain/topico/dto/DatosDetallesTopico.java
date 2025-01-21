package com.foro.hub.domain.topico.dto;

import com.foro.hub.domain.curso.Categoria;
import com.foro.hub.domain.curso.Curso;
import com.foro.hub.domain.topico.Topico;
import com.foro.hub.domain.usuario.Usuario;
import com.foro.hub.domain.usuario.UsuarioRepositorio;


public record DatosDetallesTopico(Long id, String titulo, String mensaje, String nombreAutor, Curso curso) {
    public DatosDetallesTopico(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor().getNombre(),
                topico.getCurso()
        );
    }


    @Override
    public String titulo() {
        return titulo;
    }

    @Override
    public String mensaje() {
        return mensaje;
    }




}
