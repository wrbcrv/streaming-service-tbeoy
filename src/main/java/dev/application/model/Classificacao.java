package dev.application.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Classificacao {
    
    LIVRE(1, "Livre"),
    DEZ_ANOS(2, "10 anos"),
    DOZE_ANOS(3, "12 anos"),
    QUATORZE_ANOS(4, "14 anos"),
    DEZESSEIS_ANOS(5, "16 anos"),
    DEZOITO_ANOS(6, "18 anos");

    private final int id;
    private final String label;

    Classificacao(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Classificacao valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;

        for (Classificacao classificacao : Classificacao.values()) {
            if (id.equals(classificacao.getId()))
                return classificacao;
        }

        throw new IllegalArgumentException("ID inválido: " + id);
    }
}
