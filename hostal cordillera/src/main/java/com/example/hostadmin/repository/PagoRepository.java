package com.example.hostadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.hostadmin.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    boolean existsByReservaId(Long reservaId);
}
