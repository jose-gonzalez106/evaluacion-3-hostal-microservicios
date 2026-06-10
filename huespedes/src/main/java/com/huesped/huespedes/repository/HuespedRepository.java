package com.huesped.huespedes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD:huespedes/src/main/java/com/huesped/huespedes/repository/HuespedRepository.java

import com.huesped.huespedes.model.Huesped;
=======
import org.springframework.stereotype.Repository;
import com.example.hostadmin.model.Huesped;
>>>>>>> origin/javier:hostal cordillera/src/main/java/com/example/hostadmin/repository/HuespedRepository.java
//mismo caso de empleado, el id es rut, por eso es String
@Repository
public interface HuespedRepository extends JpaRepository<Huesped, String> {
    boolean existsByCorreo(String correo);
}
