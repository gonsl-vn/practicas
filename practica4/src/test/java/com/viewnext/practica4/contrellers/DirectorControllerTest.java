package com.viewnext.practica4.contrellers;

import com.viewnext.practica4.controllers.DirectorController;
import com.viewnext.practica4.models.Director;
import com.viewnext.practica4.services.DirectorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DirectorControllerTest {

    @Mock
    private DirectorService directorService;

    @InjectMocks
    private DirectorController directorController;

    private Director director;

    // -------------------------------
    // 📌 TEST PARA LISTAR DIRECTORES
    // -------------------------------
    @Nested
    class ListarDirectoresTests {

        @BeforeEach
        void setUp() {
            // Crear instancias de directores de prueba
            director = new Director(200, "Steven", "Spielberg", 77, "EE.UU");
        }

        @Test
        void testListarDirectores_OK() {
            // Simulamos que el servicio devuelve una lista de directores
            when(directorService.obtenerDirectores()).thenReturn(Collections.singletonList(director));

            // Llamamos al controlador para obtener la lista de directores
            ResponseEntity<List<Director>> responseEntity = directorController.obtenerDirectores();

            // Verificamos que el cuerpo de la respuesta no sea nulo y contiene directores
            assertNotNull(responseEntity.getBody());
            assertFalse(responseEntity.getBody().isEmpty());
            assertEquals(1, responseEntity.getBody().size()); // Verificamos que haya un director en la lista
        }

        @Test
        void testListarDirectores_KO() {
            // Simulamos que el servicio devuelve una lista vacía
            when(directorService.obtenerDirectores()).thenReturn(Collections.emptyList());

            // Llamamos al controlador para obtener la lista de directores
            ResponseEntity<List<Director>> responseEntity = directorController.obtenerDirectores();

            // Verificamos que la respuesta no sea nula y que la lista esté vacía
            assertNotNull(responseEntity.getBody());
            assertTrue(responseEntity.getBody().isEmpty());
        }
    }

    // -------------------------------
    // 📌 TEST PARA OBTENER DIRECTOR POR ID
    // -------------------------------
    @Nested
    class ObtenerDirectorPorIdTests {

        @BeforeEach
        void setUp() {
            // Crear instancias de directores de prueba
            director = new Director(200, "Steven", "Spielberg", 77, "EE.UU");
        }

        @Test
        void testObtenerDirectorPorId_OK() {
            // Simulamos que el servicio devuelve el director con el ID especificado
            when(directorService.obtenerDirectorPorId(director.getIdDirector())).thenReturn(Optional.of(director));

            // Llamamos al controlador para obtener el director por ID
            ResponseEntity<Director> responseEntity = directorController.obtenerDirectores(director.getIdDirector());

            // Verificamos que la respuesta no sea nula y que el director tiene los datos correctos
            assertNotNull(responseEntity.getBody());
            assertEquals("Steven", responseEntity.getBody().getNombre());
            assertEquals("Spielberg", responseEntity.getBody().getApellido());
        }

        @Test
        void testObtenerDirectorPorId_KO() {
            // Simulamos que el servicio devuelve un Optional vacío (no se encuentra el director)
            when(directorService.obtenerDirectorPorId(director.getIdDirector())).thenReturn(Optional.empty());

            // Llamamos al controlador y verificamos que lance una excepción
            assertThrows(NoSuchElementException.class,
                    () -> directorController.obtenerDirectores(director.getIdDirector()));
        }
    }

    // -------------------------------
    // 📌 TEST PARA INSERTAR DIRECTOR
    // -------------------------------
    @Nested
    class InsertarDirectorTests {

        @BeforeEach
        void setUp() {
            // Crear instancias de directores de prueba
            director = new Director(200, "Steven", "Spielberg", 77, "EE.UU");
        }

        @Test
        void testInsertarDirector_OK() {
            // Simulamos que el servicio inserta correctamente el director
            doNothing().when(directorService).insertarDirector(director);

            // Llamamos al controlador para insertar el director
            ResponseEntity<Director> responseEntity = directorController.insertarDirector(director);

            // Verificamos que la respuesta no sea nula y que el director insertado tenga los datos correctos
            assertNotNull(responseEntity.getBody());
            assertEquals("Steven", responseEntity.getBody().getNombre());
            assertEquals("Spielberg", responseEntity.getBody().getApellido());
        }

        @Test
        void testInsertarDirector_KO() {
            // Simulamos que el servicio lanza una excepción al insertar el director
            doThrow(new RuntimeException("Error al insertar director")).when(directorService)
                    .insertarDirector(director);

            // Llamamos al controlador y verificamos que lance una excepción
            RuntimeException thrown = assertThrows(RuntimeException.class,
                    () -> directorController.insertarDirector(director));

            // Verificamos que el mensaje de la excepción sea el esperado
            assertEquals("Error al insertar director", thrown.getMessage());
        }
    }

    // -------------------------------
    // 📌 TEST PARA ACTUALIZAR DIRECTOR
    // -------------------------------
    @Nested
    class ActualizarDirectorTests {

        @BeforeEach
        void setUp() {
            // Crear instancias de directores de prueba
            director = new Director(200, "Steven", "Spielberg", 77, "EE.UU");
        }

        @Test
        void testActualizarDirector_OK() {
            // Simulamos que el servicio devuelve el director actualizado
            Director updatedDirector = new Director(100, "Steven", "Spielberg", 76,
                    "Estados Unidos");  // Edad actualizada
            when(directorService.actualizarDirector(director.getIdDirector(), updatedDirector)).thenReturn(
                    updatedDirector);

            // Llamamos al controlador para actualizar el director
            ResponseEntity<Director> responseEntity = directorController.ActualizarDirector(director.getIdDirector(),
                    updatedDirector);

            // Verificamos que el cuerpo de la respuesta no sea nulo y que los datos del director sean correctos
            assertNotNull(responseEntity.getBody());
            assertEquals(76, responseEntity.getBody().getEdad());  // Verificamos que la edad se haya actualizado
        }

        @Test
        void testActualizarDirector_KO() {
            // Simulamos que el servicio lanza una excepción porque no se encuentra el director
            when(directorService.actualizarDirector(director.getIdDirector(), director)).thenThrow(
                    new RuntimeException("No se encontró el director"));

            // Llamamos al controlador y verificamos que lance una excepción
            RuntimeException thrown = assertThrows(RuntimeException.class,
                    () -> directorController.ActualizarDirector(director.getIdDirector(), director));

            // Verificamos que el mensaje de la excepción sea el esperado
            assertEquals("No se encontró el director", thrown.getMessage());
        }
    }

    // -------------------------------
    // 📌 TEST PARA ELIMINAR DIRECTOR
    // -------------------------------
    @Nested
    class EliminarDirectorTests {

        @BeforeEach
        void setUp() {
            // Crear instancias de directores de prueba
            director = new Director(200, "Steven", "Spielberg", 77, "EE.UU");
        }

        @Test
        void testEliminarDirector_OK() {
            // Simulamos que el servicio elimina correctamente el director
            doNothing().when(directorService).eliminarDirector(director.getIdDirector());

            // Llamamos al controlador para eliminar el director
            ResponseEntity<Void> responseEntity = directorController.BorrarDirector(director.getIdDirector());

            // Verificamos que la respuesta tenga el código de estado 200 OK
            assertEquals(200, responseEntity.getStatusCodeValue());
        }

        @Test
        void testEliminarDirector_KO() {
            // Simulamos que el servicio lanza una excepción porque no se encuentra el director
            doThrow(new RuntimeException("No se encontró el director")).when(directorService)
                    .eliminarDirector(director.getIdDirector());

            // Llamamos al controlador y verificamos que lance una excepción
            RuntimeException thrown = assertThrows(RuntimeException.class,
                    () -> directorController.BorrarDirector(director.getIdDirector()));

            // Verificamos que el mensaje de la excepción sea el esperado
            assertEquals("No se encontró el director", thrown.getMessage());
        }
    }
}
