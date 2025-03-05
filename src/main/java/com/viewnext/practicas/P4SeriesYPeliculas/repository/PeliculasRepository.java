package com.viewnext.practicas.P4SeriesYPeliculas.repository;

import com.viewnext.practicas.P4SeriesYPeliculas.model.PeliculasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculasRepository extends JpaRepository<PeliculasModel, String> {
    PeliculasModel findByTitle(String title);
    PeliculasModel findById(Integer id);
    List<PeliculasModel> findAllByOrderByTitleAsc();

    String id(Integer id);
    // PeliculasModel findByDirectorName(String directorName);
}
