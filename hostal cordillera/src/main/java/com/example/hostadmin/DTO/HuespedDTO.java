package com.example.hostadmin.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HuespedDTO {

    private String run;
    private String nombre;
    private String apellido;

}