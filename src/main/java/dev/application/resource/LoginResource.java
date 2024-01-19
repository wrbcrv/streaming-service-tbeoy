package dev.application.resource;

import java.util.List;

import dev.application.dto.LoginDTO;
import dev.application.dto.UsuarioResponseDTO;
import dev.application.service.HashService;
import dev.application.service.JwtService;
import dev.application.service.UsuarioService;
import dev.application.service.ValidationService;
import dev.application.util.ValidationError;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.NewCookie.SameSite;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    UsuarioService usuarioService;

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Inject
    ValidationService validationService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response login(LoginDTO loginDTO) {
        try {
            List<ValidationError> validationErrors = validationService.validate(loginDTO);

            if (!validationErrors.isEmpty()) {
                try {
                    return Response.status(Status.BAD_REQUEST).entity(validationErrors).build();
                } catch (Exception e) {
                    return Response.status(Status.INTERNAL_SERVER_ERROR)
                            .entity("Erro durante tentativa de login").build();
                }
            }

            String hash = hashService.getSenhaHash(loginDTO.senha());

            UsuarioResponseDTO usuario = usuarioService.findByLoginAndSenha(loginDTO.login(), hash);

            String token = jwtService.generateJwt(usuario);

            NewCookie cookie = new NewCookie.Builder("token")
                    .value(token)
                    .domain(null)
                    .path("/")
                    .maxAge(86400)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite(SameSite.NONE)
                    .build();

            return Response.ok().header(HttpHeaders.SET_COOKIE, cookie).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("get-token")
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public Response getTokenFromCookieValue(@Context HttpHeaders httpHeaders) {
        try {
            Cookie cookie = httpHeaders.getCookies().get("token");

            if (cookie != null) {
                String cookieValue = cookie.getValue();
                return Response.ok().entity(cookieValue).build();
            }

            return Response.status(Status.NOT_FOUND).entity("Valor não encontrado").build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("logout")
    @PermitAll
    public Response logout(@Context HttpHeaders httpHeaders) {
        try {
            Cookie cookie = httpHeaders.getCookies().get("token");

            if (cookie != null) {
                NewCookie expiredCookie = new NewCookie.Builder(cookie).value("").path("/").build();

                return Response.ok().header(HttpHeaders.SET_COOKIE, expiredCookie).build();
            }

            return Response.status(Status.NOT_FOUND).entity("Cookie não encontrado").build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }
}