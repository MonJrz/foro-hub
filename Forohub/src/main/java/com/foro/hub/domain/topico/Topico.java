package com.foro.hub.domain.topico;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.foro.hub.domain.respuesta.Respuesta;
import com.foro.hub.domain.curso.CursoRepositorio;
import com.foro.hub.domain.curso.Curso;
import com.foro.hub.domain.respuesta.Respuesta;
import com.foro.hub.domain.topico.dto.DatosActualizarTopico;
import com.foro.hub.domain.topico.dto.DatosCrearTopico;
import com.foro.hub.domain.usuario.Usuario;
import com.foro.hub.domain.usuario.UsuarioRepositorio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name= "topicos")
@AllArgsConstructor

@Getter
@EqualsAndHashCode(of= "id")

public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Usuario autor;
    private String titulo;
    private String mensaje;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime fecha;
    private boolean status;
    @ManyToOne(fetch = FetchType.EAGER)
    private Curso curso;




    public Topico(DatosCrearTopico datos, UsuarioRepositorio usuarioRepositorio, CursoRepositorio cursoRepositorio) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.autor = usuarioRepositorio.findById(datos.idautor()).
                orElseThrow(()-> new IllegalArgumentException("Usuario no encontrado"));
        this.curso = cursoRepositorio.findById(datos.idcurso()).
                orElseThrow(()-> new IllegalArgumentException("Curso no encontrado"));
    }

    public Topico() {
    }

    public String mostrarStatus(){
        return this.status?"Resuelto":"Sin Respuesta";
    }

    public Long getId() {
        return id;
    }

    @PrePersist
    public void asignarFecha(){
        this.fecha = LocalDateTime.now();
    }



    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public boolean isStatus() {
        return status;
    }

    public Usuario getAutor() {
        return autor;
    }

    public Curso getCurso() {
        return curso;
    }


    public void actualizarTopico( DatosActualizarTopico datosActualizarTopico) {
        if (datosActualizarTopico.titulo() != null) {
            this.titulo = datosActualizarTopico.titulo();
        }
        if (datosActualizarTopico.mensaje() != null) {
            this.mensaje = datosActualizarTopico.mensaje();
        }

    }
}
