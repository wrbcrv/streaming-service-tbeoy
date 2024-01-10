package dev.application.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;

import dev.application.model.Classificacao;
import dev.application.model.Genero;
import dev.application.model.Titulo;

public record TituloResponseDTO(
        Long id,
        String imageUrl,
        String titulo,
        String sinopse,
        String lancamento,
        Genero genero,
        Classificacao classificacao,
        List<EpisodioDTO> episodios) {

    public static TituloResponseDTO valueOf(Titulo titulo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        String lancamento = titulo.getLancamento().format(formatter);

        List<EpisodioDTO> episodios = null;

        if (titulo.getEpisodios() != null && !titulo.getEpisodios().isEmpty())
            episodios = titulo.getEpisodios().stream().map(e -> EpisodioDTO.valueOf(e)).toList();

        return new TituloResponseDTO(
                titulo.getId(),
                titulo.getImageUrl(),
                titulo.getTitulo(),
                titulo.getSinopse(),
                lancamento,
                titulo.getGenero(),
                titulo.getClassificacao(),
                episodios);
    }
}