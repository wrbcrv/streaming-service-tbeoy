package dev.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotBlank(message = "Sobrenome é obrigatório")
        String sobrenome,
        @NotBlank(message = "Email é obrigatório") 
        @Email(message = "Informe um email válido")
        String login,
        @NotBlank(message = "Senha é obrigatório")
        @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
        String senha,
        @NotNull(message = "Perfil é obrigatório")
        Integer idPerfil) {
}