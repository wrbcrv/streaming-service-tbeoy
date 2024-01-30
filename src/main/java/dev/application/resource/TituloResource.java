package dev.application.resource;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;

import dev.application.dto.ComentarioDTO;
import dev.application.dto.ComentarioResponseDTO;
import dev.application.dto.EpisodioDTO;
import dev.application.dto.TituloDTO;
import dev.application.dto.TituloResponseDTO;
import dev.application.service.TituloService;
import dev.application.service.UsuarioService;
import dev.application.service.ValidationService;
import dev.application.util.ValidationError;
import dev.application.util.ValidationException;
import io.quarkus.security.UnauthorizedException;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/titulos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TituloResource {

    @Inject
    TituloService tituloService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    ValidationService validationService;

    @Inject
    JsonWebToken jsonWebToken;

    @GET
    @PermitAll
    public Response listAll() {
        try {
            List<TituloResponseDTO> titulos = tituloService.listAll();
            return Response.ok(titulos).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao processar a requisição: " + e.getMessage()).build();
        }
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(TituloDTO tituloDTO) {
        try {
            TituloResponseDTO titulo = tituloService.insert(tituloDTO);
            return Response.ok(titulo).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/{tituloId}/episodios")
    @RolesAllowed({ "Admin" })
    public Response insertEpisodios(@PathParam("tituloId") Long tituloId, List<EpisodioDTO> episodioDTO) {
        try {
            TituloResponseDTO titulo = tituloService.insertEpisodios(tituloId, episodioDTO);
            return Response.ok(titulo).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/{tituloId}/episodios/{episodioId}/comentario")
    @RolesAllowed({ "Admin", "User" })
    public Response insertComentarios(@PathParam("tituloId") Long tituloId, @PathParam("episodioId") Long episodioId,
            ComentarioDTO comentarioDTO) {
        try {
            List<ValidationError> validationErrors = validationService.validate(comentarioDTO);

            if (!validationErrors.isEmpty()) {
                throw new dev.application.util.ValidationException(validationErrors);
            }

            String login = jsonWebToken.getSubject();

            TituloResponseDTO titulo = tituloService.insertComentario(tituloId, episodioId, login, comentarioDTO);
            return Response.ok(titulo).build();
        } catch (ValidationException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getValidationErrors()).build();
        }
    }

    @DELETE
    @Path("/{tituloId}/episodios/{episodioId}/comentarios/{comentarioId}/delete")
    @RolesAllowed({ "Admin", "User" })
    public Response deleteComentario(@PathParam("tituloId") Long tituloId,
            @PathParam("episodioId") Long episodioId,
            @PathParam("comentarioId") Long comentarioId) {
        try {
            String login = jsonWebToken.getSubject();
            TituloResponseDTO titulo = tituloService.deleteComentario(tituloId, episodioId, comentarioId, login);
            return Response.ok(titulo).entity("Comentário deletado.").build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (UnauthorizedException e) {
            return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{tituloId}/episodios/{episodioId}/comentarios")
    public Response getComentarios(@PathParam("tituloId") Long tituloId, @PathParam("episodioId") Long episodioId) {
        List<ComentarioResponseDTO> comentarios = tituloService.getComentarios(tituloId, episodioId);

        List<ComentarioResponseDTO> reversedComentarios = comentarios.stream()
                .sorted(Comparator.comparingLong(ComentarioResponseDTO::id).reversed())
                .collect(Collectors.toList());

        return Response.ok(reversedComentarios).build();
    }

    @POST
    @Path("/{tituloId}/episodios/{episodioId}/comentarios/{comentarioId}/like")
    @RolesAllowed({ "Admin", "User" })
    public Response likeComentario(
            @PathParam("tituloId") Long tituloId,
            @PathParam("episodioId") Long episodioId,
            @PathParam("comentarioId") Long comentarioId) {
        try {
            String login = jsonWebToken.getSubject();
            TituloResponseDTO titulo = tituloService.likeComentario(tituloId, episodioId, login, comentarioId);
            return Response.ok(titulo).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{tituloId}/episodios/{episodioId}/comentarios/{comentarioId}/isliked")
    @RolesAllowed({ "Admin", "User" })
    public Response isComentarioLiked(
            @PathParam("tituloId") Long tituloId,
            @PathParam("episodioId") Long episodioId,
            @PathParam("comentarioId") Long comentarioId) {
        try {
            String login = jsonWebToken.getSubject();

            boolean isLiked = tituloService.hasUsuarioLikedComentario(login, comentarioId);

            return Response.ok(isLiked).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}