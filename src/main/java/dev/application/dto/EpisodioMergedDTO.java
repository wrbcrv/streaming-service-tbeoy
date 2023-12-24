package dev.application.dto;

import dev.application.model.Episodio;
import jakarta.validation.constraints.NotBlank;

public record EpisodioMergedDTO(
    Long id, 
    @NotBlank(message = "Título é obrigatório")
    String titulo,
    @NotBlank(message = "URL do vídeo é obrigatório") 
    String videoUrl) {

    public static EpisodioMergedDTO valueOf(Episodio episodio) {
        return new EpisodioMergedDTO(episodio.getId(), episodio.getTitulo(), episodio.getVideoUrl());
    }
}