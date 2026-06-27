package com.empleado.empleados.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmpleadoDTO {

    private String rut;
    private String nombre;
    private String ocupacion;
    private String turno;
    private String tipoEmpleado;

}
