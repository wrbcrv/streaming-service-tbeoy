package dev.application.repository;

import java.util.List;
import dev.application.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SearchRepository implements PanacheRepository<Usuario> {

    public List<Usuario> search(String query) {
        return list("LOWER(login) LIKE ?1", "%" + query.toLowerCase() + "%");
    }
}