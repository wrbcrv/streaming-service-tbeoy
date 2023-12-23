package dev.application.converter;

import dev.application.model.Genero;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GeneroConverter implements AttributeConverter<Genero, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Genero genero) {
        return (genero == null ? null : genero.getId());
    }

    @Override
    public Genero convertToEntityAttribute(Integer id) {
        return Genero.valueOf(id);
    }
}