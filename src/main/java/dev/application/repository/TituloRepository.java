package dev.application.repository;

import dev.application.model.Titulo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TituloRepository implements PanacheRepository<Titulo> {

}