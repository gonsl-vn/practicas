package com.viewnext.practicas.P4SeriesYPeliculas.repository;

import com.viewnext.practicas.P4SeriesYPeliculas.model.PeliculasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeliculasRepository extends JpaRepository<PeliculasModel, String> {
    PeliculasModel findByTitle(String title);
    PeliculasModel findByDirectorName(String directorName);
}
