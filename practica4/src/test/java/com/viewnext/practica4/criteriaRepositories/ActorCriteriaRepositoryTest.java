package com.viewnext.practica4.criteriaRepositories;

import com.viewnext.practica4.models.Actor;
import com.viewnext.practica4.repositorys.ActorCriteriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Para evitar static en @BeforeAll
class ActorCriteriaRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<Actor> criteriaQuery;

    @Mock
    private CriteriaUpdate<Actor> criteriaUpdate;

    @Mock
    private CriteriaDelete<Actor> criteriaDelete;

    @Mock
    private Root<Actor> root;

    @Mock
    private TypedQuery<Actor> typedQuery;

    @Mock
    private Predicate predicate;

    @InjectMocks
    private ActorCriteriaRepository actorCriteriaRepository;

    private Actor actor1;

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
            actor1 = new Actor(1, "Bryan", "Cranston", 67, "EE.UU");
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createQuery(Actor.class)).thenReturn(criteriaQuery);
            when(criteriaQuery.from(Actor.class)).thenReturn(root);
            when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        }

        @Test
        void testListarActores_OK() {
            List<Actor> actores = List.of(actor1);
            when(typedQuery.getResultList()).thenReturn(actores);

            List<Actor> resultado = actorCriteriaRepository.listarActores();

            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }

        @Test
        void testListarActores_KO() {
            when(typedQuery.getResultList()).thenReturn(List.of());

            List<Actor> resultado = actorCriteriaRepository.listarActores();

            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }
    }

    // -------------------------------
    // 📌 TEST PARA BUSCAR ACTOR POR ID
    // -------------------------------
    @Nested
    class BuscarActorPorIdTests {

        @BeforeEach
        void setUp() {
            actor1 = new Actor(1, "Bryan", "Cranston", 67, "EE.UU");
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createQuery(Actor.class)).thenReturn(criteriaQuery);
            when(criteriaQuery.from(Actor.class)).thenReturn(root);

            Predicate predicateMock = mock(Predicate.class);
            when(criteriaBuilder.equal(root.get("idActor"), actor1.getIdActor())).thenReturn(predicateMock);
            when(criteriaQuery.where(predicateMock)).thenReturn(criteriaQuery);
            when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
            when(typedQuery.getSingleResult()).thenReturn(actor1);
        }

        @Test
        void testBuscarActorPorId_OK() {

            Optional<Actor> resultado = actorCriteriaRepository.buscarActor(1);

            assertTrue(resultado.isPresent());
            assertEquals("Bryan", resultado.get().getNombre());
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }

        @Test
        void testBuscarActorPorId_KO() {
            when(typedQuery.getSingleResult()).thenReturn(null);

            Optional<Actor> resultado = actorCriteriaRepository.buscarActor(1);

            assertFalse(resultado.isPresent());
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }
    }

    // -------------------------------
    // 📌 TEST PARA INSERTAR ACTOR
    // -------------------------------
    @Nested
    class InsertarActorTests {

        @Test
        void testInsertarActor_OK() {
            actor1 = new Actor(1, "Bryan", "Cranston", 67, "EE.UU");

            // No se necesita un `when()` ya que persist() es void
            doNothing().when(entityManager).persist(actor1);

            actorCriteriaRepository.insertarActor(actor1);

            verify(entityManager, times(1)).persist(actor1);
        }

        @Test
        void testInsertarActor_KO() {
            actor1 = new Actor(1, "Bryan", "Cranston", 67, "EE.UU");

            doThrow(new IllegalArgumentException("Error al insertar actor")).when(entityManager).persist(actor1);

            assertThrows(IllegalArgumentException.class, () -> actorCriteriaRepository.insertarActor(actor1));

            verify(entityManager, times(1)).persist(actor1);
        }
    }

    // -------------------------------
    // 📌 TEST PARA ELIMINAR ACTOR
    // -------------------------------
    @Nested
    class EliminarActorTests {

        @BeforeEach
        void setUp() {
            actor1 = new Actor(1, "Bryan", "Cranston", 67, "EE.UU");

            // Mockear el comportamiento del CriteriaBuilder
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

            // Mockear el CriteriaDelete y la creación de la consulta
            when(criteriaBuilder.createCriteriaDelete(Actor.class)).thenReturn(criteriaDelete);
            when(criteriaDelete.from(Actor.class)).thenReturn(root);

            // Mockear el comportamiento de 'root.get("idActor")' y crear un Predicate
            when(criteriaBuilder.equal(root.get("idActor"), actor1.getIdActor())).thenReturn(predicate);
            when(criteriaDelete.where(predicate)).thenReturn(criteriaDelete);

            // Simulamos la ejecución de createQuery con el criteriaDelete
            when(entityManager.createQuery(criteriaDelete)).thenReturn(typedQuery);

            // Simular que executeUpdate() devuelve 1, indicando que la eliminación fue exitosa
            when(typedQuery.executeUpdate()).thenReturn(1);
        }

        @Test
        void testEliminarActor_OK() {

            actorCriteriaRepository.borrarActorPorId(actor1.getIdActor());
            verify(entityManager, times(1)).createQuery(criteriaDelete);
            verify(typedQuery, times(1)).executeUpdate();

        }

        @Test
        void testEliminarActor_KO() {
            // Simulamos el caso en el que no encontramos al actor en la base de datos
            when(entityManager.find(Actor.class, 99)).thenReturn(null);

            // Verificamos que se lance una excepción cuando no se encuentre al actor
            assertThrows(RuntimeException.class, () -> actorCriteriaRepository.borrarActorPorId(99));

            // Verificamos que el método remove no haya sido llamado si no encontramos al actor
            verify(entityManager, never()).remove(any(Actor.class));
        }
    }

    // -------------------------------
    // 📌 TEST PARA ACTUALIZAR ACTOR
    // -------------------------------
    @Nested
    class ActualizarActorTests {

        @BeforeEach
        void setUp() {
            actor1 = new Actor(1, "Bryan", "Cranston", 67, "EE.UU");

        }

        @Test
        void testActualizarActor_OK() {
            // Mockear el comportamiento del CriteriaBuilder
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createCriteriaUpdate(Actor.class)).thenReturn(criteriaUpdate);
            when(criteriaUpdate.from(Actor.class)).thenReturn(root);

            // Mockear el set de campos en la actualización
            when(criteriaUpdate.set(root.get("nombre"), actor1.getNombre())).thenReturn(criteriaUpdate);
            when(criteriaUpdate.set(root.get("apellido"), actor1.getApellido())).thenReturn(criteriaUpdate);
            when(criteriaUpdate.set(root.get("edad"), actor1.getEdad())).thenReturn(criteriaUpdate);
            when(criteriaUpdate.set(root.get("nacionalidad"), actor1.getNacionalidad())).thenReturn(criteriaUpdate);

            // Mockear el comportamiento de 'root.get("idActor")' y crear un Predicate
            when(criteriaBuilder.equal(root.get("idActor"), actor1.getIdActor())).thenReturn(predicate);
            when(criteriaUpdate.where(predicate)).thenReturn(criteriaUpdate);

            // Simular la creación de la consulta
            when(entityManager.createQuery(criteriaUpdate)).thenReturn(typedQuery);
            when(typedQuery.executeUpdate()).thenReturn(1); // No hace nada cuando se ejecuta el update

            actorCriteriaRepository.actualizarActor(1, actor1);

            assertNotNull(actor1);
            assertEquals("Bryan", actor1.getNombre());
        }

        @Test
        void testActualizarActor_KO() {
            // Mockear el comportamiento del CriteriaBuilder
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createCriteriaUpdate(Actor.class)).thenReturn(criteriaUpdate);
            when(criteriaUpdate.from(Actor.class)).thenReturn(root);

            actor1 = new Actor(1, "", "Cranston", 67, "EE.UU");

            when(criteriaUpdate.set(root.get("nombre"), actor1.getNombre())).thenThrow(
                    new IllegalArgumentException("No se puede poner un campo vacio"));

            assertThrows(IllegalArgumentException.class, () -> actorCriteriaRepository.actualizarActor(1, actor1));

        }
    }
}
