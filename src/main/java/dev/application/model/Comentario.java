package dev.application.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Comentario extends DefaultEntity {

    @Column(length = 1000)
    private String conteudo;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private int likes;
    private LocalDateTime data;

    public String getConteudo() {
        return conteudo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}