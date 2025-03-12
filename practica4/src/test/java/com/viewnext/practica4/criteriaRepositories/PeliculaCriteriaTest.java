package com.viewnext.practica4.criteriaRepositories;

import com.viewnext.practica4.models.Actor;
import com.viewnext.practica4.models.Director;
import com.viewnext.practica4.models.Pelicula;
import com.viewnext.practica4.models.Productora;
import com.viewnext.practica4.repositorys.PeliculaCriteriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Para evitar static en @BeforeAll
class PeliculaCriteriaRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<Pelicula> criteriaQuery;

    @Mock
    private CriteriaUpdate<Pelicula> criteriaUpdate;

    @Mock
    private CriteriaDelete<Pelicula> criteriaDelete;

    @Mock
    private Root<Pelicula> root;

    @Mock
    private TypedQuery<Pelicula> typedQuery;

    @Mock
    private Predicate predicate;

    @InjectMocks
    private PeliculaCriteriaRepository peliculaCriteriaRepository;

    private Pelicula pelicula1;
    private Director director1;
    private Productora productora1;
    private Actor actor1;

    @BeforeAll
    void setUpBeforeAll() {
        director1 = new Director(1, "Vince", "Gilligan", 57, "EE.UU");
        productora1 = new Productora(1, "AMC", LocalDate.of(1994, 1, 1));
        actor1 = new Actor(1, "Bryan", "Cranston", 67, "EE.UU");

        pelicula1 = new Pelicula(1, "Breaking Bad", LocalDate.of(2008, 1, 20), director1, productora1, List.of(actor1));

        System.out.println("Ejecutando configuración global...");
    }

    @AfterAll
    void tearDownAfterAll() {
        System.out.println("Limpieza después de todas las pruebas...");
    }

    // -------------------------------
    // 📌 TEST PARA LISTAR PELÍCULAS
    // -------------------------------
    @Nested
    class ListarPeliculasTests {

        @BeforeEach
        void setUp() {
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createQuery(Pelicula.class)).thenReturn(criteriaQuery);
            when(criteriaQuery.from(Pelicula.class)).thenReturn(root);
            when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        }

        @Test
        void testListarPeliculas_OK() {
            List<Pelicula> peliculas = List.of(pelicula1);
            when(typedQuery.getResultList()).thenReturn(peliculas);

            List<Pelicula> resultado = peliculaCriteriaRepository.listarPeliculas();

            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }

        @Test
        void testListarPeliculas_KO() {
            when(typedQuery.getResultList()).thenReturn(List.of());

            List<Pelicula> resultado = peliculaCriteriaRepository.listarPeliculas();

            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }
    }

    // -------------------------------
    // 📌 TEST PARA BUSCAR PELÍCULA POR ID
    // -------------------------------
    @Nested
    class BuscarPeliculaPorIdTests {

        @BeforeEach
        void setUp() {
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createQuery(Pelicula.class)).thenReturn(criteriaQuery);
            when(criteriaQuery.from(Pelicula.class)).thenReturn(root);

            Predicate predicateMock = mock(Predicate.class);
            when(criteriaBuilder.equal(root.get("idPelicula"), pelicula1.getIdPelicula())).thenReturn(predicateMock);
            when(criteriaQuery.where(predicateMock)).thenReturn(criteriaQuery);
            when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
            when(typedQuery.getSingleResult()).thenReturn(pelicula1);
        }

        @Test
        void testBuscarPeliculaPorId_OK() {

            Pelicula resultado = peliculaCriteriaRepository.buscarPelicula(1);

            assertNotNull(resultado);
            assertEquals("Breaking Bad", resultado.getTitulo());
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }

        @Test
        void testBuscarPeliculaPorId_KO() {
            when(typedQuery.getSingleResult()).thenReturn(null);

            Pelicula resultado = peliculaCriteriaRepository.buscarPelicula(1);

            assertNull(resultado);
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }
    }

    // -------------------------------
    // 📌 TEST PARA INSERTAR PELÍCULA
    // -------------------------------
    @Nested
    class InsertarPeliculaTests {

        @Test
        void testInsertarPelicula_OK() {
            pelicula1 = new Pelicula(1, "Breaking Bad", LocalDate.of(2008, 1, 20), director1, productora1,
                    List.of(actor1));

            // No se necesita un `when()` ya que persist() es void
            doNothing().when(entityManager).persist(pelicula1);

            peliculaCriteriaRepository.insertarPelicula(pelicula1);

            verify(entityManager, times(1)).persist(pelicula1);
        }

        @Test
        void testInsertarPelicula_KO() {
            pelicula1 = new Pelicula(1, "Breaking Bad", LocalDate.of(2008, 1, 20), director1, productora1,
                    List.of(actor1));

            doThrow(new IllegalArgumentException("Error al insertar película")).when(entityManager).persist(pelicula1);

            assertThrows(IllegalArgumentException.class, () -> peliculaCriteriaRepository.insertarPelicula(pelicula1));

            verify(entityManager, times(1)).persist(pelicula1);
        }
    }

    // -------------------------------
    // 📌 TEST PARA ELIMINAR PELÍCULA
    // -------------------------------
    @Nested
    class EliminarPeliculaTests {

        @BeforeEach
        void setUp() {
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

            when(criteriaBuilder.createCriteriaDelete(Pelicula.class)).thenReturn(criteriaDelete);
            when(criteriaDelete.from(Pelicula.class)).thenReturn(root);

            when(criteriaBuilder.equal(root.get("idPelicula"), pelicula1.getIdPelicula())).thenReturn(predicate);
            when(criteriaDelete.where(predicate)).thenReturn(criteriaDelete);

            when(entityManager.createQuery(criteriaDelete)).thenReturn(typedQuery);
            when(typedQuery.executeUpdate()).thenReturn(1);
        }

        @Test
        void testEliminarPelicula_OK() {
            peliculaCriteriaRepository.borrarPeliculaPorId(pelicula1.getIdPelicula());
            verify(entityManager, times(1)).createQuery(criteriaDelete);
            verify(typedQuery, times(1)).executeUpdate();
        }

        @Test
        void testEliminarPelicula_KO() {
            // Simulamos el caso en el que no encontramos la película en la base de datos
            when(entityManager.find(Pelicula.class, 99)).thenReturn(null);

            // Verificamos que se lance una excepción cuando no se encuentre la película
            assertThrows(RuntimeException.class, () -> peliculaCriteriaRepository.borrarPeliculaPorId(99));

            // Verificamos que el método remove no haya sido llamado si no encontramos la película
            verify(entityManager, never()).remove(any(Pelicula.class));
        }
    }

    // -------------------------------
    // 📌 TEST PARA ACTUALIZAR PELÍCULA
    // -------------------------------
    @Nested
    class ActualizarPeliculaTests {

        @BeforeEach
        void setUp() {
            pelicula1 = new Pelicula(1, "Breaking Bad", LocalDate.of(2008, 1, 20), director1, productora1,
                    List.of(actor1));
        }

        @Test
        void testActualizarPelicula_OK() {
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createCriteriaUpdate(Pelicula.class)).thenReturn(criteriaUpdate);
            when(criteriaUpdate.from(Pelicula.class)).thenReturn(root);

            when(criteriaUpdate.set(root.get("titulo"), pelicula1.getTitulo())).thenReturn(criteriaUpdate);
            when(criteriaUpdate.set(root.get("ano"), pelicula1.getAno())).thenReturn(criteriaUpdate);
            when(criteriaUpdate.set(root.get("director"), pelicula1.getDirector())).thenReturn(criteriaUpdate);
            when(criteriaUpdate.set(root.get("productora"), pelicula1.getProductora())).thenReturn(criteriaUpdate);
            when(criteriaUpdate.set(root.get("actores"), pelicula1.getActores())).thenReturn(criteriaUpdate);

            when(criteriaBuilder.equal(root.get("idPelicula"), pelicula1.getIdPelicula())).thenReturn(predicate);
            when(criteriaUpdate.where(predicate)).thenReturn(criteriaUpdate);

            when(entityManager.createQuery(criteriaUpdate)).thenReturn(typedQuery);
            when(typedQuery.executeUpdate()).thenReturn(1);

            peliculaCriteriaRepository.actualizarPelicula(1, pelicula1);

            assertNotNull(pelicula1);
            assertEquals("Breaking Bad", pelicula1.getTitulo());
        }

        @Test
        void testActualizarPelicula_KO() {
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createCriteriaUpdate(Pelicula.class)).thenReturn(criteriaUpdate);
            when(criteriaUpdate.from(Pelicula.class)).thenReturn(root);

            pelicula1 = new Pelicula(1, "", LocalDate.of(2008, 1, 20), director1, productora1, List.of(actor1));

            when(criteriaUpdate.set(root.get("titulo"), pelicula1.getTitulo())).thenThrow(
                    new IllegalArgumentException("No se puede poner un campo vacio"));

            assertThrows(IllegalArgumentException.class,
                    () -> peliculaCriteriaRepository.actualizarPelicula(1, pelicula1));
        }
    }
}
