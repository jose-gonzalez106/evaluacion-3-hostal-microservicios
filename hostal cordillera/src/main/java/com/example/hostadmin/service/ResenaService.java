package com.example.hostadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hostadmin.DTO.HuespedDTO;
import com.example.hostadmin.DTO.ResenniaDTO;
import com.example.hostadmin.client.HuespedClient;
import com.example.hostadmin.exceptions.RecursoNoEncontradoException;
import com.example.hostadmin.exceptions.ValidacionException;
import com.example.hostadmin.model.Hostal;
import com.example.hostadmin.model.Resena;
import com.example.hostadmin.repository.HostalRepository;
import com.example.hostadmin.repository.ResenaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private HuespedClient huespedClient;

    @Autowired
    private HostalRepository hostalRepository;

    public List<ResenniaDTO> obtenerTodas() {
        log.info("[ResenaService] Obteniendo todas las resenas");
        return resenaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public ResenniaDTO buscarPorId(Long id) {
        log.info("[ResenaService] Buscando resena con id: {}", id);
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("[ResenaService] Resena {} no encontrada", id);
                    return new RecursoNoEncontradoException("resena " + id + " no encontrada");
                });
        return convertirADTO(resena);
    }

    public List<ResenniaDTO> buscarPorHuesped(String run) {
        log.info("[ResenaService] Buscando resenas del huesped: {}", run);
        return resenaRepository.findAll().stream()
                .filter(r -> r.getHuespedRun() != null && r.getHuespedRun().equals(run))
                .map(this::convertirADTO)
                .toList();
    }

    public Resena guardar(String huespedRun, Long hostalId, Resena resena) {
        log.info("[ResenaService] Guardando reseña del huesped: {} en hostal {}", huespedRun, hostalId);

        HuespedDTO huesped = huespedClient.buscarPorRun(huespedRun);

        Hostal hostal = hostalRepository.findById(hostalId)
                .orElseThrow(() -> new RecursoNoEncontradoException("el hostal " + hostalId + " no existe"));

        if (resena.getComentario() == null || resena.getComentario().isBlank()) {
            throw new ValidacionException("el comentario no puede estar vacío");
        }

        resena.setHuespedRun(huesped.getRun());
        resena.setHostal(hostal);

        Resena guardada = resenaRepository.save(resena);
        log.info("[ResenaService] Reseña guardada con id: {}", guardada.getId());
        return guardada;
    }

    public String eliminar(Long id) {
        log.info("[ResenaService] Eliminando resena id: {}", id);
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("[ResenaService] Resena {} no encontrada para eliminar", id);
                    return new RecursoNoEncontradoException("resena " + id + " no encontrada");
                });
        resenaRepository.delete(resena);
        log.info("[ResenaService] Resena {} eliminada", id);
        return "resena: " + id + " eliminada";
    }

    private ResenniaDTO convertirADTO(Resena resena) {
        ResenniaDTO dto = new ResenniaDTO();
        dto.setId(resena.getId());
        dto.setComentario(resena.getComentario());
        dto.setCalificacion(resena.getCalificacion());
        dto.setFecha(resena.getFecha());
        dto.setHuesped(resena.getHuespedRun());
        if (resena.getHostal() != null) {
            dto.setHostal(resena.getHostal().getNombre());
        }
        return dto;
    }
}