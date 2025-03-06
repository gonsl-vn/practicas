package com.viewnext.practicas.P4SeriesYPeliculas.repository;

import com.viewnext.practicas.P4SeriesYPeliculas.model.SeriesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeriesRepository extends JpaRepository<SeriesModel, Integer> {
    List<SeriesModel> findAllByOrderByTitleAsc();

    SeriesModel findByTitle(String title);
    Optional<SeriesModel> findById(Integer id);
    //SeriesModel findByDirector(String director);
    //SeriesModel findByProductora(String productora);
}
