package dev.application.model;

import jakarta.persistence.Entity;

@Entity
public class Comentario extends DefaultEntity {
    
    private String comentario;
    private int likes;

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}