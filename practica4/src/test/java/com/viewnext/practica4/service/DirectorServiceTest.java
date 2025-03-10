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
        assertEquals("Steven", listaDirectores.get(0).getNombre());
        assertEquals("Christopher", listaDirectores.get(1).getNombre());
        assertEquals("Quentin", listaDirectores.get(2).getNombre());

        verify(directorRepository, times(1)).findAll();
    }

    @Test
    void testObtenerDirectorPorId() {
        Optional<Director> director = directorService.obtenerDirectorPorId(200);

        assertTrue(director.isPresent());
        assertEquals("Steven", director.get().getNombre());
        assertEquals("Spielberg", director.get().getApellido());
        assertEquals(77, director.get().getEdad());

    }

    @Test
    void testInsertarDirector() {
        Director nuevoDirector = new Director(203, "James", "Cameron", 69, "Canadá");
        directorService.insertarDirector(nuevoDirector);

        verify(directorRepository, times(1)).save(nuevoDirector);
    }

    @Test
    void testActualizarDirector() {
        Director directorActualizado = new Director(200, "Steven", "Spielberg", 78, "EE.UU");

        when(directorRepository.findById(200)).thenReturn(Optional.of(director1));
        when(directorRepository.save(any(Director.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(directorService.actualizarDirector(200, directorActualizado)).thenReturn(directorActualizado);

        Director resultado = directorService.actualizarDirector(200, directorActualizado);

        assertNotNull(resultado);
        assertEquals("Steven", resultado.getNombre());
        assertEquals("Spielberg", resultado.getApellido());
        assertEquals(78, resultado.getEdad());
    }

    @Test
    void testEliminarDirector() {
        when(directorRepository.findById(200)).thenReturn(Optional.of(director1));

        directorService.eliminarDirector(200);

        verify(directorRepository, times(1)).delete(director1);
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
    void testObtenerDirectorPorIdCriteria() {
        when(directorCriteriaRepository.buscarDirector(200)).thenReturn(Optional.of(director1));

        Optional<Director> director = directorService.obtenerDirectorPorIdCriteria(200);

        assertTrue(director.isPresent());
        assertEquals("Steven", director.get().getNombre());
        assertEquals("Spielberg", director.get().getApellido());
        assertEquals(77, director.get().getEdad());

        verify(directorCriteriaRepository, times(1)).buscarDirector(200);
    }

    @Test
    void testInsertarDirectorCriteria() {
        Director nuevoDirector = new Director(203, "James", "Cameron", 69, "Canadá");

        directorService.insertarDirectorCriteria(nuevoDirector);

        verify(directorCriteriaRepository, times(1)).insertarDirector(nuevoDirector);
    }

    @Test
    void testActualizarDirectorCriteria() {
        Director directorActualizado = new Director(200, "Steven", "Spielberg", 78, "EE.UU");

        directorService.actualizarActorCriteria(200, directorActualizado);

        verify(directorCriteriaRepository, times(1)).actualizarDirector(200, directorActualizado);
    }

    @Test
    void testEliminarDirectorCriteria() {
        directorService.eliminarDirectorCriteria(200);

        verify(directorCriteriaRepository, times(1)).borrarDirectorPorId(200);
    }
}
