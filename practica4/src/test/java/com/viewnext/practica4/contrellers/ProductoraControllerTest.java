package com.viewnext.practica4.contrellers;

import com.viewnext.practica4.controllers.ProductoraController;
import com.viewnext.practica4.models.Productora;
import com.viewnext.practica4.services.ProductoraService;
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
 * Clase de prueba para el controlador ProductoraController (vía JPA).
 */
@ExtendWith(MockitoExtension.class)
class ProductoraControllerTest {

    @Mock
    private ProductoraService productoraService;

    @InjectMocks
    private ProductoraController productoraController;

    private Productora productora;

    @BeforeEach
    void initEachTest() {
        // Creamos un objeto Productora de ejemplo
        productora = new Productora();
        productora.setIdProductora(100);
        productora.setNombre("Universal Studios");
        productora.setAnoFundacion(LocalDate.of(1912, 4, 30));
    }

    // -------------------------------
    // 1. TEST PARA LISTAR PRODUCTORAS (GET)
    // -------------------------------
    @Nested
    class ListarProductorasTests {

        @Test
        void testObtenerProductoraes_OK() {
            // Simulamos que el servicio devuelve una lista con una productora
            when(productoraService.obtenerProductoras()).thenReturn(Collections.singletonList(productora));

            ResponseEntity<List<Productora>> response = productoraController.obtenerProductoraes();

            assertNotNull(response.getBody());
            assertFalse(response.getBody().isEmpty());
            assertEquals(1, response.getBody().size());
            assertEquals("Universal Studios", response.getBody().get(0).getNombre());
        }

        @Test
        void testObtenerProductoraes_KO() {
            // Simulamos que el servicio devuelve lista vacía
            when(productoraService.obtenerProductoras()).thenReturn(Collections.emptyList());

            ResponseEntity<List<Productora>> response = productoraController.obtenerProductoraes();

            assertNotNull(response.getBody());
            assertTrue(response.getBody().isEmpty());
        }
    }

    // -------------------------------
    // 2. TEST PARA OBTENER PRODUCTORA POR ID (GET /{id})
    // -------------------------------
    @Nested
    class ObtenerProductoraPorIdTests {

        @Test
        void testObtenerProductoraPorId_OK() {
            when(productoraService.obtenerProductoraPorId(productora.getIdProductora())).thenReturn(
                    Optional.of(productora));

            ResponseEntity<Productora> response = productoraController.obtenerProductoraes(
                    productora.getIdProductora());

            assertNotNull(response.getBody());
            assertEquals("Universal Studios", response.getBody().getNombre());
        }

        @Test
        void testObtenerProductoraPorId_KO() {
            when(productoraService.obtenerProductoraPorId(productora.getIdProductora())).thenReturn(Optional.empty());

            // El controlador llama a .get() sin verificar si está presente ->
            // en tiempo de ejecución se produciría NoSuchElementException
            assertThrows(NoSuchElementException.class,
                    () -> productoraController.obtenerProductoraes(productora.getIdProductora()));
        }
    }

    // -------------------------------
    // 3. TEST PARA INSERTAR PRODUCTORA (POST)
    // -------------------------------
    @Nested
    class InsertarProductoraTests {

        @Test
        void testInsertarProductora_OK() {
            // Suponemos que no lanza excepción y se realiza el guardado
            doNothing().when(productoraService).insertarProductora(productora);

            ResponseEntity<Productora> response = productoraController.insertarProductora(productora);

            assertNotNull(response.getBody());
            assertEquals("Universal Studios", response.getBody().getNombre());
            verify(productoraService, times(1)).insertarProductora(productora);
        }

        @Test
        void testInsertarProductora_KO() {
            // Simulamos excepción en la inserción
            doThrow(new RuntimeException("Error al insertar productora")).when(productoraService)
                    .insertarProductora(productora);

            assertThrows(RuntimeException.class, () -> productoraController.insertarProductora(productora));
        }
    }

    // -------------------------------
    // 4. TEST PARA ACTUALIZAR PRODUCTORA (PUT /{id})
    // -------------------------------
    @Nested
    class ActualizarProductoraTests {

        @Test
        void testActualizarProductora_OK() {
            Productora updatedProductora = new Productora(100, "Paramount", LocalDate.of(1912, 5, 8));

            when(productoraService.actualizarProductora(eq(100), any(Productora.class))).thenReturn(updatedProductora);

            ResponseEntity<Productora> response = productoraController.ActualizarProductora(100, updatedProductora);

            assertNotNull(response.getBody());
            assertEquals("Paramount", response.getBody().getNombre());
            assertEquals(1912, response.getBody().getAnoFundacion().getYear());
        }

        @Test
        void testActualizarProductora_KO() {
            when(productoraService.actualizarProductora(eq(100), any(Productora.class))).thenThrow(
                    new RuntimeException("Productora no encontrada con ID: 100"));

            assertThrows(RuntimeException.class, () -> productoraController.ActualizarProductora(100, productora));
        }
    }

    // -------------------------------
    // 5. TEST PARA ELIMINAR PRODUCTORA (DELETE /{id})
    // -------------------------------
    @Nested
    class EliminarProductoraTests {

        @Test
        void testBorrarProductora_OK() {
            doNothing().when(productoraService).eliminarProductora(100);

            ResponseEntity<Void> response = productoraController.BorrarProductora(100);

            assertEquals(200, response.getStatusCodeValue());
            verify(productoraService, times(1)).eliminarProductora(100);
        }

        @Test
        void testBorrarProductora_KO() {
            doThrow(new RuntimeException("Productora no encontrada con ID: 100")).when(productoraService)
                    .eliminarProductora(100);

            assertThrows(RuntimeException.class, () -> productoraController.BorrarProductora(100));
        }
    }

    // -------------------------------
    // 📌 TEST PARA PAGINAR Y ORDENAR
    // -------------------------------
    @Nested
    class PaginaryOrdenarTests {
        @BeforeEach
        void setUp() {
            productora = new Productora(100, "Universal Studios", LocalDate.of(1912, 4, 30));
        }

        @AfterEach
        void tearDown() {
            // Limpiar datos simulados de los mocks
            productora = null;

            reset(productoraService);
        }

        @Test
        void testEncontrarProductorasPaginadoYOrdenado_OK() {
            // Simulamos que el servicio devuelve una página de productoras
            Page<Productora> page = new PageImpl<>(Arrays.asList(productora));
            when(productoraService.obtenerProductora(any(Pageable.class))).thenReturn(page);

            // Llamamos al controlador para encontrar productoras paginadas y ordenadas
            Page<Productora> result = productoraController.encontrarProductoras(0, 3, "idProductora");

            // Verificamos que la página no sea nula
            assertNotNull(result);
            // Verificamos que la página tenga contenido
            assertFalse(result.isEmpty());
            // Verificamos que el tamaño de la página sea el esperado
            assertEquals(1, result.getSize());
        }

        @Test
        void testEncontrarProductorasPaginadoYOrdenado_KO() {
            // Simulamos que el servicio lanza una excepción al intentar encontrar productoras
            when(productoraService.obtenerProductora(any(Pageable.class))).thenThrow(
                    new RuntimeException("Error al encontrar productoras"));

            // Llamamos al controlador y verificamos que lance una excepción
            RuntimeException thrown = assertThrows(RuntimeException.class,
                    () -> productoraController.encontrarProductoras(0, 3, "idProductora"));

            // Verificamos que el mensaje de la excepción sea el esperado
            assertEquals("Error al encontrar productoras", thrown.getMessage());
        }
    }

}
