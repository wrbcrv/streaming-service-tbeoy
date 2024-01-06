package dev.application.repository;

import dev.application.model.Comentario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ComentarioRepository implements PanacheRepository<Comentario> {
    
}
