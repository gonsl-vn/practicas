package com.viewnext.practica4.contrellers;

import com.viewnext.practica4.controllers.SerieControllerCriteria;
import com.viewnext.practica4.models.Serie;
import com.viewnext.practica4.services.SerieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
 * Clase de pruebas para SerieControllerCriteria (métodos con Criteria).
 */
@ExtendWith(MockitoExtension.class)
class SerieControllerCriteriaTest {

    @Mock
    private SerieService serieServiceCriteria;

    @InjectMocks
    private SerieControllerCriteria serieControllerCriteria;

    private Serie serie;

    @BeforeEach
    void initEachTest() {
        serie = new Serie();
        serie.setIdSerie(10);
        serie.setTitulo("The Walking Dead");
        serie.setAno(LocalDate.of(2010, 10, 31));
    }

    // -------------------------------
    // 1. LISTAR SERIES (Criteria GET)
    // -------------------------------
    @Nested
    class ListarSeriesTests {

        @Test
        void testListarSeries_OK() {
            when(serieServiceCriteria.obtenerSeriesCriteria()).thenReturn(Collections.singletonList(serie));

            ResponseEntity<List<Serie>> response = serieControllerCriteria.listarSeries();

            assertNotNull(response.getBody());
            assertFalse(response.getBody().isEmpty());
            assertEquals(1, response.getBody().size());
            assertEquals("The Walking Dead", response.getBody().get(0).getTitulo());
        }

        @Test
        void testListarSeries_KO() {
            when(serieServiceCriteria.obtenerSeriesCriteria()).thenReturn(Collections.emptyList());

            ResponseEntity<List<Serie>> response = serieControllerCriteria.listarSeries();

            assertNotNull(response.getBody());
            assertTrue(response.getBody().isEmpty());
        }
    }

    // -------------------------------
    // 2. BUSCAR SERIE POR ID (Criteria GET /{id})
    // -------------------------------
    @Nested
    class BuscarSerieTests {

        @Test
        void testBuscarSerie_OK() {
            when(serieServiceCriteria.obtenerSeriePorIdCriteria(10)).thenReturn(serie);

            ResponseEntity<Serie> response = serieControllerCriteria.buscarSerie(10);

            assertNotNull(response.getBody());
            assertEquals("The Walking Dead", response.getBody().getTitulo());
        }

        @Test
        void testBuscarSerie_KO() {
            doThrow(new RuntimeException("No se encontró la serie")).when(serieServiceCriteria)
                    .obtenerSeriePorIdCriteria(999);

            RuntimeException ex = assertThrows(RuntimeException.class, () -> serieControllerCriteria.buscarSerie(999));

            assertEquals("No se encontró la serie", ex.getMessage());
        }
    }

    // -------------------------------
    // 3. INSERTAR SERIE (Criteria POST)
    // -------------------------------
    @Nested
    class InsertarSerieTests {

        @Test
        void testInsertarSerie_OK() {
            doNothing().when(serieServiceCriteria).insertarSerieCriteria(serie);

            ResponseEntity<String> response = serieControllerCriteria.insertarSerie(serie);

            assertNotNull(response.getBody());
            assertEquals("Serie insertada correctamente.", response.getBody());
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void testInsertarSerie_KO() {
            doThrow(new RuntimeException("Error al insertar serie")).when(serieServiceCriteria)
                    .insertarSerieCriteria(serie);

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> serieControllerCriteria.insertarSerie(serie));

            assertEquals("Error al insertar serie", ex.getMessage());
        }
    }

    // -------------------------------
    // 4. ACTUALIZAR SERIE (Criteria PUT /{id})
    // -------------------------------
    @Nested
    class ActualizarSerieTests {

        @Test
        void testActualizarSerie_OK() {
            doNothing().when(serieServiceCriteria).actualizarSerieCriteria(10, serie);

            ResponseEntity<String> response = serieControllerCriteria.actualizarSerie(10, serie);

            assertNotNull(response.getBody());
            assertEquals("Serie actualizada correctamente.", response.getBody());
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void testActualizarSerie_KO() {
            doThrow(new RuntimeException("No se encontró la serie")).when(serieServiceCriteria)
                    .actualizarSerieCriteria(10, serie);

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> serieControllerCriteria.actualizarSerie(10, serie));

            assertEquals("No se encontró la serie", ex.getMessage());
        }
    }

    // -------------------------------
    // 5. ELIMINAR SERIE (Criteria DELETE /{id})
    // -------------------------------
    @Nested
    class EliminarSerieTests {

        @Test
        void testEliminarSerie_OK() {
            doNothing().when(serieServiceCriteria).eliminarSerieCriteria(10);

            ResponseEntity<String> response = serieControllerCriteria.borrarSerie(10);

            assertNotNull(response.getBody());
            assertEquals("Serie eliminada correctamente.", response.getBody());
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void testEliminarSerie_KO() {
            doThrow(new RuntimeException("No se encontró la serie")).when(serieServiceCriteria)
                    .eliminarSerieCriteria(999);

            RuntimeException ex = assertThrows(RuntimeException.class, () -> serieControllerCriteria.borrarSerie(999));

            assertEquals("No se encontró la serie", ex.getMessage());
        }
    }

    // -------------------------------
    // TESTS PARA filtrarSeries
    // -------------------------------
    @Nested
    class FiltrarSeriesTests {

        @Test
        void testFiltrarSeries_OK() {
            // 1) Simulamos que el servicio devuelve una lista con una Serie
            List<Serie> mockResult = Collections.singletonList(serie);

            when(serieServiceCriteria.filtrarSeries("Breaking", "2008", "Vince", "AMC")).thenReturn(mockResult);

            // 2) Llamamos al controlador con parámetros
            ResponseEntity<List<Serie>> response = serieControllerCriteria.filtrarSeries("Breaking", "Vince", "AMC",
                    "2008");

            // 3) Verificamos la respuesta
            assertNotNull(response.getBody(), "La lista no debe ser nula");
            assertFalse(response.getBody().isEmpty(), "No debe estar vacía");
            assertEquals(1, response.getBody().size(), "Debe tener exactamente un elemento");
            assertEquals("The Walking Dead", response.getBody().get(0).getTitulo());

            // 4) Verificamos que se llamó al servicio con los mismos parámetros
            verify(serieServiceCriteria, times(1)).filtrarSeries("Breaking", "2008", "Vince", "AMC");
        }

        @Test
        void testFiltrarSeries_Vacio() {
            // 1) Simulamos que el servicio devuelve una lista vacía
            when(serieServiceCriteria.filtrarSeries(anyString(), anyString(), anyString(), anyString())).thenReturn(
                    Collections.emptyList());

            // 2) Llamamos al controlador
            ResponseEntity<List<Serie>> response = serieControllerCriteria.filtrarSeries("foo", "bar", "baz", "2023");

            // 3) Verificamos la respuesta
            assertNotNull(response.getBody());
            assertTrue(response.getBody().isEmpty(), "La lista debe estar vacía");

            // 4) Verificamos que se llamó al servicio
            verify(serieServiceCriteria).filtrarSeries("foo", "2023", "bar", "baz");
        }

        @Test
        void testFiltrarSeries_ErrorInterno() {
            // 1) Simulamos que el servicio lanza una excepción
            doThrow(new RuntimeException("Error al filtrar series")).when(serieServiceCriteria)
                    .filtrarSeries("Breaking", "2008", "Vince", "AMC");

            // 2) Verificamos que el controlador propaga la excepción
            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> serieControllerCriteria.filtrarSeries("Breaking", "Vince", "AMC", "2008"));

            // 3) Revisamos el mensaje de la excepción
            assertEquals("Error al filtrar series", ex.getMessage());

            // 4) Verificamos la interacción
            verify(serieServiceCriteria).filtrarSeries("Breaking", "2008", "Vince", "AMC");
        }
    }
}
