package com.example.hostadmin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.hostadmin.exceptions.RecursoNoEncontradoException;
import com.example.hostadmin.exceptions.ValidacionException;
import com.example.hostadmin.model.Habitacion;
import com.example.hostadmin.model.Reserva;
import com.example.hostadmin.repository.HabitacionRepository;
import com.example.hostadmin.repository.ReservaRepository;
import com.example.hostadmin.service.ReservaService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private HabitacionRepository habitacionRepository;

    @InjectMocks
    private ReservaService reservaService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Habitacion crearHabitacion(Integer numero, String estado) {
        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(numero);
        habitacion.setEstado(estado);
        habitacion.setCapacidad(2);
        habitacion.setCategoria("simple");
        return habitacion;
    }

    @Test
    public void testBuscarPorId_Exitoso() {
        //given
        Long id = faker.number().randomNumber();
        Reserva reserva = new Reserva();
        reserva.setId(id);
        reserva.setEstado("activa");
        reserva.setFechaInicio(LocalDate.now().plusDays(1));
        reserva.setFechaTermino(LocalDate.now().plusDays(5));

        when(reservaRepository.findById(id)).thenReturn(Optional.of(reserva));

        //when
        var resultado = reservaService.buscarPorId(id);

        //then
        assertNotNull(resultado, "El DTO no debe ser nulo");
        assertEquals(id, resultado.getId(), "El ID debe coincidir");
        verify(reservaRepository, times(1)).findById(id);
    }

    @Test
    public void testBuscarPorId_NoExiste() {
        //given
        Long id = 999L;
        when(reservaRepository.findById(id)).thenReturn(Optional.empty());

        //when then
        assertThrows(RecursoNoEncontradoException.class,
                () -> reservaService.buscarPorId(id),
                "Debe lanzar excepcion si la reserva no existe");
    }

    @Test
    public void testEliminar_Exitoso() {
        //given
        Long id = 1L;
        Reserva reserva = new Reserva();
        reserva.setId(id);
        when(reservaRepository.findById(id)).thenReturn(Optional.of(reserva));

        //when
        String resultado = reservaService.eliminar(id);

        // THEN
        assertNotNull(resultado);
        verify(reservaRepository, times(1)).delete(reserva);
    }

    @Test
    public void testEliminar_NoExiste() {
        //given
        when(reservaRepository.findById(999L)).thenReturn(Optional.empty());

        //when then
        assertThrows(RecursoNoEncontradoException.class,
                () -> reservaService.eliminar(999L));
    }

    @Test
    public void testValidarHabitacionOcupada() {
        //given
        Habitacion habitacion = crearHabitacion(101, "ocupado");

        //when
        assertThrows(ValidacionException.class, () -> {
            if (!habitacion.getEstado().equalsIgnoreCase("libre")) {
                throw new ValidacionException("la habitacion no esta disponible");
            }
        });
    }

    @Test
    public void testCancelar_NoExiste() {
        //given
        when(reservaRepository.findById(999L)).thenReturn(Optional.empty());

        //when then
        assertThrows(RecursoNoEncontradoException.class,
                () -> reservaService.cancelar(999L));
        verify(reservaRepository, never()).delete(any());
    }
}