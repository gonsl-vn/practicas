package com.viewnext.practica4.repositorys;

import com.viewnext.practica4.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Integer> {

    Optional<Serie> findByNombre(String nombre);

    Optional<Serie> findByIdSerie(int idSerie);
}
