package com.empleado.empleados.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empleado.empleados.model.TipoEmpleado;

public interface TipoEmpleadoRepository extends JpaRepository<TipoEmpleado, Long> {

}
