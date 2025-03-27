package com.viewnext.practica4.contrellers;

import com.viewnext.practica4.controllers.ProductoraControllerCriteria;
import com.viewnext.practica4.models.Productora;
import com.viewnext.practica4.services.ProductoraService;
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
 * Clase de prueba para ProductoraControllerCriteria (métodos con Criteria).
 */
@ExtendWith(MockitoExtension.class)
class ProductoraControllerCriteriaTest {

    @Mock
    private ProductoraService productoraServiceCriteria;

    @InjectMocks
    private ProductoraControllerCriteria productoraControllerCriteria;

    private Productora productora;

    @BeforeEach
    void initEachTest() {
        // Creamos un objeto Productora de ejemplo
        productora = new Productora();
        productora.setIdProductora(200);
        productora.setNombre("Warner Bros");
        productora.setAnoFundacion(LocalDate.of(1923, 4, 4));
    }

    // -------------------------------
    // 1. TEST PARA LISTAR PRODUCTORAS (Criteria GET)
    // -------------------------------
    @Nested
    class ListarProductorasTests {

        @Test
        void testListarProductoras_OK() {
            when(productoraServiceCriteria.obtenerProductorasCriteria()).thenReturn(
                    Collections.singletonList(productora));

            ResponseEntity<List<Productora>> response = productoraControllerCriteria.listarProductoras();

            assertNotNull(response.getBody());
            assertFalse(response.getBody().isEmpty());
            assertEquals(1, response.getBody().size());
            assertEquals("Warner Bros", response.getBody().get(0).getNombre());
        }

        @Test
        void testListarProductoras_KO() {
            when(productoraServiceCriteria.obtenerProductorasCriteria()).thenReturn(Collections.emptyList());

            ResponseEntity<List<Productora>> response = productoraControllerCriteria.listarProductoras();

            assertNotNull(response.getBody());
            assertTrue(response.getBody().isEmpty());
        }
    }

    // -------------------------------
    // 2. TEST PARA BUSCAR PRODUCTORA POR ID (Criteria GET /{id})
    // -------------------------------
    @Nested
    class BuscarProductoraTests {

        @Test
        void testBuscarProductora_OK() {
            when(productoraServiceCriteria.obtenerProductoraPorIdCriteria(productora.getIdProductora())).thenReturn(
                    productora);

            ResponseEntity<Productora> response = productoraControllerCriteria.buscarProductora(
                    productora.getIdProductora());

            assertNotNull(response.getBody());
            assertEquals("Warner Bros", response.getBody().getNombre());
        }

        @Test
        void testBuscarProductora_KO() {
            // Simulamos que se lanza excepción al no encontrar la productora
            when(productoraServiceCriteria.obtenerProductoraPorIdCriteria(999)).thenThrow(
                    new RuntimeException("No se encontró la productora"));

            // Esperamos la excepción
            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> productoraControllerCriteria.buscarProductora(999));

            assertEquals("No se encontró la productora", ex.getMessage());
        }
    }

    // -------------------------------
    // 3. TEST PARA INSERTAR PRODUCTORA (Criteria POST)
    // -------------------------------
    @Nested
    class InsertarProductoraTests {

        @Test
        void testInsertarProductora_OK() {
            doNothing().when(productoraServiceCriteria).insertarProductoraCriteria(productora);

            ResponseEntity<String> response = productoraControllerCriteria.insertarProductora(productora);

            assertNotNull(response.getBody());
            assertEquals("Productora insertada correctamente.", response.getBody());
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void testInsertarProductora_KO() {
            doThrow(new RuntimeException("Error al insertar productora")).when(productoraServiceCriteria)
                    .insertarProductoraCriteria(productora);

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> productoraControllerCriteria.insertarProductora(productora));

            assertEquals("Error al insertar productora", ex.getMessage());
        }
    }

    // -------------------------------
    // 4. TEST PARA ACTUALIZAR PRODUCTORA (Criteria PUT /{id})
    // -------------------------------
    @Nested
    class ActualizarProductoraTests {

        @Test
        void testActualizarProductora_OK() {
            doNothing().when(productoraServiceCriteria)
                    .actualizarProductoraCriteria(productora.getIdProductora(), productora);

            ResponseEntity<String> response = productoraControllerCriteria.actualizarProductora(
                    productora.getIdProductora(), productora);

            assertNotNull(response.getBody());
            assertEquals("Productora actualizada correctamente.", response.getBody());
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void testActualizarProductora_KO() {
            doThrow(new RuntimeException("No se encontró la productora")).when(productoraServiceCriteria)
                    .actualizarProductoraCriteria(productora.getIdProductora(), productora);

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> productoraControllerCriteria.actualizarProductora(productora.getIdProductora(), productora));

            assertEquals("No se encontró la productora", ex.getMessage());
        }
    }

    // -------------------------------
    // 5. TEST PARA ELIMINAR PRODUCTORA (Criteria DELETE /{id})
    // -------------------------------
    @Nested
    class EliminarProductoraTests {

        @Test
        void testEliminarProductora_OK() {
            doNothing().when(productoraServiceCriteria).eliminarProductoraCriteria(productora.getIdProductora());

            ResponseEntity<String> response = productoraControllerCriteria.borrarProductora(
                    productora.getIdProductora());

            assertNotNull(response.getBody());
            assertEquals("Productora eliminada correctamente.", response.getBody());
            assertEquals(200, response.getStatusCodeValue());
        }

        @Test
        void testEliminarProductora_KO() {
            doThrow(new RuntimeException("No se encontró la productora")).when(productoraServiceCriteria)
                    .eliminarProductoraCriteria(productora.getIdProductora());

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> productoraControllerCriteria.borrarProductora(productora.getIdProductora()));

            assertEquals("No se encontró la productora", ex.getMessage());
        }
    }
}
