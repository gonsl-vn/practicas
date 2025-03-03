package com.viewnext.practicas.P4SeriesYPeliculas.repository;

import com.viewnext.practicas.P4SeriesYPeliculas.model.SeriesModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.SeriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesRepository extends JpaRepository<SeriesModel, Integer> {
    List<SeriesModel> findAllByOrderByTitleAsc();

    SeriesModel findByTitle(String title);

    //SeriesModel findByDirector(String director);
    //SeriesModel findByProductora(String productora);
}
