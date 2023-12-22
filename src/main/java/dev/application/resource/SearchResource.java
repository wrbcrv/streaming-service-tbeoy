package dev.application.resource;

import java.util.List;

import dev.application.dto.UsuarioResponseDTO;
import dev.application.service.SearchService;
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
    public Response search(@QueryParam("q") String query) {
        List<UsuarioResponseDTO> usuarios = searchService.search(query);
        
        return Response.ok(usuarios).build();
    }
}
