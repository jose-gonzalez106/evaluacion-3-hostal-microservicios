package com.huesped.huespedes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.huesped.huespedes.DTO.HuespedDTO;
import com.huesped.huespedes.exception.RecursoDuplicadoException;
import com.huesped.huespedes.exception.RecursoNoEncontradoException;
import com.huesped.huespedes.model.Huesped;
import com.huesped.huespedes.repository.HuespedRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HuespedService {

    private final HuespedRepository huespedRepository;

    public HuespedService(HuespedRepository huespedRepository) {
        this.huespedRepository = huespedRepository;
    }

    public List<HuespedDTO> obtenerTodos() {
        log.info("[HuespedService] Obteniendo todos los huéspedes");

        return huespedRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public HuespedDTO buscarPorRun(String run) {
        log.info("[HuespedService] Buscando huésped {}", run);
        return convertirADTO(obtenerEntidad(run));
    }

    @Transactional
    public HuespedDTO guardar(Huesped huesped) {
        if (huespedRepository.existsById(huesped.getRun())) {
            throw new RecursoDuplicadoException(
                    "Ya existe un huésped con RUN: " + huesped.getRun());
        }

        if (huespedRepository.existsByCorreo(huesped.getCorreo())) {
            throw new RecursoDuplicadoException(
                    "Ya existe un huésped con correo: " + huesped.getCorreo());
        }

        Huesped guardado = huespedRepository.save(huesped);
        return convertirADTO(guardado);
    }

    @Transactional
    public HuespedDTO actualizar(String run, Huesped huesped) {
        Huesped existente = obtenerEntidad(run);

        Optional.ofNullable(huesped.getNombre()).ifPresent(existente::setNombre);
        Optional.ofNullable(huesped.getApellido()).ifPresent(existente::setApellido);
        Optional.ofNullable(huesped.getTelefono()).ifPresent(existente::setTelefono);

        if (huesped.getCorreo() != null) {
            if (!huesped.getCorreo().equals(existente.getCorreo())
                    && huespedRepository.existsByCorreo(huesped.getCorreo())) {
                throw new RecursoDuplicadoException(
                        "Ya existe un huésped con correo: " + huesped.getCorreo());
            }
            existente.setCorreo(huesped.getCorreo());
        }

        Optional.ofNullable(huesped.getComunaId()).ifPresent(existente::setComunaId);

        Huesped actualizado = huespedRepository.save(existente);
        return convertirADTO(actualizado);
    }

    @Transactional
    public String eliminar(String run) {
        Huesped huesped = obtenerEntidad(run);
        huespedRepository.delete(huesped);
        return "Huésped eliminado correctamente";
    }

    private Huesped obtenerEntidad(String run) {
        return huespedRepository.findById(run)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Huésped con RUN " + run + " no encontrado"));
    }

    private HuespedDTO convertirADTO(Huesped huesped) {
        HuespedDTO dto = new HuespedDTO();
        dto.setRun(huesped.getRun());
        dto.setNombre(huesped.getNombre());
        dto.setApellido(huesped.getApellido());
        dto.setTelefono(huesped.getTelefono());
        dto.setCorreo(huesped.getCorreo());
        dto.setComunaId(huesped.getComunaId());
        return dto;
    }
}
