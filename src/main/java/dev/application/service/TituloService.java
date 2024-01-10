package dev.application.service;

import java.util.List;

import dev.application.dto.ComentarioDTO;
import dev.application.dto.EpisodioDTO;
import dev.application.dto.TituloDTO;
import dev.application.dto.TituloResponseDTO;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

public interface TituloService {

    List<TituloResponseDTO> listAll();

    TituloResponseDTO insert(@Valid TituloDTO tituloDTO) throws ConstraintViolationException;

    TituloResponseDTO insertEpisodios(@Valid Long tituloId, List<EpisodioDTO> episodioMergedDTO) throws ConstraintViolationException;

    TituloResponseDTO inserirComentario(@Valid Long tituloId, Long episodioId, ComentarioDTO comentarioDTO);
}
