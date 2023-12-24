package dev.application.resource;

import java.util.List;

import dev.application.dto.TituloResponseDTO;
import dev.application.service.SearchService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class SearchResource {

    @Inject
    SearchService searchService;

    @GET
    @PermitAll
    public Response search(@QueryParam("q") String query) {
        try {
            List<TituloResponseDTO> titulos = searchService.search(query);

            if (titulos != null && !titulos.isEmpty())
                return Response.ok(titulos).build();
            else
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Nenhum título encontrado para a consulta: " + query)
                        .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao processar a consulta: " + e.getMessage())
                    .build();
        }
    }
}
