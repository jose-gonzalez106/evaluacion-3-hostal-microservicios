package com.huesped.huespedes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.huesped.huespedes.DTO.HuespedDTO;
import com.huesped.huespedes.exception.RecursoDuplicadoException;
import com.huesped.huespedes.exception.RecursoNoEncontradoException;
import com.huesped.huespedes.model.Huesped;
import com.huesped.huespedes.repository.HuespedRepository;

@ExtendWith(MockitoExtension.class)
class HuespedServiceTest {

    @Mock
    private HuespedRepository huespedRepository;

    @InjectMocks
    private HuespedService huespedService;

    private Huesped huesped;

    @BeforeEach
    void setUp() {
        huesped = new Huesped();
        huesped.setRun("12345678-9");
        huesped.setNombre("Juan");
        huesped.setApellido("Perez");
        huesped.setTelefono("987654321");
        huesped.setCorreo("juan@correo.com");
        huesped.setComunaId(1L);
    }

    // ─── obtenerTodos ───────────────────────────────────────────────────────────

    @Test
    void obtenerTodos_retornaListaDeHuespedes() {
        // Given
        when(huespedRepository.findAll()).thenReturn(List.of(huesped));

        // When
        List<HuespedDTO> resultado = huespedService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("12345678-9", resultado.get(0).getRun());
        verify(huespedRepository).findAll();
    }

    @Test
    void obtenerTodos_retornaListaVaciaSiNoHayHuespedes() {
        // Given
        when(huespedRepository.findAll()).thenReturn(List.of());

        // When
        List<HuespedDTO> resultado = huespedService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ─── buscarPorRun ───────────────────────────────────────────────────────────

    @Test
    void buscarPorRun_retornaHuespedCuandoExiste() {
        // Given
        when(huespedRepository.findById("12345678-9")).thenReturn(Optional.of(huesped));

        // When
        HuespedDTO resultado = huespedService.buscarPorRun("12345678-9");

        // Then
        assertNotNull(resultado);
        assertEquals("12345678-9", resultado.getRun());
        assertEquals("Juan", resultado.getNombre());
        assertEquals("juan@correo.com", resultado.getCorreo());
    }

    @Test
    void buscarPorRun_lanzaExcepcionCuandoNoExiste() {
        // Given
        when(huespedRepository.findById("99999999-9")).thenReturn(Optional.empty());

        // When / Then
        assertThrows(RecursoNoEncontradoException.class,
                () -> huespedService.buscarPorRun("99999999-9"));
    }

    // ─── guardar ────────────────────────────────────────────────────────────────

    @Test
    void guardar_guardaHuespedCorrectamente() {
        // Given
        when(huespedRepository.existsById(huesped.getRun())).thenReturn(false);
        when(huespedRepository.existsByCorreo(huesped.getCorreo())).thenReturn(false);
        when(huespedRepository.save(huesped)).thenReturn(huesped);

        // When
        HuespedDTO resultado = huespedService.guardar(huesped);

        // Then
        assertNotNull(resultado);
        assertEquals("12345678-9", resultado.getRun());
        assertEquals("Juan", resultado.getNombre());
        verify(huespedRepository).save(huesped);
    }

    @Test
    void guardar_lanzaExcepcionSiRunDuplicado() {
        // Given
        when(huespedRepository.existsById(huesped.getRun())).thenReturn(true);

        // When / Then
        assertThrows(RecursoDuplicadoException.class,
                () -> huespedService.guardar(huesped));
        verify(huespedRepository, never()).save(any());
    }

    @Test
    void guardar_lanzaExcepcionSiCorreoDuplicado() {
        // Given
        when(huespedRepository.existsById(huesped.getRun())).thenReturn(false);
        when(huespedRepository.existsByCorreo(huesped.getCorreo())).thenReturn(true);

        // When / Then
        assertThrows(RecursoDuplicadoException.class,
                () -> huespedService.guardar(huesped));
        verify(huespedRepository, never()).save(any());
    }

    // ─── actualizar ─────────────────────────────────────────────────────────────

    @Test
    void actualizar_actualizaNombreYApellido() {
        // Given
        Huesped datosNuevos = new Huesped();
        datosNuevos.setNombre("Carlos");
        datosNuevos.setApellido("Lopez");

        when(huespedRepository.findById("12345678-9")).thenReturn(Optional.of(huesped));
        when(huespedRepository.save(any(Huesped.class))).thenReturn(huesped);

        // When
        HuespedDTO resultado = huespedService.actualizar("12345678-9", datosNuevos);

        // Then
        assertNotNull(resultado);
        verify(huespedRepository).save(any(Huesped.class));
    }

    @Test
    void actualizar_lanzaExcepcionSiNuevoCorreoYaDuplicado() {
        // Given
        Huesped datosNuevos = new Huesped();
        datosNuevos.setCorreo("otro@correo.com");

        when(huespedRepository.findById("12345678-9")).thenReturn(Optional.of(huesped));
        when(huespedRepository.existsByCorreo("otro@correo.com")).thenReturn(true);

        // When / Then
        assertThrows(RecursoDuplicadoException.class,
                () -> huespedService.actualizar("12345678-9", datosNuevos));
        verify(huespedRepository, never()).save(any());
    }

    @Test
    void actualizar_permiteActualizarConElMismoCorreo() {
        // Given
        Huesped datosNuevos = new Huesped();
        datosNuevos.setCorreo("juan@correo.com"); // mismo correo que ya tiene

        when(huespedRepository.findById("12345678-9")).thenReturn(Optional.of(huesped));
        when(huespedRepository.save(any(Huesped.class))).thenReturn(huesped);

        // When
        HuespedDTO resultado = huespedService.actualizar("12345678-9", datosNuevos);

        // Then
        assertNotNull(resultado);
        verify(huespedRepository, never()).existsByCorreo(any()); // no verifica duplicado
        verify(huespedRepository).save(any());
    }

    @Test
    void actualizar_lanzaExcepcionSiHuespedNoExiste() {
        // Given
        when(huespedRepository.findById("00000000-0")).thenReturn(Optional.empty());

        // When / Then
        assertThrows(RecursoNoEncontradoException.class,
                () -> huespedService.actualizar("00000000-0", new Huesped()));
    }

    // ─── eliminar ───────────────────────────────────────────────────────────────

    @Test
    void eliminar_eliminaHuespedCorrectamente() {
        // Given
        when(huespedRepository.findById("12345678-9")).thenReturn(Optional.of(huesped));

        // When
        String resultado = huespedService.eliminar("12345678-9");

        // Then
        assertEquals("Huésped eliminado correctamente", resultado);
        verify(huespedRepository).delete(huesped);
    }

    @Test
    void eliminar_lanzaExcepcionSiHuespedNoExiste() {
        // Given
        when(huespedRepository.findById("99999999-9")).thenReturn(Optional.empty());

        // When / Then
        assertThrows(RecursoNoEncontradoException.class,
                () -> huespedService.eliminar("99999999-9"));
        verify(huespedRepository, never()).delete(any());
    }
}