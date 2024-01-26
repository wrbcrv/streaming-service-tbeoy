package dev.application.model;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario_likes")
public class Likes extends DefaultEntity {

    @Column(name = "login")
    private String login;

    @ElementCollection
    @CollectionTable(name = "liked_comentarios", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "comentario_id")
    private Set<Long> likedComentarios;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<Long> getLikedComentarios() {
        return likedComentarios;
    }

    public void setLikedComentarios(Set<Long> likedComentarios) {
        this.likedComentarios = likedComentarios;
    }
}