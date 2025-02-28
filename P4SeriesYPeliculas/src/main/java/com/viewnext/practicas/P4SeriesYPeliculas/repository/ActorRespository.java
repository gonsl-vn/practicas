package com.viewnext.practicas.P4SeriesYPeliculas.repository;

import com.viewnext.practicas.P4SeriesYPeliculas.model.ActorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.ActorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRespository extends JpaRepository<ActorModel, String> {
    List<ActorModel> findAllByOrderByNameAsc();
    Page<ActorModel> findAllByPeliculasIdOrderByNameAsc(Integer peliculaId, Pageable pageable);

    ActorModel findByDni(String dni);
    //List<ActorModel> findByPeliculaTitle(String peliculaTitle);

}
