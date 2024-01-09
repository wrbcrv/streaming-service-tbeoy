package dev.application.service;

import java.util.List;

import dev.application.dto.UsuarioDTO;
import dev.application.dto.UsuarioResponseDTO;

public interface UsuarioService {

    List<UsuarioResponseDTO> getAll();

    UsuarioResponseDTO insert(UsuarioDTO usuarioDTO);

    UsuarioResponseDTO update(Long usuarioId, UsuarioDTO usuarioDTO);

    void delete(Long usuarioId);

    UsuarioResponseDTO findById(Long usuarioId);

    UsuarioResponseDTO findByLogin(String login);

    UsuarioResponseDTO findByLoginAndSenha(String login, String senha);
}