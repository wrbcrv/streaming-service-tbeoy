package dev.application.dto;

import java.time.format.DateTimeFormatter;

import dev.application.model.Classificacao;
import dev.application.model.Genero;
import dev.application.model.Titulo;

public record TituloResponseDTO(
        Long id,
        String titulo,
        String sinopse,
        String lancamento,
        Genero genero,
        Classificacao classificacao) {

    public static TituloResponseDTO valueOf(Titulo titulo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        String lancamento = titulo.getLancamento().format(formatter);

        return new TituloResponseDTO(
                titulo.getId(),
                titulo.getTitulo(),
                titulo.getSinopse(),
                lancamento,
                titulo.getGenero(),
                titulo.getClassificacao());
    }
}