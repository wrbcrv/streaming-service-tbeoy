package dev.application.dto;

import dev.application.model.Perfil;
import dev.application.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String sobrenome,
        String login,
        Perfil perfil) {

    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        if (usuario == null)
            return null;

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getLogin(),
                usuario.getPerfil());
    }
}