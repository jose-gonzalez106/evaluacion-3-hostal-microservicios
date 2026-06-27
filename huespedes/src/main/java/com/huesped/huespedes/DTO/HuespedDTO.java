package com.huesped.huespedes.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HuespedDTO {

    private String run;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private Long comunaId;

}