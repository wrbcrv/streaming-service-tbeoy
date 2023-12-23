package dev.application.resource;

import java.util.List;

import dev.application.dto.TituloDTO;
import dev.application.dto.TituloResponseDTO;
import dev.application.service.TituloService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/titulos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TituloResource {

    @Inject
    TituloService tituloService;

    @GET
    @PermitAll
    public Response listAll() {
        try {
            List<TituloResponseDTO> titulos = tituloService.listAll();
            return Response.ok(titulos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao processar a requisição: " + e.getMessage()).build();
        }
    }

    @POST
    @Transactional
    @RolesAllowed({ "Admin" })
    public Response insert(TituloDTO tituloDTO) {
        try {
            TituloResponseDTO titulo = tituloService.insert(tituloDTO);
            return Response.ok(titulo).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao processar a requisição: " + e.getMessage()).build();
        }
    }
}