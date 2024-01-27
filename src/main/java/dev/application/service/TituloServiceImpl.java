package dev.application.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dev.application.dto.ComentarioDTO;
import dev.application.dto.EpisodioDTO;
import dev.application.dto.TituloDTO;
import dev.application.dto.TituloResponseDTO;
import dev.application.model.Classificacao;
import dev.application.model.Comentario;
import dev.application.model.Episodio;
import dev.application.model.Genero;
import dev.application.model.Likes;
import dev.application.model.Titulo;
import dev.application.repository.ComentarioRepository;
import dev.application.repository.EpisodioRepository;
import dev.application.repository.LikesRepository;
import dev.application.repository.TituloRepository;
import dev.application.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class TituloServiceImpl implements TituloService {

    @Inject
    TituloRepository tituloRepository;

    @Inject
    EpisodioRepository episodioRepository;

    @Inject
    ComentarioRepository comentarioRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    LikesRepository likesRepository;

    @Override
    public List<TituloResponseDTO> listAll() {
        List<Titulo> titulo = tituloRepository.listAll();

        return titulo.stream().map(TituloResponseDTO::valueOf).toList();
    }

    @Override
    @Transactional
    public TituloResponseDTO insert(TituloDTO tituloDTO) {
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
    public TituloResponseDTO insertEpisodios(Long tituloId, List<EpisodioDTO> episodioDTO) {
        Titulo existingTitulo = tituloRepository.findById(tituloId);

        if (existingTitulo == null)
            throw new NotFoundException("Título não encontrado");

        List<Episodio> episodios = existingTitulo.getEpisodios();

        for (EpisodioDTO episodio : episodioDTO) {
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
    public TituloResponseDTO insertComentario(Long tituloId, Long episodioId, String login,
            ComentarioDTO comentarioDTO) {
        Titulo titulo = tituloRepository.findById(tituloId);

        Episodio episodio = episodioRepository.findById(episodioId);

        if (titulo == null || episodio == null)
            throw new NotFoundException("Título ou episódio não encontrados");

        Comentario comentario = new Comentario();
        comentario.setUsuario(usuarioRepository.findByLogin(login));
        comentario.setConteudo(comentarioDTO.conteudo());
        comentario.setLikes(comentarioDTO.likes());
        comentario.setData(LocalDateTime.now());

        episodio.getComentarios().add(comentario);

        tituloRepository.persist(titulo);

        return TituloResponseDTO.valueOf(titulo);
    }

    @Override
    @Transactional
    public TituloResponseDTO likeComentario(Long tituloId, Long episodioId, String login, Long comentarioId) {
        Titulo titulo = tituloRepository.findById(tituloId);

        Episodio episodio = episodioRepository.findById(episodioId);

        if (titulo == null || episodio == null)
            throw new NotFoundException("Título ou episódio não encontrados");

        Comentario comentario = findComentario(episodio, comentarioId);

        if (comentario == null)
            throw new NotFoundException("Comentário não encontrado");

        Likes usuarioLikes = likesRepository.findByLogin(login)
                .orElseGet(() -> {
                    Likes newUsuarioLikes = new Likes();
                    newUsuarioLikes.setLogin(login);
                    return newUsuarioLikes;
                });

        Set<Long> likedComentarios = usuarioLikes.getLikedComentarios();

        if (likedComentarios == null) {
            likedComentarios = new HashSet<>();
            usuarioLikes.setLikedComentarios(likedComentarios);
        }

        boolean alreadyLiked = likedComentarios.contains(comentarioId);
        comentario.setLikes(comentario.getLikes() + (alreadyLiked ? -1 : 1));

        if (alreadyLiked)
            likedComentarios.remove(comentarioId);
        else
            likedComentarios.add(comentarioId);

        likesRepository.persist(usuarioLikes);
        tituloRepository.persist(titulo);

        return TituloResponseDTO.valueOf(titulo);
    }

    private Comentario findComentario(Episodio episodio, Long comentarioId) {
        return episodio.getComentarios().stream()
                .filter(comentario -> comentario.getId().equals(comentarioId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean hasUsuarioLikedComentario(String login, Long comentarioId) {
        Comentario comentario = comentarioRepository.findById(comentarioId);

        if (comentario == null)
            throw new NotFoundException("Comentário não encontrado");

        Likes usuarioLikes = likesRepository.findByLogin(login)
                .orElseGet(() -> {
                    Likes newUsuarioLikes = new Likes();
                    newUsuarioLikes.setLogin(login);
                    return newUsuarioLikes;
                });

        Set<Long> likedComentarios = usuarioLikes.getLikedComentarios();

        return likedComentarios != null && likedComentarios.contains(comentarioId);
    }
}