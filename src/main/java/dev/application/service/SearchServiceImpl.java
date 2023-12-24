package dev.application.service;

import java.util.List;
import java.util.stream.Collectors;

import dev.application.dto.TituloResponseDTO;
import dev.application.model.Titulo;
import dev.application.repository.SearchRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SearchServiceImpl implements SearchService {
    @Inject
    SearchRepository searchRepository;

    @Override
    public List<TituloResponseDTO> search(String query) {
        List<Titulo> usuario = searchRepository.search(query);
        return usuario.stream().map(TituloResponseDTO::valueOf).collect(Collectors.toList());
    }
}