INSERT INTO
    usuario (nome, sobrenome, login, senha, perfil)
VALUES
    (
        'Usuário',
        'Administrador',
        'admin',
        '670O1PvrZN/9M4jaH8LZGBXvu+O2HeAPE1IAH8iSzSY+JbkNaFdyizUFaKOZMNRhDzj97kONYDRA8ZsuA9/6pg==',
        1
    );

INSERT INTO
    titulo (
        imageUrl,
        titulo,
        sinopse,
        lancamento,
        genero,
        classificacao
    )
VALUES
    (
        'https://example.com/placeholder_image.jpg',
        'Lorem Ipsum: Placeholder Title',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam non urna sit amet nisi fermentum ullamcorper. Proin eu nisi nec sapien tincidunt laoreet.',
        '2023-01-01',
        2,
        3
    );

INSERT INTO
    episodio (titulo, videoUrl)
VALUES
    ('Placeholder Episode', '');

INSERT INTO
    comentario (comentario, likes)
VALUES
    ('Placeholder Comment', 0);

INSERT INTO
    titulo_episodio (titulo_id, episodio_id)
VALUES
    (1, 1);

INSERT INTO
    episodio_comentario (episodio_id, comentario_id)
VALUES
    (1, 1);