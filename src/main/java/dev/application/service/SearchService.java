package dev.application.service;

import java.util.List;

import dev.application.dto.TituloResponseDTO;

public interface SearchService {

   List<TituloResponseDTO> search(String query);
}
