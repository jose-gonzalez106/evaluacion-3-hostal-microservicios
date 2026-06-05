package com.example.hostadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.hostadmin.model.Empleado;
// id es rut, por eso es String
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {

}
