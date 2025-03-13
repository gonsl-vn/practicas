package com.viewnext.practica4.contrellers;

import com.viewnext.practica4.controllers.PeliculaController;
import com.viewnext.practica4.models.Pelicula;
import com.viewnext.practica4.services.PeliculaService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase de pruebas unitarias para PeliculaController.
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PeliculaControllerTest {

    @Mock
    private PeliculaService peliculaService;

    @InjectMocks
    private PeliculaController peliculaController;

    private Pelicula pelicula;

    @BeforeAll
    void setUpBeforeAll() {
        System.out.println("Ejecutando configuración global de PeliculaControllerTest...");
    }

    @AfterAll
    void tearDownAfterAll() {
        System.out.println("Limpieza después de todas las pruebas de PeliculaControllerTest...");
    }

    @BeforeEach
    void setUp() {
        // Creamos una película de ejemplo para los tests
        pelicula = new Pelicula();
        pelicula.setIdPelicula(1);
        pelicula.setTitulo("The Matrix");
        pelicula.setAno(LocalDate.ofEpochDay(1999));
        // director, productora, actores se pueden dejar en null si no son relevantes en el test
    }

    // -------------------------------
    // 📌 1) TESTS PARA LISTAR PELICULAS
    // -------------------------------
    @Nested
    class ListarPeliculasTests {

        @Test
        void testListarPeliculas_OK() {
            // Simulamos que el servicio devuelve una lista con una única película
            when(peliculaService.listarPeliculas()).thenReturn(Collections.singletonList(pelicula));

            // Llamamos al controlador
            ResponseEntity<List<Pelicula>> response = peliculaController.listarPeliculas();

            // Verificamos la respuesta
            assertNotNull(response.getBody(), "El body de la respuesta no debe ser nulo");
            assertFalse(response.getBody().isEmpty(), "La lista no debe estar vacía");
            assertEquals(1, response.getBody().size(), "Debe haber exactamente una película");
            assertEquals("The Matrix", response.getBody().get(0).getTitulo());
        }

        @Test
        void testListarPeliculas_KO() {
            // Simulamos que el servicio devuelve una lista vacía
            when(peliculaService.listarPeliculas()).thenReturn(Collections.emptyList());

            // Llamamos al controlador
            ResponseEntity<List<Pelicula>> response = peliculaController.listarPeliculas();

            // Verificamos la respuesta
            assertNotNull(response.getBody(), "El body no debe ser nulo aunque esté vacío");
            assertTrue(response.getBody().isEmpty(), "La lista de películas debe estar vacía");
        }
    }

    // -------------------------------
    // 📌 2) TESTS PARA BUSCAR PELICULA POR ID
    // -------------------------------
    @Nested
    class BuscarPeliculaTests {

        @Test
        void testBuscarPelicula_OK() {
            // Simulamos que el servicio devuelve una película existente
            when(peliculaService.buscarPelicula(pelicula.getIdPelicula())).thenReturn(Optional.of(pelicula));

            // Llamamos al controlador
            ResponseEntity<Pelicula> response = peliculaController.buscarPelicula(pelicula.getIdPelicula());

            // Verificamos la respuesta
            assertEquals(200, response.getStatusCodeValue(), "Debe responder con estado 200 OK");
            assertNotNull(response.getBody(), "El body no debe ser nulo");
            assertEquals("The Matrix", response.getBody().getTitulo());
        }

        @Test
        void testBuscarPelicula_KO() {
            // Simulamos que la película no existe
            when(peliculaService.buscarPelicula(pelicula.getIdPelicula())).thenReturn(Optional.empty());

            // Llamamos al controlador
            ResponseEntity<Pelicula> response = peliculaController.buscarPelicula(pelicula.getIdPelicula());

            // Verificamos la respuesta: notFound
            assertEquals(404, response.getStatusCodeValue(), "Debe responder con estado 404 Not Found");
            assertNull(response.getBody(), "El body debe ser nulo si no se encuentra la película");
        }
    }

    // -------------------------------
    // 📌 3) TESTS PARA INSERTAR PELICULA
    // -------------------------------
    @Nested
    class InsertarPeliculaTests {

        @Test
        void testInsertarPelicula_OK() {
            // Simulamos que el servicio devuelve la película guardada
            when(peliculaService.insertarPelicula(pelicula)).thenReturn(pelicula);

            // Llamamos al controlador
            ResponseEntity<Pelicula> response = peliculaController.insertarPelicula(pelicula);

            // Verificamos la respuesta
            assertEquals(200, response.getStatusCodeValue(), "Debe responder con estado 200 OK");
            assertNotNull(response.getBody(), "El body no debe ser nulo");
            assertEquals("The Matrix", response.getBody().getTitulo());
        }

        @Test
        void testInsertarPelicula_KO() {
            // Simulamos que el servicio lanza una excepción
            doThrow(new RuntimeException("Error al insertar")).when(peliculaService).insertarPelicula(pelicula);

            // Llamamos al controlador y esperamos que no maneje la excepción internamente,
            // sino que la propague. (Dependiendo de tu lógica, esto podría cambiar.)
            assertThrows(RuntimeException.class, () -> peliculaController.insertarPelicula(pelicula));
        }
    }

    // -------------------------------
    // 📌 4) TESTS PARA ACTUALIZAR PELICULA
    // -------------------------------
    @Nested
    class ActualizarPeliculaTests {

        @Test
        void testActualizarPelicula_OK() {
            // Simulamos que el servicio devuelve la película actualizada
            Pelicula peliculaActualizada = new Pelicula();
            peliculaActualizada.setIdPelicula(1);
            peliculaActualizada.setTitulo("The Matrix Reloaded");
            peliculaActualizada.setAno(LocalDate.ofEpochDay(2003));

            when(peliculaService.actualizarPelicula(pelicula.getIdPelicula(), peliculaActualizada)).thenReturn(
                    peliculaActualizada);

            // Llamamos al controlador
            ResponseEntity<Pelicula> response = peliculaController.actualizarPelicula(pelicula.getIdPelicula(),
                    peliculaActualizada);

            // Verificamos la respuesta
            assertEquals(200, response.getStatusCodeValue());
            assertNotNull(response.getBody());
            assertEquals("The Matrix Reloaded", response.getBody().getTitulo());
            assertEquals(LocalDate.ofEpochDay(2003), response.getBody().getAno());
        }

        @Test
        void testActualizarPelicula_NoEncontrada() {
            // Simulamos que se lanza una RuntimeException porque no existe la película
            when(peliculaService.actualizarPelicula(pelicula.getIdPelicula(), pelicula)).thenThrow(
                    new RuntimeException("Pelicula con ID " + pelicula.getIdPelicula() + " no encontrada."));

            // Llamamos al controlador
            ResponseEntity<Pelicula> response = peliculaController.actualizarPelicula(pelicula.getIdPelicula(),
                    pelicula);

            // Verificamos que se retorne 404
            assertEquals(404, response.getStatusCodeValue());
            assertNull(response.getBody());
        }
    }

    // -------------------------------
    // 📌 5) TESTS PARA BORRAR PELICULA
    // -------------------------------
    @Nested
    class BorrarPeliculaTests {

        @Test
        void testBorrarPelicula_OK() {
            // Simulamos que se borra sin problemas
            doNothing().when(peliculaService).borrarPeliculaPorId(pelicula.getIdPelicula());

            // Llamamos al controlador
            ResponseEntity<Void> response = peliculaController.borrarPelicula(pelicula.getIdPelicula());

            // Verificamos la respuesta 204 No Content
            assertEquals(204, response.getStatusCodeValue());
            assertNull(response.getBody());
        }

        @Test
        void testBorrarPelicula_NoEncontrada() {
            // Simulamos que se lanza excepción al no encontrar la película
            doThrow(new RuntimeException("No se puede eliminar, Pelicula con ID 1 no encontrada.")).when(
                    peliculaService).borrarPeliculaPorId(pelicula.getIdPelicula());

            // Llamamos al controlador
            ResponseEntity<Void> response = peliculaController.borrarPelicula(pelicula.getIdPelicula());

            // Verificamos que se retorne 404
            assertEquals(404, response.getStatusCodeValue());
            assertNull(response.getBody());
        }
    }
}
