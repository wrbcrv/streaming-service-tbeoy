package dev.application.service;

import java.util.List;
import java.util.stream.Collectors;

import dev.application.dto.UsuarioResponseDTO;
import dev.application.model.Usuario;
import dev.application.repository.SearchRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SearchServiceImpl implements SearchService {
    @Inject
    SearchRepository searchRepository;

    @Override
    public List<UsuarioResponseDTO> search(String query) {
        List<Usuario> usuario = searchRepository.search(query);

        return usuario.stream().map(UsuarioResponseDTO::valueOf).collect(Collectors.toList());
    }

}
