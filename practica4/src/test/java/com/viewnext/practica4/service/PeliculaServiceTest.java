package com.viewnext.practica4.service;

import com.viewnext.practica4.models.Director;
import com.viewnext.practica4.models.Pelicula;
import com.viewnext.practica4.models.Productora;
import com.viewnext.practica4.repositorys.PeliculaCriteriaRepository;
import com.viewnext.practica4.repositorys.PeliculaRepository;
import com.viewnext.practica4.services.PeliculaService;
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
public class PeliculaServiceTest {

    @Mock
    private PeliculaRepository peliculaRepository;

    @Mock
    private PeliculaCriteriaRepository peliculaCriteriaRepository;

    @InjectMocks
    private PeliculaService peliculaService;

    private Pelicula pelicula1;
    private Pelicula pelicula2;
    private Pelicula pelicula3;

    @BeforeEach
    void setUp() {
        // Crear instancias de directores y productoras para las películas
        Director director = new Director(1, "Christopher", "Nolan", 53, "Reino Unido");
        Productora productora = new Productora(1, "Warner Bros", LocalDate.of(1975, 7, 16));

        // Crear instancias de películas de prueba
        pelicula1 = new Pelicula(101, "Inception", LocalDate.of(2010, 7, 16), director, productora, List.of());
        pelicula2 = new Pelicula(102, "Interstellar", LocalDate.of(2014, 11, 7), director, productora, List.of());
        pelicula3 = new Pelicula(103, "Dunkirk", LocalDate.of(2017, 7, 21), director, productora, List.of());

        // Simular respuestas del repositorio con Mockito
        when(peliculaRepository.findAll()).thenReturn(Arrays.asList(pelicula1, pelicula2, pelicula3));
        when(peliculaRepository.findById(101)).thenReturn(Optional.of(pelicula1));
        when(peliculaRepository.findById(102)).thenReturn(Optional.of(pelicula2));
        when(peliculaRepository.findById(103)).thenReturn(Optional.of(pelicula3));
        when(peliculaRepository.save(any(Pelicula.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @AfterEach
    void tearDown() {
        // Limpiar referencias después de cada prueba
        pelicula1 = null;
        pelicula2 = null;
        pelicula3 = null;

        // Resetear los mocks
        reset(peliculaRepository);
    }
    // -------------------- TESTS CON JPA REPOSITORY --------------------

    @Test
    void testObtenerPeliculas() {
        List<Pelicula> listaPeliculas = peliculaService.listarPeliculas();

        assertNotNull(listaPeliculas);
        assertEquals(3, listaPeliculas.size());

        verify(peliculaRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPeliculasKO() {
        when(peliculaRepository.findAll()).thenReturn(null);

        List<Pelicula> listaPeliculas = peliculaService.listarPeliculas();

        assertNull(listaPeliculas);
        verify(peliculaRepository, times(1)).findAll();
    }

    // --------------------------------------------------

    @Test
    void testObtenerPeliculaPorId() {
        Optional<Pelicula> pelicula = peliculaService.buscarPelicula(101);

        assertTrue(pelicula.isPresent());
        assertEquals("Inception", pelicula.get().getTitulo());

        verify(peliculaRepository, times(1)).findById(101);
    }

    @Test
    void testObtenerPeliculaPorIdKO() {
        when(peliculaRepository.findById(999)).thenReturn(Optional.empty());

        Optional<Pelicula> pelicula = peliculaService.buscarPelicula(999);

        assertFalse(pelicula.isPresent());
        verify(peliculaRepository, times(1)).findById(999);
    }

    // --------------------------------------------------

    @Test
    void testInsertarPelicula() {
        Pelicula nuevaPelicula = new Pelicula(104, "Tenet", LocalDate.of(2020, 8, 26), null, null, List.of());

        peliculaService.insertarPelicula(nuevaPelicula);

        verify(peliculaRepository, times(1)).save(nuevaPelicula);
    }

    @Test
    void testInsertarPeliculaKO() {
        Pelicula nuevaPelicula = new Pelicula(104, "", LocalDate.of(2020, 8, 26), null, null, List.of());

        when(peliculaRepository.save(nuevaPelicula)).thenThrow(
                new IllegalArgumentException("No se puede guardar la película"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            peliculaService.insertarPelicula(nuevaPelicula);
        });

        assertEquals("No se puede guardar la película", exception.getMessage());
    }

    // --------------------------------------------------

    @Test
    void testActualizarPelicula() {
        Pelicula peliculaActualizada = new Pelicula(101, "Inception - Director's Cut", LocalDate.of(2010, 7, 16), null,
                null, List.of());

        when(peliculaRepository.findById(101)).thenReturn(Optional.of(pelicula1));
        when(peliculaRepository.save(any(Pelicula.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pelicula resultado = peliculaService.actualizarPelicula(101, peliculaActualizada);

        assertNotNull(resultado);
        assertEquals("Inception - Director's Cut", resultado.getTitulo());

        verify(peliculaRepository, times(1)).findById(101);
        verify(peliculaRepository, times(1)).save(any(Pelicula.class));
    }

    @Test
    void testActualizarPeliculaKO() {
        Pelicula peliculaActualizada = new Pelicula(101, "", LocalDate.of(2010, 7, 16), null, null, List.of());

        when(peliculaRepository.findById(101)).thenReturn(Optional.ofNullable(pelicula1));
        when(peliculaRepository.save(any(Pelicula.class))).thenThrow(new IllegalArgumentException("Datos inválidos"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            peliculaService.actualizarPelicula(101, peliculaActualizada);
        });

        assertEquals("Datos inválidos", exception.getMessage());
    }

    // --------------------------------------------------

    @Test
    void testEliminarPelicula() {
        when(peliculaRepository.existsById(101)).thenReturn(true);

        peliculaService.borrarPeliculaPorId(101);

        verify(peliculaRepository, times(1)).deleteById(101);
    }

    @Test
    void testEliminarPeliculaKO() {
        when(peliculaRepository.existsById(999)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            peliculaService.borrarPeliculaPorId(999);
        });

        verify(peliculaRepository, times(0)).deleteById(any());
    }

    // -------------------- TESTS CON CRITERIA API --------------------

    @Test
    void testObtenerPeliculasCriteria() {
        when(peliculaCriteriaRepository.listarPeliculas()).thenReturn(Arrays.asList(pelicula1, pelicula2, pelicula3));

        List<Pelicula> listaPeliculas = peliculaService.listarPeliculasCriteria();

        assertNotNull(listaPeliculas);
        assertEquals(3, listaPeliculas.size());
        verify(peliculaCriteriaRepository, times(1)).listarPeliculas();
    }

    @Test
    void testObtenerPeliculasCriteriaKO() {
        when(peliculaCriteriaRepository.listarPeliculas()).thenReturn(null);

        List<Pelicula> listaPeliculas = peliculaService.listarPeliculasCriteria();

        assertNull(listaPeliculas);
        verify(peliculaCriteriaRepository, times(1)).listarPeliculas();
    }

    // --------------------------------------------------

    @Test
    void testObtenerPeliculaPorIdCriteria() {
        when(peliculaCriteriaRepository.buscarPelicula(101)).thenReturn(pelicula1);

        Pelicula pelicula = peliculaService.buscarPeliculaCriteria(101);

        assertNotNull(pelicula);
        assertEquals("Inception", pelicula.getTitulo());
        verify(peliculaCriteriaRepository, times(1)).buscarPelicula(101);
    }

    @Test
    void testObtenerPeliculaPorIdCriteriaKO() {
        when(peliculaCriteriaRepository.buscarPelicula(999)).thenReturn(null);

        Pelicula pelicula = peliculaService.buscarPeliculaCriteria(999);

        assertNull(pelicula);
        verify(peliculaCriteriaRepository, times(1)).buscarPelicula(999);
    }

    // --------------------------------------------------

    @Test
    void testInsertarPeliculaCriteria() {
        Pelicula nuevaPelicula = new Pelicula(104, "Tenet", LocalDate.of(2020, 8, 26), null, null, List.of());

        peliculaService.insertarPeliculaCriteria(nuevaPelicula);

        verify(peliculaCriteriaRepository, times(1)).insertarPelicula(nuevaPelicula);
    }

    @Test
    void testInsertarPeliculaCriteriaKO() {
        Pelicula nuevaPelicula = new Pelicula(104, "", LocalDate.of(2020, 8, 26), null, null, List.of());

        doThrow(new IllegalArgumentException("No se puede guardar la película")).when(peliculaCriteriaRepository)
                .insertarPelicula(nuevaPelicula);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            peliculaService.insertarPeliculaCriteria(nuevaPelicula);
        });

        assertEquals("No se puede guardar la película", exception.getMessage());
    }

    // --------------------------------------------------

    @Test
    void testActualizarPeliculaCriteria() {
        Pelicula peliculaActualizada = new Pelicula(101, "Inception - Director's Cut", LocalDate.of(2010, 7, 16), null,
                null, List.of());

        peliculaService.actualizarPeliculaCriteria(101, peliculaActualizada);

        verify(peliculaCriteriaRepository, times(1)).actualizarPelicula(101, peliculaActualizada);
    }

    @Test
    void testActualizarPeliculaCriteriaKO() {
        Pelicula peliculaActualizada = new Pelicula(101, "", LocalDate.of(2010, 7, 16), null, null, List.of());

        doThrow(new IllegalArgumentException("Datos inválidos")).when(peliculaCriteriaRepository)
                .actualizarPelicula(101, peliculaActualizada);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            peliculaService.actualizarPeliculaCriteria(101, peliculaActualizada);
        });

        assertEquals("Datos inválidos", exception.getMessage());
    }

    // --------------------------------------------------

    @Test
    void testEliminarPeliculaCriteria() {
        peliculaService.borrarPeliculaPorIdCriteria(101);

        verify(peliculaCriteriaRepository, times(1)).borrarPeliculaPorId(101);
    }

    @Test
    void testEliminarPeliculaCriteriaKO() {
        doThrow(new RuntimeException("No se pudo eliminar la película, no encontrada")).when(peliculaCriteriaRepository)
                .borrarPeliculaPorId(999);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            peliculaService.borrarPeliculaPorIdCriteria(999);
        });

        assertEquals("No se pudo eliminar la película, no encontrada", exception.getMessage());
    }
}
