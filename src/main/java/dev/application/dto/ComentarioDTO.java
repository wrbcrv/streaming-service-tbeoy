package dev.application.dto;

import dev.application.model.Comentario;

public record ComentarioDTO(
    Long id,
    String comentario,
    int likes) {
        
    public static ComentarioDTO valueOf(Comentario comentario) {
        return new ComentarioDTO(comentario.getId(), comentario.getComentario(), comentario.getLikes());
    }
}
