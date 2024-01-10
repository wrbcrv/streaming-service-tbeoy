package dev.application.dto;

import java.util.List;

import dev.application.model.Episodio;
import jakarta.validation.constraints.NotBlank;

public record EpisodioDTO(
    Long id, 
    @NotBlank(message = "Título é obrigatório")
    String titulo,
    @NotBlank(message = "URL do vídeo é obrigatório") 
    String videoUrl,

    List<ComentarioDTO> comentarios) {

    public static EpisodioDTO valueOf(Episodio episodio) {
        List<ComentarioDTO> comentarios = null;
        
        if (episodio.getComentarios() != null && !episodio.getComentarios().isEmpty())
            comentarios = episodio.getComentarios().stream().map(e -> ComentarioDTO.valueOf(e)).toList();

        return new EpisodioDTO(episodio.getId(), episodio.getTitulo(), episodio.getVideoUrl(), comentarios);
    }
}