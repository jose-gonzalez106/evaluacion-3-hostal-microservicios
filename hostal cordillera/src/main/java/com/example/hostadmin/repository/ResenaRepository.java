package com.example.hostadmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hostadmin.model.Resena;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByHuespedRun(String run);
}
