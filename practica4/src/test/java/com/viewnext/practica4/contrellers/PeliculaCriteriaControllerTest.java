package com.viewnext.practica4.contrellers;

import com.viewnext.practica4.controllers.PeliculaControllerCriteria;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase de pruebas para PeliculaControllerCriteria, siguiendo la lógica de Criteria (PeliculaService ->
 * PeliculaCriteriaRepository).
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PeliculaCriteriaControllerTest {

    @Mock
    private PeliculaService peliculaServiceCriteria;

    @InjectMocks
    private PeliculaControllerCriteria peliculaControllerCriteria;

    private Pelicula pelicula;

    @BeforeAll
    void beforeAll() {
        System.out.println("Configuración global de PeliculaCriteriaControllerTest...");
    }

    @AfterAll
    void afterAll() {
        System.out.println("Limpieza de PeliculaCriteriaControllerTest completada...");
    }

    // -------------------------------
    // 1. TEST PARA LISTAR PELICULAS
    // -------------------------------
    @Nested
    class ListarPeliculasTests {

        @BeforeEach
        void setUp() {
            // Creamos un objeto Pelicula de ejemplo
            pelicula = new Pelicula();
            pelicula.setIdPelicula(1);
            pelicula.setTitulo("The Matrix");
            pelicula.setAno(LocalDate.of(1999, 3, 31));  // Ejemplo de fecha
        }

        @Test
        void testListarPeliculas_OK() {
            // 1) Simulamos que el servicio devuelve una lista con una película
            when(peliculaServiceCriteria.listarPeliculasCriteria()).thenReturn(Collections.singletonList(pelicula));

            // 2) Llamamos al controlador
            ResponseEntity<List<Pelicula>> response = peliculaControllerCriteria.listarPeliculas();

            // 3) Verificamos la respuesta
            assertNotNull(response.getBody(), "La lista no debe ser nula");
            assertFalse(response.getBody().isEmpty(), "La lista no debe estar vacía");
            assertEquals(1, response.getBody().size(), "Debe haber exactamente una película");
            assertEquals("The Matrix", response.getBody().get(0).getTitulo());
        }

        @Test
        void testListarPeliculas_Vacio() {
            // 1) Simulamos que el servicio devuelve una lista vacía
            when(peliculaServiceCriteria.listarPeliculasCriteria()).thenReturn(Collections.emptyList());

            // 2) Llamamos al controlador
            ResponseEntity<List<Pelicula>> response = peliculaControllerCriteria.listarPeliculas();

            // 3) Verificamos la respuesta
            assertNotNull(response.getBody(), "La lista debe existir aunque esté vacía");
            assertTrue(response.getBody().isEmpty(), "La lista debe estar vacía");
        }
    }

    // -------------------------------
    // 2. TEST PARA BUSCAR PELICULA POR ID
    // -------------------------------
    @Nested
    class BuscarPeliculaTests {

        @BeforeEach
        void setUp() {
            // Película de ejemplo
            pelicula = new Pelicula();
            pelicula.setIdPelicula(10);
            pelicula.setTitulo("Inception");
            pelicula.setAno(LocalDate.of(2010, 7, 16));
        }

        @Test
        void testBuscarPelicula_OK() {
            // 1) Simulamos que el servicio encuentra la película
            when(peliculaServiceCriteria.buscarPeliculaCriteria(pelicula.getIdPelicula())).thenReturn(pelicula);

            // 2) Llamamos al controlador
            ResponseEntity<Pelicula> response = peliculaControllerCriteria.buscarPelicula(pelicula.getIdPelicula());

            // 3) Verificamos la respuesta
            assertNotNull(response.getBody(), "El body no debe ser nulo");
            assertEquals("Inception", response.getBody().getTitulo());
            assertEquals(LocalDate.of(2010, 7, 16), response.getBody().getAno());
        }

        @Test
        void testBuscarPelicula_KO() {
            // 1) Simulamos que la película no se encuentra (el servicio lanza una excepción)
            when(peliculaServiceCriteria.buscarPeliculaCriteria(999)).thenThrow(
                    new RuntimeException("Pelicula no encontrada"));

            // 2) Verificamos que se lanza la excepción al llamar al controlador
            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> peliculaControllerCriteria.buscarPelicula(999));

            // 3) Comprobamos el mensaje
            assertEquals("Pelicula no encontrada", ex.getMessage());
        }
    }

    // -------------------------------
    // 3. TEST PARA INSERTAR PELICULA
    // -------------------------------
    @Nested
    class InsertarPeliculaTests {

        @BeforeEach
        void setUp() {
            pelicula = new Pelicula();
            pelicula.setIdPelicula(50);
            pelicula.setTitulo("Interstellar");
            pelicula.setAno(LocalDate.of(2014, 11, 7));
        }

        @Test
        void testInsertarPelicula_OK() {
            // 1) Simulamos que insertarPeliculaCriteria no lanza excepción
            doNothing().when(peliculaServiceCriteria).insertarPeliculaCriteria(pelicula);

            // 2) Llamamos al controlador
            ResponseEntity<String> response = peliculaControllerCriteria.insertarPelicula(pelicula);

            // 3) Verificamos la respuesta
            assertNotNull(response.getBody());
            assertEquals("Película insertada correctamente.", response.getBody());
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void testInsertarPelicula_KO() {
            // 1) Simulamos que el servicio lanza excepción
            doThrow(new RuntimeException("Error al insertar")).when(peliculaServiceCriteria)
                    .insertarPeliculaCriteria(pelicula);

            // 2) Verificamos la excepción
            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> peliculaControllerCriteria.insertarPelicula(pelicula));

            // 3) Comprobamos el mensaje
            assertEquals("Error al insertar", ex.getMessage());
        }
    }

    // -------------------------------
    // 4. TEST PARA ACTUALIZAR PELICULA
    // -------------------------------
    @Nested
    class ActualizarPeliculaTests {

        @BeforeEach
        void setUp() {
            pelicula = new Pelicula();
            pelicula.setIdPelicula(200);
            pelicula.setTitulo("Avatar");
            pelicula.setAno(LocalDate.of(2009, 12, 18));
        }

        @Test
        void testActualizarPelicula_OK() {
            // 1) Simulamos que el servicio no lanza excepción (void)
            doNothing().when(peliculaServiceCriteria).actualizarPeliculaCriteria(pelicula.getIdPelicula(), pelicula);

            // 2) Llamamos al controlador
            ResponseEntity<String> response = peliculaControllerCriteria.actualizarPelicula(pelicula.getIdPelicula(),
                    pelicula);

            // 3) Verificamos la respuesta
            assertNotNull(response.getBody());
            assertEquals("Película actualizada correctamente.", response.getBody());
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void testActualizarPelicula_KO() {
            // 1) Simulamos que se lanza excepción (por ejemplo, la película no existe)
            doThrow(new RuntimeException("No se encontró la película")).when(peliculaServiceCriteria)
                    .actualizarPeliculaCriteria(pelicula.getIdPelicula(), pelicula);

            // 2) Verificamos la excepción
            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> peliculaControllerCriteria.actualizarPelicula(pelicula.getIdPelicula(), pelicula));

            // 3) Comprobamos el mensaje
            assertEquals("No se encontró la película", ex.getMessage());
        }
    }

    // -------------------------------
    // 5. TEST PARA ELIMINAR PELICULA
    // -------------------------------
    @Nested
    class EliminarPeliculaTests {

        @BeforeEach
        void setUp() {
            pelicula = new Pelicula();
            pelicula.setIdPelicula(300);
            pelicula.setTitulo("Titanic");
            pelicula.setAno(LocalDate.of(1997, 12, 19));
        }

        @Test
        void testEliminarPelicula_OK() {
            // 1) Simulamos que no lanza excepción
            doNothing().when(peliculaServiceCriteria).borrarPeliculaPorIdCriteria(pelicula.getIdPelicula());

            // 2) Llamamos al controlador
            ResponseEntity<String> response = peliculaControllerCriteria.borrarPelicula(pelicula.getIdPelicula());

            // 3) Verificamos la respuesta
            assertNotNull(response.getBody());
            assertEquals("Película eliminada correctamente.", response.getBody());
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void testEliminarPelicula_KO() {
            // 1) Simulamos que se lanza excepción porque no existe la película
            doThrow(new RuntimeException("No se encontró la película")).when(peliculaServiceCriteria)
                    .borrarPeliculaPorIdCriteria(pelicula.getIdPelicula());

            // 2) Verificamos la excepción
            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> peliculaControllerCriteria.borrarPelicula(pelicula.getIdPelicula()));

            // 3) Comprobamos el mensaje
            assertEquals("No se encontró la película", ex.getMessage());
        }
    }
}
