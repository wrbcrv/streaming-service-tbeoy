package dev.application.dto;

import java.time.LocalDateTime;

public record ComentarioDTO(
        String comentario,
        int likes,
        LocalDateTime data) {
}
