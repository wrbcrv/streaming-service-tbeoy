package dev.application.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Genero {
    
    ACAO(1, "Ação"),
    COMEDIA(2, "Comédia"),
    DRAMA(3, "Drama"),
    FICCAO_CIENTIFICA(4, "Ficção Científica"),
    TERROR(5, "Terror"),
    AVENTURA(6, "Aventura"),
    ROMANCE(7, "Romance"),
    DOCUMENTARIO(8, "Documentário"),
    ANIMACAO(9, "Animação"),
    FANTASIA(10, "Fantasia");

    private final int id;
    private final String label;

    Genero(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Genero valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;

        for (Genero genero : Genero.values()) {
            if (id.equals(genero.getId()))
                return genero;
        }

        throw new IllegalArgumentException("ID inválido: " + id);
    }
}
