package com.viewnext.practica4.service;

import com.viewnext.practica4.models.Actor;
import com.viewnext.practica4.repositorys.ActorCriteriaRepository;
import com.viewnext.practica4.repositorys.ActorRepository;
import com.viewnext.practica4.services.ActorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private ActorCriteriaRepository actorCriteriaRepository;

    @InjectMocks
    private ActorService actorService;

    private Actor actor1;
    private Actor actor2;
    private Actor actor3;

    @BeforeEach
    void setUp() {
        // Crear instancias de actores de prueb
        actor1 = new Actor(100, "Chris", "Evans", 45, "Estados Unidos");
        actor2 = new Actor(101, "Marcos", "Bolina", 54, "Estados");
        actor3 = new Actor(102, "Fina", "Morgan", 65, "Unidos");

        // Simular respuestas del repositorio con Mockito
        when(actorRepository.findAll()).thenReturn(Arrays.asList(actor1, actor2, actor3));
        when(actorRepository.findByIdActor(100)).thenReturn(Optional.of(actor1));
        when(actorRepository.findByIdActor(101)).thenReturn(Optional.of(actor2));
        when(actorRepository.findByIdActor(102)).thenReturn(Optional.of(actor3));
        when(actorRepository.save(any(Actor.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @AfterEach
    void tearDown() {
        // Limpiar datos simulados de los mocks
        actor1 = null;
        actor2 = null;
        actor3 = null;

        // Reiniciar el mock después de cada prueba para evitar inconsistencias
        reset(actorRepository);
    }

    // -------------------- TESTS CON JPA REPOSITORY --------------------

    @Test
    void testObtenerActores() {
        List<Actor> listaActores = actorService.obtenerActores();

        assertNotNull(listaActores);
        assertEquals(3, listaActores.size());

        verify(actorRepository, times(1)).findAll();
    }

    @Test
    void testObtenerActoresKO() {
        when(actorRepository.findAll()).thenReturn(null);

        List<Actor> listaActores = actorService.obtenerActores();

        assertNull(listaActores);
        verify(actorRepository, times(1)).findAll();
    }

    // --------------------------------------------------

    @Test
    void testObtenerActorPorId() {
        Optional<Actor> actor = actorService.obtenerActorPorId(100);

        assertTrue(actor.isPresent());
        assertEquals("Chris", actor.get().getNombre());

        verify(actorRepository, times(1)).findByIdActor(100);
    }

    @Test
    void testObtenerActorPorIdKO() {
        when(actorRepository.findByIdActor(999)).thenReturn(Optional.empty());

        Optional<Actor> actor = actorService.obtenerActorPorId(999);

        assertFalse(actor.isPresent());
        verify(actorRepository, times(1)).findByIdActor(999);
    }

    // --------------------------------------------------

    @Test
    void testInsertarActor() {
        Actor nuevoActor = new Actor(103, "Robert", "Downey Jr.", 58, "USA");

        actorService.insertarActor(nuevoActor);

        verify(actorRepository, times(1)).save(nuevoActor);
    }

    @Test
    void testInsertarActorKO() {
        Actor nuevoActor = new Actor(103, "", "Downey Jr.", 58, "USA");

        when(actorRepository.save(nuevoActor)).thenThrow(new IllegalArgumentException("No se puede guardar actor"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            actorService.insertarActor(nuevoActor);
        });

        assertEquals("No se puede guardar actor", exception.getMessage());
    }

    // --------------------------------------------------

    @Test
    void testActualizarActor() {
        Actor actorActualizado = new Actor(100, "Chris", "Pratt", 43, "EE.UU");

        when(actorRepository.findByIdActor(100)).thenReturn(Optional.of(actor1));
        when(actorRepository.save(any(Actor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Actor resultado = actorService.actualizarActor(100, actorActualizado);

        assertNotNull(resultado);
        assertEquals("Chris", resultado.getNombre());

        verify(actorRepository, times(1)).findByIdActor(100);
        verify(actorRepository, times(1)).save(any(Actor.class));
    }

    @Test
    void testActualizarActorKO() {
        Actor actorActualizado = new Actor(100, "", "Pratt", 43, "EE.UU");

        when(actorRepository.findByIdActor(100)).thenReturn(Optional.ofNullable(actor1));
        when(actorRepository.save(any(Actor.class))).thenThrow(new IllegalArgumentException("Datos inválidos"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            actorService.actualizarActor(100, actorActualizado);
        });

        assertEquals("Datos inválidos", exception.getMessage());
    }

    // --------------------------------------------------

    @Test
    void testEliminarActor() {
        when(actorRepository.findByIdActor(100)).thenReturn(Optional.of(actor1));

        actorService.eliminarActor(100);

        verify(actorRepository, times(1)).delete(actor1);
    }

    @Test
    void testEliminarActorKO() {
        when(actorRepository.findByIdActor(999)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            actorService.eliminarActor(999);
        });

        verify(actorRepository, times(0)).delete(any());
    }

    @Test
    void testObtenerActoresPaginacionyOrdenados_OK() {
        // Creamos una lista de actores
        List<Actor> actores = new ArrayList<>();
        actores.add(new Actor(1, "Actor 1", "Apellido 1", 30, "Nacionalidad 1"));
        actores.add(new Actor(2, "Actor 2", "Apellido 2", 31, "Nacionalidad 2"));
        actores.add(new Actor(3, "Actor 3", "Apellido 3", 32, "Nacionalidad 3"));

        // Creamos un Pageable con paginación y ordenación
        Pageable pageable = PageRequest.of(0, 2, Sort.by("nombre"));

        // Creamos un Page de actores
        Page<Actor> page = new PageImpl<>(actores, pageable, actores.size());

        // Configuramos el mock
        when(actorRepository.findAll(pageable)).thenReturn(page);

        // Llamamos al método que queremos probar
        Page<Actor> result = actorService.obtenerActores(pageable);

        // Verificamos que el resultado sea correcto
        assertEquals(3, result.getContent().size());
        assertEquals("Actor 1", result.getContent().get(0).getNombre());
        assertEquals("Actor 2", result.getContent().get(1).getNombre());
    }

    @Test
    void testObtenerActoresPaginacionyOrdenados_KO() {
        // Configuración del mock
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nombre"));
        when(actorRepository.findAll(pageable)).thenReturn(Page.empty());

        // Llamada al método que se está testeando
        Page<Actor> actores = actorService.obtenerActores(pageable);

        // Verificación de los resultados
        assertTrue(actores.isEmpty());
        assertEquals(0, actores.getTotalElements());
        assertEquals(1, actores.getTotalPages());
    }

    // -------------------- TESTS CON CRITERIA API --------------------

    @Test
    void testObtenerActoresCriteria() {
        when(actorCriteriaRepository.listarActores()).thenReturn(Arrays.asList(actor1, actor2, actor3));

        List<Actor> listaActores = actorService.obtenerActoresCriteria();

        assertNotNull(listaActores);
        assertEquals(3, listaActores.size());
        verify(actorCriteriaRepository, times(1)).listarActores();
    }

    @Test
    void testObtenerActoresCriteriaKO() {
        when(actorCriteriaRepository.listarActores()).thenReturn(null);

        List<Actor> listaActores = actorService.obtenerActoresCriteria();

        assertNull(listaActores);
        verify(actorCriteriaRepository, times(1)).listarActores();
    }

    // --------------------------------------------------

    @Test
    void testObtenerActorPorIdCriteria() {
        when(actorCriteriaRepository.buscarActor(100)).thenReturn(Optional.of(actor1));

        Optional<Actor> actor = actorService.obtenerActorPorIdCriteria(100);

        assertTrue(actor.isPresent());
        assertEquals("Chris", actor.get().getNombre());

        verify(actorCriteriaRepository, times(1)).buscarActor(100);
    }

    @Test
    void testObtenerActorPorIdCriteriaKO() {
        when(actorCriteriaRepository.buscarActor(999)).thenReturn(Optional.empty());

        Optional<Actor> actor = actorService.obtenerActorPorIdCriteria(999);

        assertFalse(actor.isPresent());
        verify(actorCriteriaRepository, times(1)).buscarActor(999);
    }

    // --------------------------------------------------

    @Test
    void testInsertarActorCriteria() {
        Actor nuevoActor = new Actor(103, "Tom", "Holland", 27, "UK");

        actorService.insertarActorCriteria(nuevoActor);

        verify(actorCriteriaRepository, times(1)).insertarActor(nuevoActor);
    }

    @Test
    void testInsertarActorCriteriaKO() {
        Actor nuevoActor = new Actor(103, "", "Downey Jr.", 58, "USA");

        doThrow(new IllegalArgumentException("No se puede guardar actor")).when(actorCriteriaRepository)
                .insertarActor(nuevoActor);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            actorService.insertarActorCriteria(nuevoActor);
        });

        assertEquals("No se puede guardar actor", exception.getMessage());
    }

    // --------------------------------------------------

    @Test
    void testActualizarActorCriteria() {
        Actor actorActualizado = new Actor(100, "Chris", "Hemsworth", 40, "Australia");

        actorService.actualizarActorCriteria(100, actorActualizado);

        verify(actorCriteriaRepository, times(1)).actualizarActor(100, actorActualizado);
    }

    @Test
    void testActualizarActorCriteriaKO() {
        Actor actorActualizado = new Actor(100, "", "Pratt", 43, "EE.UU");

        doThrow(new IllegalArgumentException("Datos inválidos")).when(actorCriteriaRepository)
                .actualizarActor(100, actorActualizado);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            actorService.actualizarActorCriteria(100, actorActualizado);
        });

        assertEquals("Datos inválidos", exception.getMessage());
    }

    // --------------------------------------------------

    @Test
    void testEliminarActorCriteria() {
        actorService.eliminarActorCriteria(100);

        verify(actorCriteriaRepository, times(1)).borrarActorPorId(100);
    }

    @Test
    void testEliminarActorCriteriaKO() {
        doThrow(new RuntimeException("No se pudo eliminar actor, Actor no encontrado")).when(actorCriteriaRepository)
                .borrarActorPorId(999);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            actorService.eliminarActorCriteria(999);
        });

        assertEquals("No se pudo eliminar actor, Actor no encontrado", exception.getMessage());
    }

}
