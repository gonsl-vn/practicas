package com.viewnext.practica4.contrellers;

import com.viewnext.practica4.controllers.DirectorControllerCriteria;
import com.viewnext.practica4.models.Director;
import com.viewnext.practica4.services.DirectorService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase de prueba simplificada para DirectorControllerCriteria.
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DirectorCriteriaControllerTest {

    @Mock
    private DirectorService directorServiceCriteria;

    @InjectMocks
    private DirectorControllerCriteria directorControllerCriteria;

    private Director director;

    @BeforeAll
    void beforeAll() {
        System.out.println("Ejecutando configuración global para DirectorCriteriaControllerTest...");
    }

    @AfterAll
    void afterAll() {
        System.out.println("Limpieza al finalizar DirectorCriteriaControllerTest...");
    }

    // -------------------------------
    // 1. TEST PARA LISTAR DIRECTORES
    // -------------------------------
    @Nested
    class ListarDirectoresTests {
        @BeforeEach
        void setUpEachTest() {
            // Un director de ejemplo
            director = new Director(1, "Steven", "Spielberg", 75, "EEUU");
        }

        @Test
        void testListarDirectores_OK() {
            // 1) Simulamos que el servicio devuelve una lista con un solo director
            when(directorServiceCriteria.obtenerDirectores()).thenReturn(Collections.singletonList(director));

            // 2) Llamamos al controlador
            ResponseEntity<List<Director>> response = directorControllerCriteria.listarDirectores();

            // 3) Verificamos la respuesta
            assertNotNull(response.getBody(), "La respuesta no debe ser nula");
            assertFalse(response.getBody().isEmpty(), "La lista no debe estar vacía");
            assertEquals(1, response.getBody().size(), "Debe haber exactamente un director");
            assertEquals("Spielberg", response.getBody().get(0).getApellido());
        }

        @Test
        void testListarDirectores_KO() {
            // 1) Simulamos que el servicio devuelve una lista vacía
            when(directorServiceCriteria.obtenerDirectores()).thenReturn(Collections.emptyList());

            // 2) Llamamos al controlador
            ResponseEntity<List<Director>> response = directorControllerCriteria.listarDirectores();

            // 3) Verificamos la respuesta
            assertNotNull(response.getBody(), "La lista en el body no debe ser nula");
            assertTrue(response.getBody().isEmpty(), "La lista debe estar vacía");
        }
    }

    // -------------------------------
    // 2. TEST PARA BUSCAR DIRECTOR POR ID
    // -------------------------------
    @Nested
    class BuscarDirectorTests {
        @BeforeEach
        void setUpEachTest() {
            // Un director de ejemplo
            director = new Director(1, "Steven", "Spielberg", 75, "EEUU");
        }

        @Test
        void testBuscarDirector_OK() {
            // 1) Simulamos que se encuentra el director
            when(directorServiceCriteria.obtenerDirectorPorIdCriteria(director.getIdDirector())).thenReturn(
                    Optional.of(director));

            // 2) Llamamos al controlador
            ResponseEntity<Optional<Director>> response = directorControllerCriteria.buscarDirector(
                    director.getIdDirector());

            // 3) Verificamos que el Optional contenga al director
            assertNotNull(response.getBody(), "El Optional no debe ser nulo");
            assertTrue(response.getBody().isPresent(), "El Optional debe contener un valor");
            assertEquals("Spielberg", response.getBody().get().getApellido());
        }

        @Test
        void testBuscarDirector_KO() {
            // 1) Simulamos que no se encuentra el director (Optional vacío)
            when(directorServiceCriteria.obtenerDirectorPorIdCriteria(director.getIdDirector())).thenReturn(
                    Optional.empty());

            // 2) Llamamos al controlador
            ResponseEntity<Optional<Director>> response = directorControllerCriteria.buscarDirector(
                    director.getIdDirector());

            // 3) Verificamos que el Optional está vacío
            assertNotNull(response.getBody(), "El Optional no debe ser nulo");
            assertFalse(response.getBody().isPresent(), "El Optional debe estar vacío");
        }
    }

    // -------------------------------
    // 3. TEST PARA INSERTAR DIRECTOR
    // -------------------------------
    @Nested
    class InsertarDirectorTests {

        @BeforeEach
        void setUpEachTest() {
            // Un director de ejemplo
            director = new Director(1, "Steven", "Spielberg", 75, "EEUU");
        }

        @Test
        void testInsertarDirector_OK() {
            // 1) Simulamos que el servicio no lanza excepción (método void)
            doNothing().when(directorServiceCriteria).insertarDirector(director);

            // 2) Llamamos al controlador
            ResponseEntity<String> response = directorControllerCriteria.insertarDirector(director);

            // 3) Verificamos la respuesta
            assertNotNull(response.getBody(), "El mensaje no debe ser nulo");
            assertEquals("Director insertado correctamente.", response.getBody());
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void testInsertarDirector_KO() {
            // 1) Simulamos que el servicio lanza una excepción
            doThrow(new RuntimeException("Error al insertar director")).when(directorServiceCriteria)
                    .insertarDirector(director);

            // 2) Verificamos que se lance la excepción al llamar
            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> directorControllerCriteria.insertarDirector(director));

            // 3) Verificamos el mensaje de la excepción
            assertEquals("Error al insertar director", ex.getMessage());
        }
    }

    // -------------------------------
    // 4. TEST PARA ACTUALIZAR DIRECTOR
    // -------------------------------
    @Nested
    class ActualizarDirectorTests {

        @BeforeEach
        void setUpEachTest() {
            // Un director de ejemplo
            director = new Director(1, "Steven", "Spielberg", 75, "EEUU");
        }

        @Test
        void testActualizarDirector_OK() {
            doNothing().when(directorServiceCriteria).actualizarDirectorCriteria(1, director);

            // 2) Llamamos al controlador
            ResponseEntity<String> response = directorControllerCriteria.actualizarDirector(director.getIdDirector(),
                    director);

            // 3) Verificamos la respuesta
            assertNotNull(response.getBody());
            assertEquals("Director actualizado correctamente.", response.getBody());
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void testActualizarDirector_Fallo() {
            // 1) Simulamos que el servicio lanza una excepción
            doThrow(new RuntimeException("No se encontró el director")).when(directorServiceCriteria)
                    .actualizarDirectorCriteria(director.getIdDirector(), director);

            // 2) Verificamos que se lance la excepción
            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> directorControllerCriteria.actualizarDirector(director.getIdDirector(), director));

            // 3) Verificamos el mensaje
            assertEquals("No se encontró el director", ex.getMessage());
        }
    }

    // -------------------------------
    // 5. TEST PARA ELIMINAR DIRECTOR
    // -------------------------------
    @Nested
    class EliminarDirectorTests {

        @BeforeEach
        void setUpEachTest() {
            // Un director de ejemplo
            director = new Director(1, "Steven", "Spielberg", 75, "EEUU");
        }

        @Test
        void testEliminarDirector_OK() {
            // 1) Simulamos que eliminarDirectorCriteria no lanza excepción (método void)
            doNothing().when(directorServiceCriteria).eliminarDirectorCriteria(director.getIdDirector());

            // 2) Llamamos al controlador
            ResponseEntity<String> response = directorControllerCriteria.borrarDirector(director.getIdDirector());

            // 3) Verificamos la respuesta
            assertNotNull(response.getBody());
            assertEquals("Director eliminado correctamente.", response.getBody());
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void testEliminarDirector_Fallo() {
            // 1) Simulamos que el servicio lanza excepción al eliminar
            doThrow(new RuntimeException("No se encontró el director")).when(directorServiceCriteria)
                    .eliminarDirectorCriteria(director.getIdDirector());

            // 2) Verificamos que se lanza la excepción
            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> directorControllerCriteria.borrarDirector(director.getIdDirector()));

            // 3) Verificamos el mensaje
            assertEquals("No se encontró el director", ex.getMessage());
        }
    }
}
