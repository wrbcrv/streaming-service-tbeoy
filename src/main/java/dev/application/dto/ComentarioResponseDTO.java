package dev.application.dto;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import dev.application.model.Comentario;

public record ComentarioResponseDTO(
                Long id,
                UsuarioResponseDTO usuario,
                String conteudo,
                Integer likes,
                String data) {

        public static ComentarioResponseDTO valueOf(Comentario comentario) {
                String formattedData = comentario.getData() != null ? comentario.getData()
                                .format(DateTimeFormatter.ofPattern("d 'de' MMM 'de' yyyy HH:mm a",
                                                new Locale("pt", "BR")))
                                : null;

                return new ComentarioResponseDTO(
                                comentario.getId(),
                                UsuarioResponseDTO.valueOf(comentario.getUsuario()),
                                comentario.getConteudo(),
                                comentario.getLikes(),
                                formattedData);
        }
}