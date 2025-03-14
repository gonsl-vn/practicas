package com.viewnext.practica4.contrellers;

import com.viewnext.practica4.controllers.SerieController;
import com.viewnext.practica4.models.Serie;
import com.viewnext.practica4.services.SerieService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase de pruebas para SerieController (métodos con JPA).
 */
@ExtendWith(MockitoExtension.class)
class SerieControllerTest {

    @Mock
    private SerieService serieService;

    @InjectMocks
    private SerieController serieController;

    private Serie serie;

    @BeforeEach
    void setUpEach() {
        // Creamos una serie de ejemplo
        serie = new Serie();
        serie.setIdSerie(1);
        serie.setTitulo("Breaking Bad");
        serie.setAno(LocalDate.of(2008, 1, 20));
        // El director, productora, actores pueden ser null si no son relevantes para el test
    }

    // -------------------------------
    // 1. LISTAR SERIES (GET /api/serie)
    // -------------------------------
    @Nested
    class ListarSeriesTests {

        @Test
        void testObtenerSeries_OK() {
            when(serieService.obtenerSeries()).thenReturn(Collections.singletonList(serie));

            ResponseEntity<List<Serie>> response = serieController.obtenerSeries();

            assertNotNull(response.getBody());
            assertFalse(response.getBody().isEmpty());
            assertEquals(1, response.getBody().size());
            assertEquals("Breaking Bad", response.getBody().get(0).getTitulo());
        }

        @Test
        void testObtenerSeries_KO() {
            when(serieService.obtenerSeries()).thenReturn(Collections.emptyList());

            ResponseEntity<List<Serie>> response = serieController.obtenerSeries();

            assertNotNull(response.getBody());
            assertTrue(response.getBody().isEmpty());
        }
    }

    // -------------------------------
    // 2. OBTENER SERIE POR ID (GET /api/serie/{id})
    // -------------------------------
    @Nested
    class ObtenerSeriePorIdTests {

        @Test
        void testObtenerSeriePorId_OK() {
            when(serieService.obtenerSeriePorId(1)).thenReturn(Optional.of(serie));

            ResponseEntity<Serie> response = serieController.obtenerSeries(1);

            assertNotNull(response.getBody());
            assertEquals("Breaking Bad", response.getBody().getTitulo());
        }

        @Test
        void testObtenerSeriePorId_KO() {
            when(serieService.obtenerSeriePorId(1)).thenReturn(Optional.empty());

            // El controlador hace .get() sin chequear si está presente ->
            // produce NoSuchElementException en tiempo de ejecución
            assertThrows(NoSuchElementException.class, () -> serieController.obtenerSeries(1));
        }
    }

    // -------------------------------
    // 3. INSERTAR SERIE (POST /api/serie)
    // -------------------------------
    @Nested
    class InsertarSerieTests {

        @Test
        void testInsertarSerie_OK() {
            doNothing().when(serieService).insertarSerie(serie);

            ResponseEntity<Serie> response = serieController.insertarSerie(serie);

            assertNotNull(response.getBody());
            assertEquals("Breaking Bad", response.getBody().getTitulo());
            verify(serieService, times(1)).insertarSerie(serie);
        }

        @Test
        void testInsertarSerie_KO() {
            doThrow(new RuntimeException("Error al insertar serie")).when(serieService).insertarSerie(serie);

            assertThrows(RuntimeException.class, () -> serieController.insertarSerie(serie));
        }
    }

    // -------------------------------
    // 4. ACTUALIZAR SERIE (PUT /api/serie/{id})
    // -------------------------------
    @Nested
    class ActualizarSerieTests {

        @Test
        void testActualizarSerie_OK() {
            Serie serieActualizada = new Serie(1, "Better Call Saul", LocalDate.of(2015, 2, 8), null, null, null);

            when(serieService.actualizarSerie(1, serieActualizada)).thenReturn(serieActualizada);

            ResponseEntity<Serie> response = serieController.ActualizarSerie(1, serieActualizada);

            assertNotNull(response.getBody());
            assertEquals("Better Call Saul", response.getBody().getTitulo());
            assertEquals(2015, response.getBody().getAno().getYear());
        }

        @Test
        void testActualizarSerie_KO() {
            when(serieService.actualizarSerie(1, serie)).thenThrow(
                    new RuntimeException("Serie no encontrada con ID: 1"));

            assertThrows(RuntimeException.class, () -> serieController.ActualizarSerie(1, serie));
        }
    }

    // -------------------------------
    // 5. ELIMINAR SERIE (DELETE /api/serie/{id})
    // -------------------------------
    @Nested
    class EliminarSerieTests {

        @Test
        void testBorrarSerie_OK() {
            doNothing().when(serieService).eliminarSerie(1);

            ResponseEntity<Void> response = serieController.BorrarSerie(1);

            assertEquals(200, response.getStatusCodeValue());
            verify(serieService, times(1)).eliminarSerie(1);
        }

        @Test
        void testBorrarSerie_KO() {
            doThrow(new RuntimeException("Serie no encontrada")).when(serieService).eliminarSerie(1);

            assertThrows(RuntimeException.class, () -> serieController.BorrarSerie(1));
        }
    }

    // -------------------------------
    // 📌 TEST PARA PAGINAR Y ORDENAR
    // -------------------------------
    @Nested
    class PaginaryOrdenarTests {
        @BeforeEach
        void setUp() {
            serie = new Serie(1, "Breaking Bad", LocalDate.of(2008, 1, 20), null, null, null);
        }

        @AfterEach
        void tearDown() {
            // Limpiar datos simulados de los mocks
            serie = null;

            reset(serieService);
        }

        @Test
        void testEncontrarSeriesPaginadoYOrdenado_OK() {
            // Simulamos que el servicio devuelve una página de series
            Page<Serie> page = new PageImpl<>(Arrays.asList(serie));
            when(serieService.obtenerSerie(any(Pageable.class))).thenReturn(page);

            // Llamamos al controlador para encontrar series paginadas y ordenadas
            Page<Serie> result = serieController.encontrarSerie(0, 3, "idSerie");

            // Verificamos que la página no sea nula
            assertNotNull(result);
            // Verificamos que la página tenga contenido
            assertFalse(result.isEmpty());
            // Verificamos que el tamaño de la página sea el esperado
            assertEquals(1, result.getSize());
        }

        @Test
        void testEncontrarSeriesPaginadoYOrdenado_KO() {
            // Simulamos que el servicio lanza una excepción al intentar encontrar series
            when(serieService.obtenerSerie(any(Pageable.class))).thenThrow(
                    new RuntimeException("Error al encontrar series"));

            // Llamamos al controlador y verificamos que lance una excepción
            RuntimeException thrown = assertThrows(RuntimeException.class,
                    () -> serieController.encontrarSerie(0, 3, "idSerie"));

            // Verificamos que el mensaje de la excepción sea el esperado
            assertEquals("Error al encontrar series", thrown.getMessage());
        }
    }

}
