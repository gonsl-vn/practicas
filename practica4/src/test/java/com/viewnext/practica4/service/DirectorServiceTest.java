package com.viewnext.practica4.service;

import com.viewnext.practica4.models.Director;
import com.viewnext.practica4.repositorys.DirectorCriteriaRepository;
import com.viewnext.practica4.repositorys.DirectorRepository;
import com.viewnext.practica4.services.DirectorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DirectorServiceTest {

    @Mock
    private DirectorRepository directorRepository;

    @Mock
    private DirectorCriteriaRepository directorCriteriaRepository;

    @InjectMocks
    private DirectorService directorService;

    private Director director1;
    private Director director2;
    private Director director3;

    @BeforeEach
    void setUp() {
        // Crear instancias de directores de prueba
        director1 = new Director(200, "Steven", "Spielberg", 77, "EE.UU");
        director2 = new Director(201, "Christopher", "Nolan", 53, "Reino Unido");
        director3 = new Director(202, "Quentin", "Tarantino", 60, "EE.UU");

        // Simular respuestas del repositorio con Mockito
        when(directorRepository.findAll()).thenReturn(Arrays.asList(director1, director2, director3));
        when(directorRepository.findByIdDirector(200)).thenReturn(Optional.of(director1));
        when(directorRepository.findByIdDirector(201)).thenReturn(Optional.of(director2));
        when(directorRepository.findByIdDirector(202)).thenReturn(Optional.of(director3));
        when(directorRepository.save(any(Director.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(directorService.obtenerDirectorPorId(200)).thenReturn(Optional.of(director1));
        when(directorService.obtenerDirectorPorId(201)).thenReturn(Optional.of(director2));
        when(directorService.obtenerDirectorPorId(202)).thenReturn(Optional.of(director3));
    }

    @AfterEach
    void tearDown() {
        // Limpiar referencias después de cada prueba
        director1 = null;
        director2 = null;
        director3 = null;

        // Resetear los mocks
        reset(directorRepository);
    }

    // -------------------- TESTS CON JPA REPOSITORY --------------------

    @Test
    void testObtenerDirectores() {
        List<Director> listaDirectores = directorService.obtenerDirectores();

        assertNotNull(listaDirectores);
        assertEquals(3, listaDirectores.size());

        verify(directorRepository, times(1)).findAll();
    }

    @Test
    void testObtenerDirectoresKO() {
        when(directorRepository.findAll()).thenReturn(null);

        List<Director> listaDirectores = directorService.obtenerDirectores();

        assertNull(listaDirectores);
        verify(directorRepository, times(1)).findAll();
    }

    // --------------------------------------------------

    @Test
    void testObtenerDirectorPorId() {
        Optional<Director> director = directorService.obtenerDirectorPorId(200);

        assertTrue(director.isPresent());
        assertEquals("Steven", director.get().getNombre());

        verify(directorRepository, times(1)).findByIdDirector(200);
    }

    @Test
    void testObtenerDirectorPorIdKO() {
        when(directorRepository.findByIdDirector(200)).thenReturn(Optional.empty());

        Optional<Director> director = directorService.obtenerDirectorPorId(200);

        assertFalse(director.isPresent());
        verify(directorRepository, times(1)).findByIdDirector(200);
    }

    // --------------------------------------------------

    @Test
    void testInsertarDirector() {
        Director nuevoDirector = new Director(203, "James", "Cameron", 69, "Canadá");

        directorService.insertarDirector(nuevoDirector);

        verify(directorRepository, times(1)).save(nuevoDirector);
    }

    @Test
    void testInsertarDirectorKO() {
        Director nuevoDirector = new Director(203, "", "Cameron", 69, "Canadá");

        when(directorRepository.save(nuevoDirector)).thenThrow(
                new IllegalArgumentException("No se puede guardar director"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            directorService.insertarDirector(nuevoDirector);
        });

        assertEquals("No se puede guardar director", exception.getMessage());
    }

    // --------------------------------------------------

    @Test
    void testActualizarDirector() {
        Director directorActualizado = new Director(200, "Steven", "Spielberg", 78, "EE.UU");

        when(directorRepository.findByIdDirector(200)).thenReturn(Optional.of(director1));
        when(directorRepository.save(any(Director.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Director resultado = directorService.actualizarDirector(200, directorActualizado);

        assertNotNull(resultado);
        assertEquals("Steven", resultado.getNombre());

        verify(directorRepository, times(1)).findByIdDirector(200);
        verify(directorRepository, times(1)).save(any(Director.class));
    }

    @Test
    void testActualizarDirectorKO() {
        Director directorActualizado = new Director(200, "", "Spielberg", 78, "EE.UU");

        when(directorRepository.findByIdDirector(200)).thenReturn(Optional.ofNullable(director1));
        when(directorRepository.save(any(Director.class))).thenThrow(new IllegalArgumentException("Datos inválidos"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            directorService.actualizarDirector(200, directorActualizado);
        });

        assertEquals("Datos inválidos", exception.getMessage());
    }

    // --------------------------------------------------

    @Test
    void testEliminarDirector() {
        when(directorRepository.findByIdDirector(200)).thenReturn(Optional.of(director1));

        directorService.eliminarDirector(200);

        verify(directorRepository, times(1)).delete(director1);
    }

    @Test
    void testEliminarDirectorKO() {
        when(directorRepository.findByIdDirector(200)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            directorService.eliminarDirector(200);
        });

        verify(directorRepository, times(0)).delete(any());
    }

    // -------------------- TESTS CON CRITERIA API --------------------

    @Test
    void testObtenerDirectoresCriteria() {
        when(directorCriteriaRepository.listarDirectores()).thenReturn(Arrays.asList(director1, director2, director3));

        List<Director> listaDirectores = directorService.obtenerDirectoresCriteria();

        assertNotNull(listaDirectores);
        assertEquals(3, listaDirectores.size());
        verify(directorCriteriaRepository, times(1)).listarDirectores();
    }

    @Test
    void testObtenerDirectoresCriteriaKO() {
        when(directorCriteriaRepository.listarDirectores()).thenReturn(null);

        List<Director> listaDirectores = directorService.obtenerDirectoresCriteria();

        assertNull(listaDirectores);
        verify(directorCriteriaRepository, times(1)).listarDirectores();
    }

    // --------------------------------------------------

    @Test
    void testObtenerDirectorPorIdCriteria() {
        when(directorCriteriaRepository.buscarDirector(200)).thenReturn(Optional.of(director1));

        Optional<Director> director = directorService.obtenerDirectorPorIdCriteria(200);

        assertTrue(director.isPresent());
        assertEquals("Steven", director.get().getNombre());

        verify(directorCriteriaRepository, times(1)).buscarDirector(200);
    }

    @Test
    void testObtenerDirectorPorIdCriteriaKO() {
        when(directorCriteriaRepository.buscarDirector(200)).thenReturn(Optional.empty());

        Optional<Director> director = directorService.obtenerDirectorPorIdCriteria(200);

        assertFalse(director.isPresent());
        verify(directorCriteriaRepository, times(1)).buscarDirector(200);
    }

    // --------------------------------------------------

    @Test
    void testActualizarDirectorCriteria() {
        Director directorActualizado = new Director(200, "Steven", "Spielberg", 78, "EE.UU");

        directorService.actualizarDirectorCriteria(200, directorActualizado);

        verify(directorCriteriaRepository, times(1)).actualizarDirector(200, directorActualizado);
    }

    @Test
    void testActualizarDirectorCriteriaKO() {
        Director directorActualizado = new Director(200, "", "Spielberg", 78, "EE.UU");

        doThrow(new IllegalArgumentException("Datos inválidos")).when(directorCriteriaRepository)
                .actualizarDirector(200, directorActualizado);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            directorService.actualizarDirectorCriteria(200, directorActualizado);
        });

        assertEquals("Datos inválidos", exception.getMessage());
    }

    // --------------------------------------------------

    @Test
    void testEliminarDirectorCriteria() {
        directorService.eliminarDirectorCriteria(200);

        verify(directorCriteriaRepository, times(1)).borrarDirectorPorId(200);
    }

    @Test
    void testEliminarDirectorCriteriaKO() {
        doThrow(new RuntimeException("No se pudo eliminar director, Director no encontrado")).when(
                directorCriteriaRepository).borrarDirectorPorId(200);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            directorService.eliminarDirectorCriteria(200);
        });

        assertEquals("No se pudo eliminar director, Director no encontrado", exception.getMessage());
    }

}
