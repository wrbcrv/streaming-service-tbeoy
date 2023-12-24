package dev.application.repository;

import java.util.List;

import dev.application.model.Titulo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SearchRepository implements PanacheRepository<Titulo> {

    public List<Titulo> search(String query) {
        return list("LOWER(titulo) LIKE ?1", "%" + query.toLowerCase() + "%");
    }
}