package dev.application.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TituloDTO(
        @NotBlank(message = "Titulo é obrigatório")
        String titulo,
        @NotBlank(message = "Sinopse é obrigatório")
        String sinopse,
        @NotNull(message = "Data de lançamento é obrigatório")
        LocalDate lancamento,
        @NotNull(message = "Escolha um gênero")
        Integer generoId,
        @NotNull(message = "Escolha uma classificação indicativa")
        Integer classificacaoId) {
}