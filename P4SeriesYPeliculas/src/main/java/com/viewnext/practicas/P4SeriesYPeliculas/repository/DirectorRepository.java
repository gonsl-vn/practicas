package com.viewnext.practicas.P4SeriesYPeliculas.repository;

import com.viewnext.practicas.P4SeriesYPeliculas.model.DirectorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends JpaRepository<DirectorModel, String> {
    DirectorModel findByDni(String dni);
  //  DirectorModel findByPelicula(String pelicula);
    //DirectorModel findBySerie(String serie);
    List<DirectorModel> findAllByOrderByNameAsc();
}
