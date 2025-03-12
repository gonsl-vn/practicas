package com.viewnext.practica4.contrellers;

import com.viewnext.practica4.controllers.ActorController;
import com.viewnext.practica4.models.Actor;
import com.viewnext.practica4.repositorys.ActorRepository;
import com.viewnext.practica4.services.ActorService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Para evitar static en @BeforeAll
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
            when(actorService.obtenerActores()).thenReturn(Collections.singletonList(actor));
            listResponseEntity = actorController.obtenerActores();

            assertTrue(listResponseEntity.hasBody());
        }

        @Test
        void testObtenerActores_KO() {
            when(actorService.obtenerActores()).thenReturn(null);
            listResponseEntity = actorController.obtenerActores();

            assertFalse(listResponseEntity.getBody() != null);
        }
    }

}
