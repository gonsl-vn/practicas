package com.viewnext.practica4.contrellers;

import com.viewnext.practica4.controllers.ActorControllerCriteria;
import com.viewnext.practica4.models.Actor;
import com.viewnext.practica4.repositorys.ActorCriteriaRepository;
import com.viewnext.practica4.services.ActorService;
import org.junit.jupiter.api.*;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Para evitar static en @BeforeAll
public class ActorCriteriaControllerTest {

    @Mock
    private ResponseEntity<Actor> responseEntity;

    @Mock
    private ActorCriteriaRepository actorRepository;

    @Mock
    private ResponseEntity<List<Actor>> listResponseEntity;

    @Mock
    private ActorService actorService;

    @InjectMocks
    private ActorControllerCriteria actorController;

    private Actor actor;

    @BeforeAll
    void setUpBeforeAll() {
        System.out.println("Ejecutando configuración global...");
    }

    @AfterAll
    void tearDownAfterAll() {
        System.out.println("Limpieza después de todas las pruebas...");
    }

    // -------------------------------
    // 📌 TEST PARA LISTAR ACTORES
    // -------------------------------
    @Nested
    class ListarActoresTests {

        @BeforeEach
        void setUp() {
            actor = new Actor(100, "Chris", "Evans", 45, "Estados Unidos");

        }

        @Test
        void tesObtenerActores_OK() {
            when(actorService.obtenerActoresCriteria()).thenReturn(Collections.singletonList(actor));
            listResponseEntity = actorController.listarActores();

            assertTrue(listResponseEntity.getBody().size() != 0);

        }

        @Test
        void testObtenerActores_KO() {
            when(actorService.obtenerActoresCriteria()).thenReturn(null);
            listResponseEntity = actorController.listarActores();

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

        @Test
        void testObtenerActorPorId_OK() {
            // Simulamos que el servicio devuelve un actor con el ID especificado
            when(actorService.obtenerActorPorIdCriteria(actor.getIdActor())).thenReturn(Optional.ofNullable(actor));

            // Llamamos al controlador para obtener el actor
            ResponseEntity<Actor> responseEntity = actorController.buscarActor(100);
            // Verificamos que la respuesta no sea nula
            assertNotNull(responseEntity.getBody());
            // Verificamos que el actor tiene los datos correctos
            assertEquals("Chris", responseEntity.getBody().getNombre());
            assertEquals("Evans", responseEntity.getBody().getApellido());
        }

        @Test
        void testObtenerActorPorId_KO() {
            // Simulamos que el servicio devuelve un Optional vacío (no se encuentra el actor)
            when(actorService.obtenerActorPorIdCriteria(100)).thenThrow(new NoSuchElementException());

            // Llamamos al controlador y verificamos que lance una excepción
            assertThrows(NoSuchElementException.class, () -> actorController.buscarActor(actor.getIdActor()));
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

        @Test
        void testInsertarActor_OK() {
            when(actorService.obtenerActorPorIdCriteria(actor.getIdActor())).thenReturn(Optional.ofNullable(actor));

            actorController.insertarActor(actor);
            actor = actorController.buscarActor(actor.getIdActor()).getBody();
            // Verificamos que el cuerpo de la respuesta no sea nulo
            assertNotNull(actor);
            // Verificamos que los datos del actor insertado sean correctos
            assertEquals("Chris", actor.getNombre());
            assertEquals("Evans", actor.getApellido());
        }

        @Test
        void testInsertarActor_KO() {
            // Simulamos que el servicio devuelve un Optional vacío (no se encuentra el actor)
            when(actorService.obtenerActorPorIdCriteria(100)).thenThrow(new NoSuchElementException());

            actorController.insertarActor(actor);

            // Llamamos al controlador y verificamos que lance una excepción
            assertThrows(NoSuchElementException.class, () -> actorController.buscarActor(actor.getIdActor()).getBody());
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

        @Test
        void testActualizarActor_OK() {
            // Simulamos que el servicio devuelve el actor actualizado
            Actor updatedActor = new Actor(100, "Chris", "Evans", 46, "Estados Unidos");  // Edad actualizada

            // Llamamos al controlador para actualizar el actor
            ResponseEntity<String> responseEntity = actorController.actualizarActor(actor.getIdActor(), updatedActor);

            // Verificamos que el cuerpo de la respuesta no sea nulo
            assertNotNull(responseEntity.getBody());
            // Verificamos que la respuesta tenga el código de estado 200 OK
            assertEquals(200, responseEntity.getStatusCodeValue());
        }

        @Test
        void testActualizarActor_KO() {
            // Simulamos que el servicio devuelve null, lo que indicaría que el actor no se ha encontrado
            when(actorController.actualizarActor(actor.getIdActor(), actor)).thenThrow(
                    new RuntimeException("No se encontró el actor"));

            // Llamamos al controlador para actualizar el actor y verificamos que lance una excepción
            RuntimeException thrown = assertThrows(RuntimeException.class,
                    () -> actorController.actualizarActor(actor.getIdActor(), actor));

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

        @Test
        void testEliminarActor_OK() {
            // Simulamos que el servicio elimina correctamente el actor
            doNothing().when(actorService).eliminarActorCriteria(actor.getIdActor());

            // Llamamos al controlador para eliminar el actor
            ResponseEntity<String> responseEntity = actorController.borrarActor(actor.getIdActor());

            // Verificamos que el código de estado sea 200 OK
            assertEquals(200, responseEntity.getStatusCodeValue());
        }

        @Test
        void testEliminarActor_KO() {
            // Simulamos que el servicio lanza una excepción al intentar eliminar el actor
            when(actorController.borrarActor(100)).thenThrow(new RuntimeException("No se encontró el actor"));

            // Llamamos al controlador y verificamos que lance una excepción
            RuntimeException thrown = assertThrows(RuntimeException.class,
                    () -> actorController.borrarActor(actor.getIdActor()));

            // Verificamos que el mensaje de la excepción sea el esperado
            assertEquals("No se encontró el actor", thrown.getMessage());
        }
    }

}
