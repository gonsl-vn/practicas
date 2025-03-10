package com.viewnext.practica4.service;

import com.viewnext.practica4.models.Director;
import com.viewnext.practica4.models.Productora;
import com.viewnext.practica4.models.Serie;
import com.viewnext.practica4.repositorys.SerieCriteriaRepository;
import com.viewnext.practica4.repositorys.SerieRepository;
import com.viewnext.practica4.services.SerieService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SerieServiceTest {

    @Mock
    private SerieRepository serieRepository;

    @Mock
    private SerieCriteriaRepository serieCriteriaRepository;

    @InjectMocks
    private SerieService serieService;

    private Serie serie1;
    private Serie serie2;
    private Serie serie3;

    @BeforeEach
    void setUp() {
        // Crear instancias de director, productora y actores para las series
        Director director = new Director(1, "Vince", "Gilligan", 57, "EE.UU");
        Productora productora = new Productora(1, "AMC", LocalDate.of(1994, 1, 1));

        // Crear instancias de series de prueba
        serie1 = new Serie(101, "Breaking Bad", LocalDate.of(2008, 1, 20), director, productora, List.of());
        serie2 = new Serie(102, "Better Call Saul", LocalDate.of(2015, 2, 8), director, productora, List.of());
        serie3 = new Serie(103, "The Walking Dead", LocalDate.of(2010, 10, 31), director, productora, List.of());

        // Simular respuestas del repositorio con Mockito
        when(serieRepository.findAll()).thenReturn(Arrays.asList(serie1, serie2, serie3));
        when(serieRepository.findByIdSerie(101)).thenReturn(Optional.of(serie1));
        when(serieRepository.findByIdSerie(102)).thenReturn(Optional.of(serie2));
        when(serieRepository.findByIdSerie(103)).thenReturn(Optional.of(serie3));
        when(serieRepository.findByTitulo("Breaking Bad")).thenReturn(Optional.of(serie1));
        when(serieRepository.save(any(Serie.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @AfterEach
    void tearDown() {
        // Limpiar referencias después de cada prueba
        serie1 = null;
        serie2 = null;
        serie3 = null;

        // Resetear los mocks
        reset(serieRepository);
    }

    // -------------------- TESTS CON JPA REPOSITORY --------------------

    @Test
    void testObtenerSeries() {
        List<Serie> listaSeries = serieService.obtenerSeries();

        assertNotNull(listaSeries);
        assertEquals(3, listaSeries.size());
        assertEquals("Breaking Bad", listaSeries.get(0).getTitulo());
        assertEquals("Better Call Saul", listaSeries.get(1).getTitulo());
        assertEquals("The Walking Dead", listaSeries.get(2).getTitulo());

        verify(serieRepository, times(1)).findAll();
    }

    @Test
    void testObtenerSeriePorId() {
        Optional<Serie> serie = serieService.obtenerSeriePorId(101);

        assertTrue(serie.isPresent());
        assertEquals("Breaking Bad", serie.get().getTitulo());
        assertEquals(2008, serie.get().getAno().getYear());

        verify(serieRepository, times(1)).findByIdSerie(101);
    }

    @Test
    void testObtenerSeriePorTitulo() {
        Optional<Serie> serie = serieService.obtenerSeriePorTitulo("Breaking Bad");

        assertTrue(serie.isPresent());
        assertEquals(2008, serie.get().getAno().getYear());

        verify(serieRepository, times(1)).findByTitulo("Breaking Bad");
    }

    @Test
    void testInsertarSerie() {
        Serie nuevaSerie = new Serie(104, "Game of Thrones", LocalDate.of(2011, 4, 17), null, null, List.of());
        serieService.insertarSerie(nuevaSerie);

        verify(serieRepository, times(1)).save(nuevaSerie);
    }

    @Test
    void testActualizarSerie() {
        Serie serieActualizada = new Serie(101, "Breaking Bad - Extended", LocalDate.of(2008, 1, 20), null, null,
                List.of());

        when(serieRepository.findById(101)).thenReturn(Optional.of(serie1));
        when(serieRepository.save(any(Serie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Serie resultado = serieService.actualizarSerie(101, serieActualizada);

        assertNotNull(resultado);
        assertEquals("Breaking Bad - Extended", resultado.getTitulo());

        verify(serieRepository, times(1)).findById(101);
        verify(serieRepository, times(1)).save(any(Serie.class));
    }

    @Test
    void testEliminarSerie() {
        when(serieRepository.findByIdSerie(101)).thenReturn(Optional.of(serie1));

        serieService.eliminarSerie(101);

        verify(serieRepository, times(1)).delete(serie1);
    }

    // -------------------- TESTS CON CRITERIA API --------------------

    @Test
    void testObtenerSeriesCriteria() {
        when(serieCriteriaRepository.listarSeries()).thenReturn(Arrays.asList(serie1, serie2, serie3));

        List<Serie> listaSeries = serieService.obtenerSeriesCriteria();

        assertNotNull(listaSeries);
        assertEquals(3, listaSeries.size());
        verify(serieCriteriaRepository, times(1)).listarSeries();
    }

    @Test
    void testObtenerSeriePorIdCriteria() {
        when(serieCriteriaRepository.buscarSerie(101)).thenReturn(serie1);

        Serie serie = serieService.obtenerSeriePorIdCriteria(101);

        assertNotNull(serie);
        assertEquals("Breaking Bad", serie.getTitulo());
        verify(serieCriteriaRepository, times(1)).buscarSerie(101);
    }

    @Test
    void testInsertarSerieCriteria() {
        Serie nuevaSerie = new Serie(104, "Game of Thrones", LocalDate.of(2011, 4, 17), null, null, List.of());

        serieService.insertarSerieCriteria(nuevaSerie);

        verify(serieCriteriaRepository, times(1)).insertarSerie(nuevaSerie);
    }

    @Test
    void testActualizarSerieCriteria() {
        Serie serieActualizada = new Serie(101, "Breaking Bad - Extended", LocalDate.of(2008, 1, 20), null, null,
                List.of());

        serieService.actualizarSerieCriteria(101, serieActualizada);

        verify(serieCriteriaRepository, times(1)).actualizarSerie(101, serieActualizada);
    }

    @Test
    void testEliminarSerieCriteria() {
        serieService.eliminarSerieCriteria(101);

        verify(serieCriteriaRepository, times(1)).borrarSeriePorId(101);
    }
}
