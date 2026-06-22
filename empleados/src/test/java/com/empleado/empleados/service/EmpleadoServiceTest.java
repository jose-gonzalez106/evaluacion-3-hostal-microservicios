package com.empleado.empleados.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.empleado.empleados.DTO.EmpleadoDTO;
import com.empleado.empleados.exceptions.RecursoNoEncontradoException;
import com.empleado.empleados.exceptions.ValidacionException;
import com.empleado.empleados.model.Empleado;
import com.empleado.empleados.model.Hostal;
import com.empleado.empleados.model.TipoEmpleado;
import com.empleado.empleados.repository.EmpleadoRepository;
import com.empleado.empleados.repository.HostalRepository;
import com.empleado.empleados.repository.TipoEmpleadoRepository;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private HostalRepository hostalRepository;

    @Mock
    private TipoEmpleadoRepository tipoEmpleadoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Empleado crearEmpleado() {
        Empleado empleado = new Empleado();
        empleado.setRut("12345678");
        empleado.setNombre(faker.name().firstName());
        empleado.setOcupacion("recepcionista");
        empleado.setTurno("manana");
        return empleado;
    }

    @Test
    public void testBuscarPorRut_Exitoso() {
        //given
        Empleado empleado = crearEmpleado();
        when(empleadoRepository.findById("12345678")).thenReturn(Optional.of(empleado));

        //when
        EmpleadoDTO resultado = empleadoService.buscarPorRut("12345678");

        //then
        assertNotNull(resultado, "el DTO no debe ser nulo");
        assertEquals("12345678", resultado.getRut(), "el RUN debe coincidir");
        verify(empleadoRepository, times(1)).findById("12345678");
    }

    @Test
    public void testBuscarPorRut_NoExiste() {
        // given
        when(empleadoRepository.findById("00000000")).thenReturn(Optional.empty());

        //when then
        assertThrows(RecursoNoEncontradoException.class,
                () -> empleadoService.buscarPorRut("00000000"),
                "debe lanzar excepcion si el empleado no existe");
    }

    @Test
    public void testGuardar_Exitoso() {
        //given
        Empleado empleado = crearEmpleado();
        Hostal hostal = new Hostal();
        hostal.setId(1L);
        TipoEmpleado tipo = new TipoEmpleado();
        tipo.setId(1L);
        tipo.setCategoria("recepcion");

        when(empleadoRepository.existsById("12345678")).thenReturn(false);
        when(hostalRepository.findById(1L)).thenReturn(Optional.of(hostal));
        when(tipoEmpleadoRepository.findById(1L)).thenReturn(Optional.of(tipo));
        when(empleadoRepository.save(any(Empleado.class))).thenAnswer(invocacion -> invocacion.getArgument(0));

        //when
        Empleado resultado = empleadoService.guardar(1L, 1L, empleado);

        //then
        assertNotNull(resultado);
        assertEquals(hostal, resultado.getHostal());
        assertEquals(tipo, resultado.getTipoEmpleado());
        verify(empleadoRepository, times(1)).save(any(Empleado.class));
    }

    @Test
    public void testGuardar_RutDuplicado() {
        //given
        Empleado empleado = crearEmpleado();
        when(empleadoRepository.existsById("12345678")).thenReturn(true);

        //when then
        assertThrows(ValidacionException.class,
                () -> empleadoService.guardar(1L, 1L, empleado),
                "debe lanzar excepcion si el RUN ya existe");
        verify(empleadoRepository, never()).save(any());
    }

    @Test
    public void testGuardar_HostalNoExiste() {
        //given
        Empleado empleado = crearEmpleado();
        when(empleadoRepository.existsById("12345678")).thenReturn(false);
        when(hostalRepository.findById(99L)).thenReturn(Optional.empty());

        //when then
        assertThrows(RecursoNoEncontradoException.class,
                () -> empleadoService.guardar(99L, 1L, empleado));
    }

    @Test
    public void testEliminar_NoExiste() {
        //given
        when(empleadoRepository.findById("000000")).thenReturn(Optional.empty());

        //when then
        assertThrows(RecursoNoEncontradoException.class,
                () -> empleadoService.eliminar("000000"),
                "debe lanzar excepcion si el empleado no existe");
    }
}