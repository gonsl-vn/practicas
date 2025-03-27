package com.viewnext.practica4.criteriaRepositories;

import com.viewnext.practica4.models.Productora;
import com.viewnext.practica4.repositorys.ProductoraCriteriaRepository;
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
class ProductoraCriteriaRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<Productora> criteriaQuery;

    @Mock
    private CriteriaUpdate<Productora> criteriaUpdate;

    @Mock
    private CriteriaDelete<Productora> criteriaDelete;

    @Mock
    private Root<Productora> root;

    @Mock
    private TypedQuery<Productora> typedQuery;

    @Mock
    private Predicate predicate;

    @InjectMocks
    private ProductoraCriteriaRepository productoraCriteriaRepository;

    private Productora productora1;

    @BeforeAll
    void setUpBeforeAll() {
        productora1 = new Productora(1, "AMC", LocalDate.of(1994, 1, 1));
        System.out.println("Ejecutando configuración global...");
    }

    @AfterAll
    void tearDownAfterAll() {
        System.out.println("Limpieza después de todas las pruebas...");
    }

    // -------------------------------
    // 📌 TEST PARA LISTAR PRODUCTORAS
    // -------------------------------
    @Nested
    class ListarProductorasTests {

        @BeforeEach
        void setUp() {
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createQuery(Productora.class)).thenReturn(criteriaQuery);
            when(criteriaQuery.from(Productora.class)).thenReturn(root);
            when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        }

        @Test
        void testListarProductoras_OK() {
            List<Productora> productoras = List.of(productora1);
            when(typedQuery.getResultList()).thenReturn(productoras);

            List<Productora> resultado = productoraCriteriaRepository.listarProductoras();

            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }

        @Test
        void testListarProductoras_KO() {
            when(typedQuery.getResultList()).thenReturn(List.of());

            List<Productora> resultado = productoraCriteriaRepository.listarProductoras();

            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }
    }

    // -------------------------------
    // 📌 TEST PARA BUSCAR PRODUCTORA POR ID
    // -------------------------------
    @Nested
    class BuscarProductoraPorIdTests {

        @BeforeEach
        void setUp() {
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createQuery(Productora.class)).thenReturn(criteriaQuery);
            when(criteriaQuery.from(Productora.class)).thenReturn(root);

            Predicate predicateMock = mock(Predicate.class);
            when(criteriaBuilder.equal(root.get("idProductora"), productora1.getIdProductora())).thenReturn(
                    predicateMock);
            when(criteriaQuery.where(predicateMock)).thenReturn(criteriaQuery);
            when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
            when(typedQuery.getSingleResult()).thenReturn(productora1);
        }

        @Test
        void testBuscarProductoraPorId_OK() {

            Productora resultado = productoraCriteriaRepository.buscarProductora(1);

            assertNotNull(resultado);
            assertEquals("AMC", resultado.getNombre());
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }

        @Test
        void testBuscarProductoraPorId_KO() {
            when(typedQuery.getSingleResult()).thenReturn(null);

            Productora resultado = productoraCriteriaRepository.buscarProductora(1);

            assertNull(resultado);
            verify(entityManager, times(1)).createQuery(criteriaQuery);
        }
    }

    // -------------------------------
    // 📌 TEST PARA INSERTAR PRODUCTORA
    // -------------------------------
    @Nested
    class InsertarProductoraTests {

        @Test
        void testInsertarProductora_OK() {
            productora1 = new Productora(1, "AMC", LocalDate.of(1994, 1, 1));

            // No se necesita un `when()` ya que persist() es void
            doNothing().when(entityManager).persist(productora1);

            productoraCriteriaRepository.insertarProductora(productora1);

            verify(entityManager, times(1)).persist(productora1);
        }

        @Test
        void testInsertarProductora_KO() {
            productora1 = new Productora(1, "AMC", LocalDate.of(1994, 1, 1));

            doThrow(new IllegalArgumentException("Error al insertar productora")).when(entityManager)
                    .persist(productora1);

            assertThrows(IllegalArgumentException.class,
                    () -> productoraCriteriaRepository.insertarProductora(productora1));

            verify(entityManager, times(1)).persist(productora1);
        }
    }

    // -------------------------------
    // 📌 TEST PARA ELIMINAR PRODUCTORA
    // -------------------------------
    @Nested
    class EliminarProductoraTests {

        @BeforeEach
        void setUp() {
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

            when(criteriaBuilder.createCriteriaDelete(Productora.class)).thenReturn(criteriaDelete);
            when(criteriaDelete.from(Productora.class)).thenReturn(root);

            when(criteriaBuilder.equal(root.get("idProductora"), productora1.getIdProductora())).thenReturn(predicate);
            when(criteriaDelete.where(predicate)).thenReturn(criteriaDelete);

            when(entityManager.createQuery(criteriaDelete)).thenReturn(typedQuery);
            when(typedQuery.executeUpdate()).thenReturn(1);
        }

        @Test
        void testEliminarProductora_OK() {
            productoraCriteriaRepository.borrarProductoraPorId(productora1.getIdProductora());

            verify(entityManager, times(1)).createQuery(criteriaDelete);
            verify(typedQuery, times(1)).executeUpdate();
        }

        @Test
        void testEliminarProductora_KO() {
            // Simulamos el caso en el que no encontramos la productora en la base de datos
            when(entityManager.find(Productora.class, 99)).thenReturn(null);

            // Verificamos que se lance una excepción cuando no se encuentre la productora
            assertThrows(RuntimeException.class, () -> productoraCriteriaRepository.borrarProductoraPorId(99));

            // Verificamos que el método remove no haya sido llamado si no encontramos la productora
            verify(entityManager, never()).remove(any(Productora.class));
        }
    }

    // -------------------------------
    // 📌 TEST PARA ACTUALIZAR PRODUCTORA
    // -------------------------------
    @Nested
    class ActualizarProductoraTests {

        @BeforeEach
        void setUp() {
            productora1 = new Productora(1, "AMC", LocalDate.of(1994, 1, 1));
        }

        @Test
        void testActualizarProductora_OK() {
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createCriteriaUpdate(Productora.class)).thenReturn(criteriaUpdate);
            when(criteriaUpdate.from(Productora.class)).thenReturn(root);

            when(criteriaUpdate.set(root.get("nombre"), productora1.getNombre())).thenReturn(criteriaUpdate);
            when(criteriaUpdate.set(root.get("anoFundacion"), productora1.getAnoFundacion())).thenReturn(
                    criteriaUpdate);

            when(criteriaBuilder.equal(root.get("idProductora"), productora1.getIdProductora())).thenReturn(predicate);
            when(criteriaUpdate.where(predicate)).thenReturn(criteriaUpdate);

            when(entityManager.createQuery(criteriaUpdate)).thenReturn(typedQuery);
            when(typedQuery.executeUpdate()).thenReturn(1);

            productoraCriteriaRepository.actualizarProductora(1, productora1);

            assertNotNull(productora1);
            assertEquals("AMC", productora1.getNombre());
        }

        @Test
        void testActualizarProductora_KO() {
            when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
            when(criteriaBuilder.createCriteriaUpdate(Productora.class)).thenReturn(criteriaUpdate);
            when(criteriaUpdate.from(Productora.class)).thenReturn(root);

            productora1 = new Productora(1, "", LocalDate.of(1994, 1, 1));

            when(criteriaUpdate.set(root.get("nombre"), productora1.getNombre())).thenThrow(
                    new IllegalArgumentException("No se puede poner un campo vacio"));

            assertThrows(IllegalArgumentException.class,
                    () -> productoraCriteriaRepository.actualizarProductora(1, productora1));
        }
    }
}
