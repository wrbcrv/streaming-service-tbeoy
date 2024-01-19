package dev.application.dto;

import dev.application.model.Comentario;

public record ComentarioResponseDTO(
                Long id,
                String comentario,
                int likes) {

        public static ComentarioResponseDTO valueOf(Comentario comentario) {
                return new ComentarioResponseDTO(
                                comentario.getId(),
                                comentario.getComentario(),
                                comentario.getLikes());
        }
}
