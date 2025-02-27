package com.viewnext.practicas.P4SeriesYPeliculas.repository;

import com.viewnext.practicas.P4SeriesYPeliculas.model.DirectorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<DirectorModel, String> {
    DirectorModel findByDni(String dni);
  //  DirectorModel findByPelicula(String pelicula);
    //DirectorModel findBySerie(String serie);
}
