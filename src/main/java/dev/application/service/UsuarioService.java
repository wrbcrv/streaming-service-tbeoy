package dev.application.service;

import java.util.List;

import dev.application.dto.UsuarioDTO;
import dev.application.dto.UsuarioResponseDTO;
import dev.application.util.ValidationException;

public interface UsuarioService {

    List<UsuarioResponseDTO> getAll();

    UsuarioResponseDTO insert(UsuarioDTO usuarioDTO) throws ValidationException;

    UsuarioResponseDTO update(Long usuarioId, UsuarioDTO usuarioDTO);

    void delete(Long usuarioId);

    UsuarioResponseDTO findById(Long usuarioId);

    UsuarioResponseDTO findByLogin(String login);

    UsuarioResponseDTO findByLoginAndSenha(String login, String senha);
}