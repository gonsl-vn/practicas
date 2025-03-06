package com.viewnext.practicas.P4SeriesYPeliculas.service;

import com.viewnext.practicas.P4SeriesYPeliculas.Service.ProductoraService;
import com.viewnext.practicas.P4SeriesYPeliculas.Service.SeriesService;
import com.viewnext.practicas.P4SeriesYPeliculas.model.*;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.SeriesEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.ProductoraCriteriaRepository;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.SeriesCriteriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class SeriesServiceTest {
    @Mock
    private SeriesCriteriaRepository seriesCriteriaRepository;

    @InjectMocks
    private SeriesService seriesService;
    private DirectorModel director1;
    private DirectorModel director2;
    private PeliculasModel pelicula1;
    private PeliculasModel pelicula2;
    private SeriesModel serie1;
    private SeriesModel serie2;

    private ProductoraModel productora1;
    private ActorModel actor1;
    private ActorModel actor2;
    private ProductoraModel productora2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        actor1 = new ActorModel("66666666H", "actorin", "actorez", 25, "ruso", null, null);
        actor2 = new ActorModel("77777777J", "actoron", "actorano", 35, "aleman", null, null);

        productora1 = new ProductoraModel(31, "productoraTest1", 2025, null, null);
        director1 = new DirectorModel("55555555F", "alvaro", "alvarez", 20, "serbio", null, null);
        director2 = new DirectorModel("44444444G", "Enrique", "Enriquez", 30, "italiano", null, null);
        pelicula2 = new PeliculasModel(30, "peliTest2", 2024, director1, productora1, null);
        serie1 = new SeriesModel(30, "serieTest1", 2025, director1, null, null);
        serie2 = new SeriesModel(40, "serieTest2", 2024, director2, productora1, List.of(actor1, actor2));

        director1.setPeliculas(List.of(pelicula2));
        director1.setSeries(List.of(serie1));
        pelicula2.setDirector(director1);
        pelicula2.setActores(List.of(actor1));
        pelicula2.setProductora(productora1);
        productora1.setPeliculas(List.of(pelicula2));
        productora1.setSeries(List.of(serie1));
        serie1.setDirector(director1);
        serie1.setActores(List.of(actor1, actor2));
        serie1.setProductora(productora1);
        serie2.setDirector(director2);
        serie2.setActores(List.of(actor2));
        serie2.setProductora(productora2);
    }

    public void testBuscadorSeries() {
        when(seriesCriteriaRepository.buscarSeriesPorCriteria(serie1.getTitle(),
                serie1.getCreationYear(),serie1.getProductora().getName(),
                serie1.getDirector().getName() + " " +
                serie1.getDirector().getSurname(),
                actor1.getName() + " " + actor1.getSurname()))
                .thenReturn(List.of(serie1));
        Pageable pageable = PageRequest.of(0, 2, Sort.by("title").ascending());

        Page<SeriesEntity> result = seriesService.buscaPorParametros(
                 "serieTest1", 2025, productora1.getName(), director1.getName()
                , actor1.getName(), pageable
        );
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(serie1.getTitle(), result.getContent().get(0).getTitle());
    }
}