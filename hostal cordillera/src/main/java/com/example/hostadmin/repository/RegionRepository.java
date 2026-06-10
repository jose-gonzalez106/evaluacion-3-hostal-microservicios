package com.example.hostadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.hostadmin.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    boolean existsByNombre(String nombre);
}
