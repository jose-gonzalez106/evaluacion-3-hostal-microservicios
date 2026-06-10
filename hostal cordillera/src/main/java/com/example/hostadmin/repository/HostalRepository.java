package com.example.hostadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.hostadmin.model.Hostal;

@Repository
public interface HostalRepository extends JpaRepository<Hostal, Long>  {
    boolean existsByRutEmpresa(String rutEmpresa);

}
