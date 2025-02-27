package com.viewnext.practicas.P4SeriesYPeliculas.repository;

import com.viewnext.practicas.P4SeriesYPeliculas.model.ActorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRespository extends JpaRepository<ActorModel, String> {
    ActorModel findByDni(String dni);
    //List<ActorModel> findByPeliculaTitle(String peliculaTitle);
   // ActorModel findBySerie(String serie);
}
