package com.huesped.huespedes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huesped.huespedes.DTO.HuespedDTO;
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
        log.info("[HuespedService] Obteniendo todos los huespedes");

        return huespedRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public HuespedDTO buscarPorRun(String run) {

        log.info("[HuespedService] Buscando huesped con run: {}", run);

        Huesped huesped = huespedRepository.findById(run)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Huesped con RUN " + run + " no encontrado"));

        return convertirADTO(huesped);
    }

    public Huesped guardar(Huesped huesped) {

        if (huespedRepository.existsById(huesped.getRun())) {
            throw new RuntimeException(
                    "Ya existe un huesped con run: " + huesped.getRun());
        }

        if (huespedRepository.existsByCorreo(huesped.getCorreo())) {
            throw new RuntimeException(
                    "Ya existe un huesped con correo: " + huesped.getCorreo());
        }

        return huespedRepository.save(huesped);
    }

    public Huesped actualizar(String run, Huesped huesped) {

        Huesped existente = huespedRepository.findById(run)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Huesped con RUN " + run + " no encontrado"));

        if (huesped.getNombre() != null) {
            existente.setNombre(huesped.getNombre());
        }

        if (huesped.getApellido() != null) {
            existente.setApellido(huesped.getApellido());
        }

        if (huesped.getTelefono() != null) {
            existente.setTelefono(huesped.getTelefono());
        }

        if (huesped.getCorreo() != null) {

            if (!huesped.getCorreo().equals(existente.getCorreo())
                    && huespedRepository.existsByCorreo(huesped.getCorreo())) {

                throw new RuntimeException(
                        "Ya existe un huesped con correo: "
                                + huesped.getCorreo());
            }

            existente.setCorreo(huesped.getCorreo());
        }

        if (huesped.getComunaId() != null) {
            existente.setComunaId(huesped.getComunaId());
        }

        return huespedRepository.save(existente);
    }

    public String eliminar(String run) {

        Huesped huesped = huespedRepository.findById(run)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Huesped con RUN " + run + " no encontrado"));

        huespedRepository.delete(huesped);

        return "Huesped "
                + huesped.getNombre()
                + " eliminado del registro";
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