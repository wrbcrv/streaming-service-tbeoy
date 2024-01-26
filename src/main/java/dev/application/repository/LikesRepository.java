package dev.application.repository;

import java.util.Optional;

import dev.application.model.Likes;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LikesRepository implements PanacheRepository<Likes> {

    public Optional<Likes> findByLogin(String login) {
        return find("login", login).firstResultOptional();
    }
}
