package com.huesped.huespedes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.datafaker.Faker;

import com.huesped.huespedes.DTO.HuespedDTO;
import com.huesped.huespedes.exception.RecursoDuplicadoException;
import com.huesped.huespedes.exception.RecursoNoEncontradoException;
import com.huesped.huespedes.model.Huesped;
import com.huesped.huespedes.repository.HuespedRepository;

@ExtendWith(MockitoExtension.class)
public class HuespedServiceTest {

    @Mock
    private HuespedRepository huespedRepository;

    @InjectMocks
    private HuespedService huespedService;

    private Faker faker = new Faker();

    private Huesped crearHuesped() {
        Huesped huesped = new Huesped();
        huesped.setRun("12345678");
        huesped.setNombre(faker.name().firstName());
        huesped.setApellido(faker.name().lastName());
        huesped.setCorreo(faker.internet().emailAddress());
        huesped.setTelefono("912345678");
        return huesped;
    }

    @Test
    public void testBuscarPorRun_Exitoso() {
        // given
        Huesped huesped = crearHuesped();
        when(huespedRepository.findById("12345678")).thenReturn(Optional.of(huesped));

        // when
        HuespedDTO resultado = huespedService.buscarPorRun("12345678");

        // then
        assertNotNull(resultado);
        assertEquals("12345678", resultado.getRun());
        verify(huespedRepository, times(1)).findById("12345678");
    }

    @Test
    public void testBuscarPorRun_NoExiste() {
        // given
        when(huespedRepository.findById("00000000")).thenReturn(Optional.empty());

        // when then
        assertThrows(RecursoNoEncontradoException.class,
                () -> huespedService.buscarPorRun("00000000"));
    }

    @Test
    public void testGuardar_Exitoso() {
        // given
        Huesped huesped = crearHuesped();
        when(huespedRepository.existsById("12345678")).thenReturn(false);
        when(huespedRepository.existsByCorreo(huesped.getCorreo())).thenReturn(false);
        when(huespedRepository.save(any(Huesped.class))).thenReturn(huesped);

        // when
        HuespedDTO resultado = huespedService.guardar(huesped);

        // then
        assertNotNull(resultado);
        assertEquals("12345678", resultado.getRun());
        verify(huespedRepository, times(1)).save(any(Huesped.class));
    }

    @Test
    public void testGuardar_RunDuplicado() {
        // given
        Huesped huesped = crearHuesped();
        when(huespedRepository.existsById("12345678")).thenReturn(true);

        // when then
        assertThrows(RecursoDuplicadoException.class,
                () -> huespedService.guardar(huesped));
        verify(huespedRepository, never()).save(any());
    }

    @Test
    public void testGuardar_CorreoDuplicado() {
        // given
        Huesped huesped = crearHuesped();
        when(huespedRepository.existsById("12345678")).thenReturn(false);
        when(huespedRepository.existsByCorreo(huesped.getCorreo())).thenReturn(true);

        // when then
        assertThrows(RecursoDuplicadoException.class,
                () -> huespedService.guardar(huesped));
    }

    @Test
    public void testEliminar_NoExiste() {
        // given
        when(huespedRepository.findById("00000000")).thenReturn(Optional.empty());

        // when then
        assertThrows(RecursoNoEncontradoException.class,
                () -> huespedService.eliminar("00000000"));
    }
}