package dev.application.resource;

import java.util.List;

import dev.application.dto.EpisodioMergedDTO;
import dev.application.dto.TituloDTO;
import dev.application.dto.TituloResponseDTO;
import dev.application.service.TituloService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
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
        TituloResponseDTO titulo = tituloService.insert(tituloDTO);
        return Response.ok(titulo).build();
    }

    @POST
    @Path("/{tituloId}/episodios")
    @RolesAllowed({ "Admin" })
    public Response insertEpisodios(@PathParam("tituloId") Long tituloId, List<EpisodioMergedDTO> episodioMergedDTO) {
        TituloResponseDTO titulo = tituloService.insertEpisodios(tituloId, episodioMergedDTO);
        return Response.ok(titulo).build();
    }
}