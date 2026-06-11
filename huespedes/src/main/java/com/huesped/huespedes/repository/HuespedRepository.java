package com.huesped.huespedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huesped.huespedes.model.Huesped;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, String> {

    boolean existsByCorreo(String correo);

}