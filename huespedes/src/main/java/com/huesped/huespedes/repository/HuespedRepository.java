package com.huesped.huespedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huesped.huespedes.model.Huesped;
//mismo caso de empleado, el id es rut, por eso es String
public interface HuespedRepository extends JpaRepository<Huesped, String> {
    boolean existsByCorreo(String correo);
}
