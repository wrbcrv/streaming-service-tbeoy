package dev.application.service;

import java.util.List;

import dev.application.dto.ComentarioDTO;
import dev.application.dto.EpisodioDTO;
import dev.application.dto.TituloDTO;
import dev.application.dto.TituloResponseDTO;

public interface TituloService {

    List<TituloResponseDTO> listAll();

    TituloResponseDTO insert(TituloDTO tituloDTO);

    TituloResponseDTO insertEpisodios(Long tituloId, List<EpisodioDTO> episodioDTO);

    TituloResponseDTO insertComentario(Long tituloId, Long episodioId, ComentarioDTO comentarioDTO);

}
