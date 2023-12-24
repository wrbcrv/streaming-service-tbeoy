package dev.application.model;

import jakarta.persistence.Entity;

@Entity
public class Episodio extends DefaultEntity {

    private String titulo;
    private String videoUrl;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}