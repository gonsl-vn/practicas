package com.example.practica52.repository;

import com.example.practica52.model.Distrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistritoRepository extends JpaRepository<Distrito, Integer> {
    Distrito findByNombreDistrito(String nombresDistritos);
}
