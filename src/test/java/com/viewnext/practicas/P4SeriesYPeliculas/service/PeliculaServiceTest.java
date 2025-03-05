package com.viewnext.practicas.P4SeriesYPeliculas.service;

import com.viewnext.practicas.P4SeriesYPeliculas.Service.DirectorService;
import com.viewnext.practicas.P4SeriesYPeliculas.Service.PeliculasService;
import com.viewnext.practicas.P4SeriesYPeliculas.model.*;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.DirectorEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.PeliculasEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.DirectorCriteriaRepository;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.PeliculaCriteriaRepository;
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

public class PeliculaServiceTest {

    @Mock
    private PeliculaCriteriaRepository peliculaCriteriaRepository;

    @InjectMocks
    private PeliculasService peliculaService;
    private DirectorModel director1;
    private DirectorModel director2;
    private PeliculasModel pelicula1;
    private PeliculasModel pelicula2;
    private SeriesModel serie1;
    private ProductoraModel productora1;
    private ActorModel actor1;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        actor1 = new ActorModel("66666666H", "actorin", "actorez", 25, "ruso", null, null );
        productora1 = new ProductoraModel(31, "productoraTest1", 2025, null, null);
        director1 = new DirectorModel("55555555F", "alvaro", "alvarez", 20, "serbio", null, null);
        director2 = new DirectorModel("44444444G", "Enrique", "Enriquez", 30, "italiano", null, null);
        pelicula2 = new PeliculasModel(30, "peliTest2", 2024, director1, productora1, null);
        serie1 = new SeriesModel(30,"serieTest1", 2025, director1, null, null);
        //pageable = PageRequest.of(0, 2, Sort.by("name").ascending());
        director1.setPeliculas(List.of(pelicula2));
        director1.setSeries(List.of(serie1));
        pelicula2.setDirector(director1);
        pelicula2.setActores(List.of(actor1));
        pelicula2.setProductora(productora1);
    }

    @Test
    public void testBuscadorPeliculas(){
        when(peliculaCriteriaRepository.buscarPeliculasPorCriteria(pelicula2.getTitle(),
                pelicula2.getCreationYear(), pelicula2.getProductora().getName()
                , pelicula2.getDirector().getName()+" " + pelicula2.getDirector().getSurname(),
                actor1.getName() +" "+ actor1.getSurname())).thenReturn(List.of(pelicula2));
        Pageable pageable = PageRequest.of(0, 2, Sort.by("title").ascending());

        Page<PeliculasEntity> result = peliculaService.buscaPorParametros("peliTest2",
                2024, "productoraTest1", "alvaro alvarez",
                "actorin actorez", pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(pelicula2.getTitle(), result.getContent().get(0).getTitle());
    }

}
