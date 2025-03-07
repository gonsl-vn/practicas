package com.viewnext.practicas.P4SeriesYPeliculas.service;

import com.viewnext.practicas.P4SeriesYPeliculas.Service.DirectorService;
import com.viewnext.practicas.P4SeriesYPeliculas.clients.UserClient;
import com.viewnext.practicas.P4SeriesYPeliculas.model.DirectorModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.PeliculasModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.SeriesModel;
import com.viewnext.practicas.P4SeriesYPeliculas.model.entity.DirectorEntity;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.DirectorCriteriaRepository;
import com.viewnext.practicas.P4SeriesYPeliculas.repository.DirectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DirectorServiceTest {

    @Mock
    private DirectorCriteriaRepository directorCriteriaRepository;

    @Mock
    private DirectorRepository directorRepository;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private DirectorService directorService;
    private DirectorModel director1;
    private DirectorModel director2;
    private PeliculasModel pelicula1;
    private PeliculasModel pelicula2;
    private SeriesModel serie1;
    private Pageable pageable;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        director1 = new DirectorModel("55555555F", "alvaro", "alvarez", 20, "serbio", null, null);
        director2 = new DirectorModel("44444444G", "Enrique", "Enriquez", 30, "italiano", null, null);
        pelicula2 = new PeliculasModel(30, "peliTest2", 2024, director1, null, null);
        serie1 = new SeriesModel(30,"serieTest1", 2025, director1, null, null);
        //pageable = PageRequest.of(0, 2, Sort.by("name").ascending());
        director1.setPeliculas(List.of(pelicula2));
        director1.setSeries(List.of(serie1));
    }
        @Test
        public void testBuscadorDirectores(){
            when(directorCriteriaRepository.buscarDirectoresPorCriteria(director1.getDni(),
                    director1.getName(),director1.getSurname(),director1.getAge(), director1.getNationality(),
                    pelicula2.getTitle(),serie1.getTitle() )).thenReturn(List.of(director1));
            Pageable pageable = PageRequest.of(0, 2, Sort.by("name").ascending());

            Page<DirectorEntity> result = directorService.pruebaBusqueda("55555555F",
                    "alvaro", "alvarez", 20, "serbio",
                    pelicula2.getTitle(),serie1.getTitle() , pageable);
            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
            assertEquals(1, result.getContent().size());
            assertEquals("alvaro", result.getContent().get(0).getName());
        }
        @Test
        public void director_addDirectorTest(){
            when(directorRepository.save(any(DirectorModel.class))).thenReturn(director1);
            when(userClient.existeUsuario(anyString())).thenReturn(true);

            DirectorModel result = directorService.addDirector(director1);

            assertNotNull(result);
             assertEquals(director1, result);
        }

        @Test
        public void director_deleteDirectorTest(){
            when(directorRepository.findByDni("55555555F")).thenReturn(director1);
            doNothing().when(directorRepository).delete(director1);

            assertDoesNotThrow(()-> directorService.deleteDirector(director1.getDni()));
            verify(directorRepository, times(1)).delete(director1);
        }

}
