package com.viewnext.practicas.P4SeriesYPeliculas.service;

import com.viewnext.practicas.P4SeriesYPeliculas.Service.ProductoraService;
import com.viewnext.practicas.P4SeriesYPeliculas.model.*;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.ProductoraEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.ProductoraCriteriaRepository;
import org.hibernate.Internal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

public class ProductoraServiceTest {
    @Mock
    private ProductoraCriteriaRepository productoraCriteriaRepository;

    @InjectMocks
    private ProductoraService productoraService;
    private DirectorModel director1;
    private DirectorModel director2;
    private PeliculasModel pelicula1;
    private PeliculasModel pelicula2;
    private SeriesModel serie1;
    private SeriesModel serie2;
    private SeriesModel serie3;
    private ProductoraModel productora1;
    private ActorModel actor1;
    private ActorModel actor2;
    private ProductoraModel productora2;
    private ProductoraModel productora3;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        actor1 = new ActorModel("66666666H", "actorin", "actorez", 25, "ruso", null, null );
        actor2 = new ActorModel("77777777J", "actoron", "actorano", 35, "aleman", null, null );

        productora1 = new ProductoraModel(31, "productoraTest1", 2025, null, null);
        productora2 = new ProductoraModel(32, "productoraTest1", 2025, null, null);
        productora3 = new ProductoraModel(33, "productoraTest1", 2025, null, null);

        director1 = new DirectorModel("55555555F", "alvaro", "alvarez", 20, "serbio", null, null);
        director2 = new DirectorModel("44444444G", "Enrique", "Enriquez", 30, "italiano", null, null);

        pelicula2 = new PeliculasModel(30, "peliTest2", 2024, director1, productora1, null);

        serie1 = new SeriesModel(30,"serieTest1", 2025, director1, null, null);
        serie2 = new SeriesModel(40,"serieTest2", 2024, director2, productora1, List.of(actor1,actor2));
        serie3 = new SeriesModel(50,"serieTest3", 2024, director2, productora1, List.of(actor1,actor2));

        //pageable = PageRequest.of(0, 2, Sort.by("name").ascending());
        director1.setPeliculas(List.of(pelicula2));
        director1.setSeries(List.of(serie1));
        pelicula2.setDirector(director1);
        pelicula2.setActores(List.of(actor1));
        pelicula2.setProductora(productora1);
        productora1.setPeliculas(List.of(pelicula2));
        productora1.setSeries(List.of(serie1));
        productora2.setPeliculas(List.of(pelicula2));
        productora2.setSeries(List.of(serie1));
        productora3.setPeliculas(List.of(pelicula2));
        productora3.setSeries(List.of(serie1));

    }

    @Test
    public void testBuscadorProductora(){
        when(productoraCriteriaRepository.buscarProductoraPorCriteria(productora1.getName(),
                productora1.getFoundedInYear(), pelicula2.getTitle(), serie1.getTitle()))
                .thenReturn(List.of(productora1));
        Pageable pageable = PageRequest.of(0, 2, Sort.by("name").ascending());

        Page<ProductoraEntity> result = productoraService.buscarProductoraPorParametros(
                 "productoraTest1", 2025, pelicula2.getTitle(), serie1.getTitle(), pageable
        );

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(productora1.getName(), result.getContent().get(0).getName());
    }/*
    @Test
    public void testPaginacion(){
        Pageable pageable = PageRequest.of(0, 1, Sort.by("name").ascending());

        Page<ProductoraEntity> result = productoraService.buscarProductoraPorParametros("productora",
                2025, pelicula2.getTitle(), serie1.getTitle(), pageable);
        assertNotNull(result);
        assertEquals(3, result.getTotalElements());
        assertEquals(1, result.getContent().size());

    }*/
}
