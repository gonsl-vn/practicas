package com.viewnext.practicas.P4SeriesYPeliculas.service;

import com.viewnext.practicas.P4SeriesYPeliculas.Service.ActorService;
import com.viewnext.practicas.P4SeriesYPeliculas.Service.DirectorService;
import com.viewnext.practicas.P4SeriesYPeliculas.model.ActorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.DirectorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.PeliculasModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.SeriesModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.ActorEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.ActorCriteriaRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class ActorServiceTest {

    @Mock
    private ActorCriteriaRepository actorCriteriaRepository;

    @InjectMocks
    private ActorService actorService;
    private ActorModel actor1;
    private ActorModel actor2;
    private ActorModel actor3;
    private DirectorService directorService;
    private DirectorModel director1;
    private DirectorModel director2;
    private PeliculasModel pelicula1;
    private PeliculasModel pelicula2;
    private SeriesModel serie1;
    private Pageable pageable;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        director1 = new DirectorModel("55555555F", "alvaro", "alvarez", 20, "serbio", null, null);
        director2 = new DirectorModel("44444444G", "Enrique", "Enriquez", 30, "italiano", null, null);
        pelicula2 = new PeliculasModel(30, "peliTest2", 2024, director1, null, null);
        serie1 = new SeriesModel(30,"serieTest1", 2025, director1, null, null);
        //pageable = PageRequest.of(0, 2, Sort.by("name").ascending());
        director1.setPeliculas(List.of(pelicula2));
        director1.setSeries(List.of(serie1));
        actor1 = new ActorModel("11111111Z", "actorN1", "actorS1", 33,
                "español", List.of(pelicula2), List.of(serie1) );
        actor2 = new ActorModel("22222222Y", "actorN2", "actorS2", 33,
                "español", List.of(pelicula2), List.of(serie1) );

    }

    @Test
    public void testBuscadorActores(){
        when(actorCriteriaRepository.buscarActoresPorCriteria(actor1.getName(),
                actor1.getDni(), actor1.getSurname(), actor1.getAge(),
                actor1.getNationality(), pelicula2.getTitle(), serie1.getTitle())).thenReturn(List.of(actor1));
        Pageable pageable = PageRequest.of(0, 2, Sort.by("name").ascending());

        Page<ActorEntity> result = actorService.buscarActorPorVariosParam( "actorN1", "11111111Z", "actorS1", 33,
                "español", pelicula2.getTitle(), serie1.getTitle(), pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());

    }
}
