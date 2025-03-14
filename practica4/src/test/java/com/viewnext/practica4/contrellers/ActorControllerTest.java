package com.viewnext.practica4.contrellers;

import com.viewnext.practica4.controllers.ActorController;
import com.viewnext.practica4.models.Actor;
import com.viewnext.practica4.repositorys.ActorRepository;
import com.viewnext.practica4.services.ActorService;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActorControllerTest {

    @Mock
    private ResponseEntity<Actor> responseEntity;

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private ResponseEntity<List<Actor>> listResponseEntity;

    @Mock
    private ActorService actorService;

    @InjectMocks
    private ActorController actorController;

    private Actor actor;

    // -------------------------------
    // 📌 TEST PARA LISTAR ACTORES
    // -------------------------------
    @Nested
    class ListarActoresTests {

        @BeforeEach
        void setUp() {
            actor = new Actor(100, "Chris", "Evans", 45, "Estados Unidos");

        }

        @AfterEach
        void tearDown() {
            // Limpiar datos simulados de los mocks
            actor = null;
            // Reiniciar el mock después de cada prueba para evitar inconsistencias
            reset(actorRepository);
            reset(actorService);
        }

        @Test
        void tesObtenerActores_OK() {
            when(actorService.obtenerActores()).thenReturn(Collections.singletonList(actor));
            listResponseEntity = actorController.obtenerActores();

            assertFalse(Objects.requireNonNull(listResponseEntity.getBody()).isEmpty());
        }

        @Test
        void testObtenerActores_KO() {
            when(actorService.obtenerActores()).thenReturn(null);
            listResponseEntity = actorController.obtenerActores();

            assertNull(listResponseEntity.getBody());
        }
    }

    // -------------------------------
    // 📌 TEST PARA OBTENER ACTOR POR ID
    // -------------------------------
    @Nested
    class ObtenerActorPorIdTests {

        @BeforeEach
        void setUp() {
            actor = new Actor(100, "Chris", "Evans", 45, "Estados Unidos");

        }

        @AfterEach
        void tearDown() {
            // Limpiar datos simulados de los mocks
            actor = null;
            // Reiniciar el mock después de cada prueba para evitar inconsistencias
            reset(actorRepository);
            reset(actorService);
        }

        @Test
        void testObtenerActorPorId_OK() {
            // Simulamos que el servicio devuelve un actor con el ID especificado
            when(actorService.obtenerActorPorId(actor.getIdActor())).thenReturn(Optional.of(actor));

            // Llamamos al controlador para obtener el actor
            ResponseEntity<Actor> responseEntity = actorController.obtenerActores(actor.getIdActor());

            // Verificamos que la respuesta no sea nula
            assertNotNull(responseEntity.getBody());
            // Verificamos que el actor tiene los datos correctos
            assertEquals("Chris", responseEntity.getBody().getNombre());
            assertEquals("Evans", responseEntity.getBody().getApellido());
        }

        @Test
        void testObtenerActorPorId_KO() {
            // Simulamos que el servicio devuelve un Optional vacío (no se encuentra el actor)
            when(actorService.obtenerActorPorId(actor.getIdActor())).thenReturn(Optional.empty());

            // Llamamos al controlador y verificamos que lance una excepción
            assertThrows(NoSuchElementException.class, () -> actorController.obtenerActores(actor.getIdActor()));
        }
    }

    // -------------------------------
    // 📌 TEST PARA INSERTAR ACTOR
    // -------------------------------
    @Nested
    class InsertarActorTests {

        @BeforeEach
        void setUp() {
            actor = new Actor(100, "Chris", "Evans", 45, "Estados Unidos");
        }

        @AfterEach
        void tearDown() {
            // Limpiar datos simulados de los mocks
            actor = null;
            // Reiniciar el mock después de cada prueba para evitar inconsistencias
            reset(actorRepository);
            reset(actorService);
        }

        @Test
        void testInsertarActor_OK() {
            responseEntity = actorController.insertarActor(actor);

            // Verificamos que el cuerpo de la respuesta no sea nulo
            assertNotNull(responseEntity.getBody());
            // Verificamos que los datos del actor insertado sean correctos
            assertEquals("Chris", responseEntity.getBody().getNombre());
            assertEquals("Evans", responseEntity.getBody().getApellido());
            // Verificamos que la respuesta tiene el código de estado 200 OK
            assertEquals(200, responseEntity.getStatusCodeValue());
        }

        @Test
        void testInsertarActor_KO() {
            // Simulamos que el servicio lanza una excepción al insertar el actor
            doThrow(new RuntimeException("Error al insertar actor")).when(actorService).insertarActor(actor);

            // Llamamos al controlador y verificamos que lance una excepción
            RuntimeException thrown = assertThrows(RuntimeException.class, () -> actorController.insertarActor(actor));

            // Verificamos que el mensaje de la excepción sea el esperado
            assertEquals("Error al insertar actor", thrown.getMessage());
        }
    }

    // -------------------------------
    // 📌 TEST PARA ACTUALIZAR ACTOR
    // -------------------------------
    @Nested
    class ActualizarActorTests {

        @BeforeEach
        void setUp() {
            actor = new Actor(100, "Chris", "Evans", 45, "Estados Unidos");
        }

        @AfterEach
        void tearDown() {
            // Limpiar datos simulados de los mocks
            actor = null;
            // Reiniciar el mock después de cada prueba para evitar inconsistencias
            reset(actorRepository);
            reset(actorService);
        }

        @Test
        void testActualizarActor_OK() {
            // Simulamos que el servicio devuelve el actor actualizado
            Actor updatedActor = new Actor(100, "Chris", "Evans", 46, "Estados Unidos");  // Edad actualizada
            when(actorService.actualizarActor(actor.getIdActor(), updatedActor)).thenReturn(updatedActor);

            // Llamamos al controlador para actualizar el actor
            ResponseEntity<Actor> responseEntity = actorController.ActualizarActor(actor.getIdActor(), updatedActor);

            // Verificamos que el cuerpo de la respuesta no sea nulo
            assertNotNull(responseEntity.getBody());
            // Verificamos que el actor actualizado tiene los datos correctos
            assertEquals("Chris", responseEntity.getBody().getNombre());
            assertEquals("Evans", responseEntity.getBody().getApellido());
            assertEquals(46, responseEntity.getBody().getEdad());  // Verificamos que la edad se haya actualizado
            // Verificamos que la respuesta tenga el código de estado 200 OK
            assertEquals(200, responseEntity.getStatusCodeValue());
        }

        @Test
        void testActualizarActor_KO() {
            // Simulamos que el servicio devuelve null, lo que indicaría que el actor no se ha encontrado
            when(actorService.actualizarActor(actor.getIdActor(), actor)).thenThrow(
                    new RuntimeException("No se encontró el actor"));

            // Llamamos al controlador para actualizar el actor y verificamos que lance una excepción
            RuntimeException thrown = assertThrows(RuntimeException.class,
                    () -> actorController.ActualizarActor(actor.getIdActor(), actor));

            // Verificamos que el mensaje de la excepción sea el esperado
            assertEquals("No se encontró el actor", thrown.getMessage());
        }
    }

    // -------------------------------
    // 📌 TEST PARA ELIMINAR ACTOR
    // -------------------------------
    @Nested
    class EliminarActorTests {
        @BeforeEach
        void setUp() {
            actor = new Actor(100, "Chris", "Evans", 45, "Estados Unidos");
        }

        @AfterEach
        void tearDown() {
            // Limpiar datos simulados de los mocks
            actor = null;
            // Reiniciar el mock después de cada prueba para evitar inconsistencias
            reset(actorRepository);
            reset(actorService);
        }

        @Test
        void testEliminarActor_OK() {
            // Simulamos que el servicio elimina correctamente el actor
            doNothing().when(actorService).eliminarActor(actor.getIdActor());

            // Llamamos al controlador para eliminar el actor
            ResponseEntity<Void> responseEntity = actorController.BorrarActor(actor.getIdActor());

            // Verificamos que el código de estado sea 200 OK
            assertEquals(200, responseEntity.getStatusCodeValue());
        }

        @Test
        void testEliminarActor_KO() {
            // Simulamos que el servicio lanza una excepción al intentar eliminar el actor
            doThrow(new RuntimeException("No se encontró el actor")).when(actorService)
                    .eliminarActor(actor.getIdActor());

            // Llamamos al controlador y verificamos que lance una excepción
            RuntimeException thrown = assertThrows(RuntimeException.class,
                    () -> actorController.BorrarActor(actor.getIdActor()));

            // Verificamos que el mensaje de la excepción sea el esperado
            assertEquals("No se encontró el actor", thrown.getMessage());
        }
    }

    // -------------------------------
    // 📌 TEST PARA PAGINAR Y ORDENAR
    // -------------------------------
    @Nested
    class PaginaryOrdenarTests {
        @BeforeEach
        void setUp() {
            actor = new Actor(100, "Chris", "Evans", 45, "Estados Unidos");
        }

        @AfterEach
        void tearDown() {
            // Limpiar datos simulados de los mocks
            actor = null;
            // Reiniciar el mock después de cada prueba para evitar inconsistencias
            reset(actorRepository);
            reset(actorService);
        }

        @Test
        void testEncontrarActoresPaginadoYOrdenado_OK() {
            // Simulamos que el servicio devuelve una página de actores
            Page<Actor> page = new PageImpl<>(Arrays.asList(actor));
            when(actorService.obtenerActores(any(Pageable.class))).thenReturn(page);

            // Llamamos al controlador para encontrar actores paginados y ordenados
            Page<Actor> result = actorController.encontrarActores(0, 3, "idActor");

            // Verificamos que la página no sea nula
            assertNotNull(result);
            // Verificamos que la página tenga contenido
            assertFalse(result.isEmpty());
            // Verificamos que el tamaño de la página sea el esperado
            assertEquals(1, result.getSize());
        }

        @Test
        void testEncontrarActoresPaginadoYOrdenado_KO() {
            // Simulamos que el servicio lanza una excepción al intentar encontrar actores
            when(actorService.obtenerActores(any(Pageable.class))).thenThrow(
                    new RuntimeException("Error al encontrar actores"));

            // Llamamos al controlador y verificamos que lance una excepción
            RuntimeException thrown = assertThrows(RuntimeException.class,
                    () -> actorController.encontrarActores(0, 3, "idActor"));

            // Verificamos que el mensaje de la excepción sea el esperado
            assertEquals("Error al encontrar actores", thrown.getMessage());
        }
    }

}
