package com.viewnext.practica4.criteriaRepositories;

import com.viewnext.practica4.models.Director;
import com.viewnext.practica4.repositorys.DirectorCriteriaRepository;
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
class DirectorCriteriaRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<Director> criteriaQuery;

    @Mock
    private CriteriaUpdate<Director> criteriaUpdate;

    @Mock
    private CriteriaDelete<Director> criteriaDelete;

    @Mock
    private Root<Director> root;

    @Mock
    private TypedQuery<Director> typedQuery;

    @Mock
    private Predicate predicate;

    @InjectMocks
    private DirectorCriteriaRepository directorCriteriaRepository;

    private Director director1;

    @BeforeAll
    void setUpBeforeAll() {
        System.out.println("Ejecutando configuración global...");
    }

    @AfterAll
    void tearDownAfterAll() {
        System.out.println("Limpieza después de todas las pruebas...");
    }

    // -------------------------------
    // 📌 TEST PARA LISTAR DIRECTORES
    // -------------------------------
    @Nested
    class ListarDirectoresTests {

        @BeforeEach
        void setUp() {
            director1 = new Director(1, "Vince", "Gilligan", 57, "EE.UU");
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createQuery(Director.class)).thenReturn(criteriaQuery);
            when(criteriaQuery.from(Director.class)).thenReturn(root);
            when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        }

        @Test
        void testListarDirectores_OK() {
            List<Director> directores = List.of(director1);
            when(typedQuery.getResultList()).thenReturn(directores);

            List<Director> resultado = directorCriteriaRepository.listarDirectores();

            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }

        @Test
        void testListarDirectores_KO() {
            when(typedQuery.getResultList()).thenReturn(List.of());

            List<Director> resultado = directorCriteriaRepository.listarDirectores();

            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }
    }

    // -------------------------------
    // 📌 TEST PARA BUSCAR DIRECTOR POR ID
    // -------------------------------
    @Nested
    class BuscarDirectorPorIdTests {

        @BeforeEach
        void setUp() {
            director1 = new Director(1, "Vince", "Gilligan", 57, "EE.UU");
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createQuery(Director.class)).thenReturn(criteriaQuery);
            when(criteriaQuery.from(Director.class)).thenReturn(root);

            Predicate predicateMock = mock(Predicate.class);
            when(criteriaBuilder.equal(root.get("idDirector"), director1.getIdDirector())).thenReturn(predicateMock);
            when(criteriaQuery.where(predicateMock)).thenReturn(criteriaQuery);
            when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
            when(typedQuery.getSingleResult()).thenReturn(director1);
        }

        @Test
        void testBuscarDirectorPorId_OK() {

            Optional<Director> resultado = directorCriteriaRepository.buscarDirector(1);

            assertTrue(resultado.isPresent());
            assertEquals("Vince", resultado.get().getNombre());
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }

        @Test
        void testBuscarDirectorPorId_KO() {
            when(typedQuery.getSingleResult()).thenReturn(null);

            Optional<Director> resultado = directorCriteriaRepository.buscarDirector(1);

            assertFalse(resultado.isPresent());
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }
    }

    // -------------------------------
    // 📌 TEST PARA INSERTAR DIRECTOR
    // -------------------------------
    @Nested
    class InsertarDirectorTests {

        @Test
        void testInsertarDirector_OK() {
            director1 = new Director(1, "Vince", "Gilligan", 57, "EE.UU");

            // No se necesita un `when()` ya que persist() es void
            doNothing().when(entityManager).persist(director1);

            directorCriteriaRepository.insertarDirector(director1);

            verify(entityManager, times(1)).persist(director1);
        }

        @Test
        void testInsertarDirector_KO() {
            director1 = new Director(1, "Vince", "Gilligan", 57, "EE.UU");

            doThrow(new IllegalArgumentException("Error al insertar director")).when(entityManager).persist(director1);

            assertThrows(IllegalArgumentException.class, () -> directorCriteriaRepository.insertarDirector(director1));

            verify(entityManager, times(1)).persist(director1);
        }
    }

    // -------------------------------
    // 📌 TEST PARA ELIMINAR DIRECTOR
    // -------------------------------
    @Nested
    class EliminarDirectorTests {

        @BeforeEach
        void setUp() {
            director1 = new Director(1, "Vince", "Gilligan", 57, "EE.UU");

            // Mockear el comportamiento del CriteriaBuilder
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

            // Mockear el CriteriaDelete y la creación de la consulta
            when(criteriaBuilder.createCriteriaDelete(Director.class)).thenReturn(criteriaDelete);
            when(criteriaDelete.from(Director.class)).thenReturn(root);

            // Mockear el comportamiento de 'root.get("idDirector")' y crear un Predicate
            when(criteriaBuilder.equal(root.get("idDirector"), director1.getIdDirector())).thenReturn(predicate);
            when(criteriaDelete.where(predicate)).thenReturn(criteriaDelete);

            // Simulamos la ejecución de createQuery con el criteriaDelete
            when(entityManager.createQuery(criteriaDelete)).thenReturn(typedQuery);

            // Simular que executeUpdate() devuelve 1, indicando que la eliminación fue exitosa
            when(typedQuery.executeUpdate()).thenReturn(1);
        }

        @Test
        void testEliminarDirector_OK() {
            directorCriteriaRepository.borrarDirectorPorId(director1.getIdDirector());
            verify(entityManager, times(1)).createQuery(criteriaDelete);
            verify(typedQuery, times(1)).executeUpdate();
        }

        @Test
        void testEliminarDirector_KO() {
            // Simulamos el caso en el que no encontramos al director en la base de datos
            when(entityManager.find(Director.class, 99)).thenReturn(null);

            // Verificamos que se lance una excepción cuando no se encuentre al director
            assertThrows(RuntimeException.class, () -> directorCriteriaRepository.borrarDirectorPorId(99));

            // Verificamos que el método remove no haya sido llamado si no encontramos al director
            verify(entityManager, never()).remove(any(Director.class));
        }
    }

    // -------------------------------
    // 📌 TEST PARA ACTUALIZAR DIRECTOR
    // -------------------------------
    @Nested
    class ActualizarDirectorTests {

        @BeforeEach
        void setUp() {
            director1 = new Director(1, "Vince", "Gilligan", 57, "EE.UU");
        }

        @Test
        void testActualizarDirector_OK() {
            // Mockear el comportamiento del CriteriaBuilder
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createCriteriaUpdate(Director.class)).thenReturn(criteriaUpdate);
            when(criteriaUpdate.from(Director.class)).thenReturn(root);

            // Mockear el set de campos en la actualización
            when(criteriaUpdate.set(root.get("nombre"), director1.getNombre())).thenReturn(criteriaUpdate);
            when(criteriaUpdate.set(root.get("apellido"), director1.getApellido())).thenReturn(criteriaUpdate);
            when(criteriaUpdate.set(root.get("edad"), director1.getEdad())).thenReturn(criteriaUpdate);
            when(criteriaUpdate.set(root.get("nacionalidad"), director1.getNacionalidad())).thenReturn(criteriaUpdate);

            // Mockear el comportamiento de 'root.get("idDirector")' y crear un Predicate
            when(criteriaBuilder.equal(root.get("idDirector"), director1.getIdDirector())).thenReturn(predicate);
            when(criteriaUpdate.where(predicate)).thenReturn(criteriaUpdate);

            // Simular la creación de la consulta
            when(entityManager.createQuery(criteriaUpdate)).thenReturn(typedQuery);
            when(typedQuery.executeUpdate()).thenReturn(1); // No hace nada cuando se ejecuta el update

            directorCriteriaRepository.actualizarDirector(1, director1);

            assertNotNull(director1);
            assertEquals("Vince", director1.getNombre());
        }

        @Test
        void testActualizarDirector_KO() {
            // Mockear el comportamiento del CriteriaBuilder
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createCriteriaUpdate(Director.class)).thenReturn(criteriaUpdate);
            when(criteriaUpdate.from(Director.class)).thenReturn(root);

            director1 = new Director(1, "", "Gilligan", 57, "EE.UU");

            when(criteriaUpdate.set(root.get("nombre"), director1.getNombre())).thenThrow(
                    new IllegalArgumentException("No se puede poner un campo vacio"));

            assertThrows(IllegalArgumentException.class,
                    () -> directorCriteriaRepository.actualizarDirector(1, director1));

        }
    }
}
