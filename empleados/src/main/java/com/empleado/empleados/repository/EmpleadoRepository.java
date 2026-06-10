package com.empleado.empleados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD:empleados/src/main/java/com/empleado/empleados/repository/EmpleadoRepository.java

import com.empleado.empleados.model.Empleado;
=======
import org.springframework.stereotype.Repository;
import com.example.hostadmin.model.Empleado;
>>>>>>> origin/javier:hostal cordillera/src/main/java/com/example/hostadmin/repository/EmpleadoRepository.java
// id es rut, por eso es String
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {

}
