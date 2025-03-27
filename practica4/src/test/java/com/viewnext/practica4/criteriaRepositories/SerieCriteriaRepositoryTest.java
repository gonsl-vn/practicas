package com.viewnext.practica4.criteriaRepositories;

import com.viewnext.practica4.models.Serie;
import com.viewnext.practica4.repositorys.SerieCriteriaRepository;
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
class SerieCriteriaRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<Serie> criteriaQuery;

    @Mock
    private CriteriaUpdate<Serie> criteriaUpdate;

    @Mock
    private CriteriaDelete<Serie> criteriaDelete;

    @Mock
    private Root<Serie> root;

    @Mock
    private TypedQuery<Serie> typedQuery;

    @Mock
    private Predicate predicate;

    @InjectMocks
    private SerieCriteriaRepository serieCriteriaRepository;

    private Serie serie1;

    @BeforeAll
    void setUpBeforeAll() {
        serie1 = new Serie(1, "Breaking Bad", LocalDate.of(2008, 1, 20), null, null, null);
        System.out.println("Ejecutando configuración global...");
    }

    @AfterAll
    void tearDownAfterAll() {
        System.out.println("Limpieza después de todas las pruebas...");
    }

    // -------------------------------
    // 📌 TEST PARA LISTAR SERIES
    // -------------------------------
    @Nested
    class ListarSeriesTests {

        @BeforeEach
        void setUp() {
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createQuery(Serie.class)).thenReturn(criteriaQuery);
            when(criteriaQuery.from(Serie.class)).thenReturn(root);
            when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        }

        @Test
        void testListarSeries_OK() {
            List<Serie> series = List.of(serie1);
            when(typedQuery.getResultList()).thenReturn(series);

            List<Serie> resultado = serieCriteriaRepository.listarSeries();

            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }

        @Test
        void testListarSeries_KO() {
            when(typedQuery.getResultList()).thenReturn(List.of());

            List<Serie> resultado = serieCriteriaRepository.listarSeries();

            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }
    }

    // -------------------------------
    // 📌 TEST PARA BUSCAR SERIE POR ID
    // -------------------------------
    @Nested
    class BuscarSeriePorIdTests {

        @BeforeEach
        void setUp() {
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createQuery(Serie.class)).thenReturn(criteriaQuery);
            when(criteriaQuery.from(Serie.class)).thenReturn(root);

            Predicate predicateMock = mock(Predicate.class);
            when(criteriaBuilder.equal(root.get("idSerie"), serie1.getIdSerie())).thenReturn(predicateMock);
            when(criteriaQuery.where(predicateMock)).thenReturn(criteriaQuery);
            when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
            when(typedQuery.getSingleResult()).thenReturn(serie1);
        }

        @Test
        void testBuscarSeriePorId_OK() {

            Serie resultado = serieCriteriaRepository.buscarSerie(1);

            assertNotNull(resultado);
            assertEquals("Breaking Bad", resultado.getTitulo());
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }

        @Test
        void testBuscarSeriePorId_KO() {
            when(typedQuery.getSingleResult()).thenReturn(null);

            Serie resultado = serieCriteriaRepository.buscarSerie(1);

            assertNull(resultado);
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }
    }

    // -------------------------------
    // 📌 TEST PARA INSERTAR SERIE
    // -------------------------------
    @Nested
    class InsertarSerieTests {

        @Test
        void testInsertarSerie_OK() {
            serie1 = new Serie(1, "Breaking Bad", LocalDate.of(2008, 1, 20), null, null, null);

            // No se necesita un `when()` ya que persist() es void
            doNothing().when(entityManager).persist(serie1);

            serieCriteriaRepository.insertarSerie(serie1);

            verify(entityManager, times(1)).persist(serie1);
        }

        @Test
        void testInsertarSerie_KO() {
            serie1 = new Serie(1, "Breaking Bad", LocalDate.of(2008, 1, 20), null, null, null);

            doThrow(new IllegalArgumentException("Error al insertar serie")).when(entityManager).persist(serie1);

            assertThrows(IllegalArgumentException.class, () -> serieCriteriaRepository.insertarSerie(serie1));

            verify(entityManager, times(1)).persist(serie1);
        }
    }

    // -------------------------------
    // 📌 TEST PARA ELIMINAR SERIE
    // -------------------------------
    @Nested
    class EliminarSerieTests {

        @BeforeEach
        void setUp() {
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

            when(criteriaBuilder.createCriteriaDelete(Serie.class)).thenReturn(criteriaDelete);
            when(criteriaDelete.from(Serie.class)).thenReturn(root);

            when(criteriaBuilder.equal(root.get("idSerie"), serie1.getIdSerie())).thenReturn(predicate);
            when(criteriaDelete.where(predicate)).thenReturn(criteriaDelete);

            when(entityManager.createQuery(criteriaDelete)).thenReturn(typedQuery);
            when(typedQuery.executeUpdate()).thenReturn(1);
        }

        @Test
        void testEliminarSerie_OK() {
            serieCriteriaRepository.borrarSeriePorId(serie1.getIdSerie());

            verify(entityManager, times(1)).createQuery(criteriaDelete);
            verify(typedQuery, times(1)).executeUpdate();
        }

        @Test
        void testEliminarSerie_KO() {
            // Simulamos el caso en el que no encontramos la serie en la base de datos
            when(entityManager.find(Serie.class, 99)).thenReturn(null);

            // Verificamos que se lance una excepción cuando no se encuentre la serie
            assertThrows(RuntimeException.class, () -> serieCriteriaRepository.borrarSeriePorId(99));

            // Verificamos que el método remove no haya sido llamado si no encontramos la serie
            verify(entityManager, never()).remove(any(Serie.class));
        }
    }

    // -------------------------------
    // 📌 TEST PARA ACTUALIZAR SERIE
    // -------------------------------
    @Nested
    class ActualizarSerieTests {

        @BeforeEach
        void setUp() {
            serie1 = new Serie(1, "Breaking Bad", LocalDate.of(2008, 1, 20), null, null, null);
        }

        @Test
        void testActualizarSerie_OK() {
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createCriteriaUpdate(Serie.class)).thenReturn(criteriaUpdate);
            when(criteriaUpdate.from(Serie.class)).thenReturn(root);

            when(criteriaUpdate.set(root.get("titulo"), serie1.getTitulo())).thenReturn(criteriaUpdate);
            when(criteriaUpdate.set(root.get("ano"), serie1.getAno())).thenReturn(criteriaUpdate);

            when(criteriaBuilder.equal(root.get("idSerie"), serie1.getIdSerie())).thenReturn(predicate);
            when(criteriaUpdate.where(predicate)).thenReturn(criteriaUpdate);

            when(entityManager.createQuery(criteriaUpdate)).thenReturn(typedQuery);
            when(typedQuery.executeUpdate()).thenReturn(1);

            serieCriteriaRepository.actualizarSerie(1, serie1);

            assertNotNull(serie1);
            assertEquals("Breaking Bad", serie1.getTitulo());
        }

        @Test
        void testActualizarSerie_KO() {
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createCriteriaUpdate(Serie.class)).thenReturn(criteriaUpdate);
            when(criteriaUpdate.from(Serie.class)).thenReturn(root);

            serie1 = new Serie(1, "", LocalDate.of(2008, 1, 20), null, null, null);

            when(criteriaUpdate.set(root.get("titulo"), serie1.getTitulo())).thenThrow(
                    new IllegalArgumentException("No se puede poner un campo vacío"));

            assertThrows(IllegalArgumentException.class, () -> serieCriteriaRepository.actualizarSerie(1, serie1));
        }
    }
}
