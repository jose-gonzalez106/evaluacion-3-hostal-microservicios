package com.example.hostadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hostadmin.model.TipoEmpleado;

@Repository
public interface TipoEmpleadoRepository extends JpaRepository<TipoEmpleado, Long> {
    boolean existsByCategoria(String categoria);
}


