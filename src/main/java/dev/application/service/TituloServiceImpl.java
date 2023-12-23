package dev.application.service;

import java.util.List;

import dev.application.dto.TituloDTO;
import dev.application.dto.TituloResponseDTO;
import dev.application.model.Classificacao;
import dev.application.model.Genero;
import dev.application.model.Titulo;
import dev.application.repository.TituloRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@ApplicationScoped
public class TituloServiceImpl implements TituloService {

    @Inject
    TituloRepository tituloRepository;

    @Override
    public List<TituloResponseDTO> listAll() {
        List<Titulo> titulo = tituloRepository.listAll();

        return titulo.stream().map(TituloResponseDTO::valueOf).toList();
    }

    @Override
    public TituloResponseDTO insert(@Valid TituloDTO tituloDTO) throws ConstraintViolationException {
        Titulo titulo = new Titulo();

        titulo.setTitulo(tituloDTO.titulo());
        titulo.setSinopse(tituloDTO.sinopse());
        titulo.setLancamento(tituloDTO.lancamento());
        titulo.setGenero(Genero.valueOf(tituloDTO.generoId()));
        titulo.setClassificacao(Classificacao.valueOf(tituloDTO.classificacaoId()));

        tituloRepository.persist(titulo);

        return TituloResponseDTO.valueOf(titulo);
    }
}