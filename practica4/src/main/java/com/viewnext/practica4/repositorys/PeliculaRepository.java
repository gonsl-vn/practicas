package com.viewnext.practica4.repositorys;

import com.viewnext.practica4.models.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {

    // Buscar película por título
    Optional<Pelicula> findByTitulo(String titulo);

    // Buscar películas por año
    List<Pelicula> findByAno(int ano);

    // Buscar películas de una productora específica
    List<Pelicula> findByProductoraIdProductora(int idProductora);

    // Buscar películas dirigidas por un director específico
    List<Pelicula> findByDirectorIdDirector(int idDirector);
}
