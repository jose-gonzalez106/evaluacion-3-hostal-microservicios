package com.empleado.empleados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empleado.empleados.model.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {

}
