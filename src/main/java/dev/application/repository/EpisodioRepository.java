package dev.application.repository;

import dev.application.model.Episodio;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EpisodioRepository implements PanacheRepository<Episodio> {
    
}
