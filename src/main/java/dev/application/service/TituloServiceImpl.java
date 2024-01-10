package dev.application.service;

import java.util.List;

import dev.application.dto.ComentarioDTO;
import dev.application.dto.EpisodioDTO;
import dev.application.dto.TituloDTO;
import dev.application.dto.TituloResponseDTO;
import dev.application.model.Classificacao;
import dev.application.model.Comentario;
import dev.application.model.Episodio;
import dev.application.model.Genero;
import dev.application.model.Titulo;
import dev.application.repository.ComentarioRepository;
import dev.application.repository.EpisodioRepository;
import dev.application.repository.TituloRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class TituloServiceImpl implements TituloService {

    @Inject
    TituloRepository tituloRepository;

    @Inject
    EpisodioRepository episodioRepository;

    @Inject
    ComentarioRepository comentarioRepository;

    @Override
    public List<TituloResponseDTO> listAll() {
        List<Titulo> titulo = tituloRepository.listAll();

        return titulo.stream().map(TituloResponseDTO::valueOf).toList();
    }

    @Override
    @Transactional
    public TituloResponseDTO insert(@Valid TituloDTO tituloDTO) throws ConstraintViolationException {
        Titulo titulo = new Titulo();

        titulo.setImageUrl(tituloDTO.imageUrl());
        titulo.setTitulo(tituloDTO.titulo());
        titulo.setSinopse(tituloDTO.sinopse());
        titulo.setLancamento(tituloDTO.lancamento());
        titulo.setGenero(Genero.valueOf(tituloDTO.generoId()));
        titulo.setClassificacao(Classificacao.valueOf(tituloDTO.classificacaoId()));

        tituloRepository.persist(titulo);

        return TituloResponseDTO.valueOf(titulo);
    }

    @Override
    @Transactional
    public TituloResponseDTO insertEpisodios(@Valid Long tituloId, List<EpisodioDTO> episodioMergedDTO)
            throws ConstraintViolationException {
        Titulo existingTitulo = tituloRepository.findById(tituloId);

        if (existingTitulo == null)
            throw new NotFoundException("Título não encontrado");

        List<Episodio> episodios = existingTitulo.getEpisodios();

        for (EpisodioDTO episodio : episodioMergedDTO) {
            Episodio newEpisodio = new Episodio();

            newEpisodio.setTitulo(episodio.titulo());
            newEpisodio.setVideoUrl(episodio.videoUrl());

            episodios.add(newEpisodio);
        }

        tituloRepository.persist(existingTitulo);

        return TituloResponseDTO.valueOf(existingTitulo);
    }

    @Override
    @Transactional
    public TituloResponseDTO inserirComentario(@Valid Long tituloId, Long episodioId,
            ComentarioDTO comentarioDTO) throws ConstraintViolationException {
        Titulo titulo = tituloRepository.findById(tituloId);

        Episodio episodio = episodioRepository.findById(episodioId);

        if (titulo == null || episodio == null)
            throw new NotFoundException("Título ou episódio não encontrados");

        Comentario comentario = new Comentario();
        comentario.setComentario(comentarioDTO.comentario());
        comentario.setLikes(comentarioDTO.likes());
        episodio.getComentarios().add(comentario);

        tituloRepository.persist(titulo);

        return TituloResponseDTO.valueOf(titulo);
    }
}