package dev.application.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Size;

public record ComentarioDTO(
        @Size(max = 1000, message = "Limite máximo de caracteres atingido")
        String conteudo,
        Integer likes,
        LocalDateTime data) {
}