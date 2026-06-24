package com.huesped.huespedes.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.huesped.huespedes.model.Huesped;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, String> {

    boolean existsByCorreo(String correo);

    Optional<Huesped> findByCorreo(String correo);
}