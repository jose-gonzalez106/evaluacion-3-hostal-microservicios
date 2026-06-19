package com.huesped.huespedes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@Transactional
public class HuespedService {

    @Autowired
    private HuespedRepository huespedRepository;

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

    public HuespedDTO actualizar(String run, Huesped huesped) {

        Huesped existente = obtenerEntidad(run);

        if (huesped.getNombre() != null)
            existente.setNombre(huesped.getNombre());

        if (huesped.getApellido() != null)
            existente.setApellido(huesped.getApellido());

        if (huesped.getTelefono() != null)
            existente.setTelefono(huesped.getTelefono());

        if (huesped.getCorreo() != null) {

            if (!huesped.getCorreo().equals(existente.getCorreo())
                    && huespedRepository.existsByCorreo(huesped.getCorreo())) {

                throw new RecursoDuplicadoException(
                        "Ya existe un huésped con correo: "
                                + huesped.getCorreo());
            }

            existente.setCorreo(huesped.getCorreo());
        }

        if (huesped.getComunaId() != null)
            existente.setComunaId(huesped.getComunaId());

        Huesped actualizado = huespedRepository.save(existente);

        return convertirADTO(actualizado);
    }

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