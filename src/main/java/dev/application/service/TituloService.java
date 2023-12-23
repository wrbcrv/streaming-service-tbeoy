package dev.application.service;

import java.util.List;

import dev.application.dto.TituloDTO;
import dev.application.dto.TituloResponseDTO;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

public interface TituloService {

    List<TituloResponseDTO> listAll();

    TituloResponseDTO insert(@Valid TituloDTO tituloDTO) throws ConstraintViolationException;
}