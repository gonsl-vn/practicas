package com.viewnext.practica4.service;

import com.viewnext.practica4.models.Productora;
import com.viewnext.practica4.repositorys.ProductoraCriteriaRepository;
import com.viewnext.practica4.repositorys.ProductoraRepository;
import com.viewnext.practica4.services.ProductoraService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoraServiceTest {

    @Mock
    private ProductoraRepository productoraRepository;

    @Mock
    private ProductoraCriteriaRepository productoraCriteriaRepository;

    @InjectMocks
    private ProductoraService productoraService;

    private Productora productora1;
    private Productora productora2;
    private Productora productora3;

    @BeforeEach
    void setUp() {
        // Crear instancias de productoras de prueba
        productora1 = new Productora(1, "Warner Bros", LocalDate.of(1923, 4, 4));
        productora2 = new Productora(2, "Universal Pictures", LocalDate.of(1912, 6, 8));
        productora3 = new Productora(3, "Paramount Pictures", LocalDate.of(1912, 5, 8));

        // Simular respuestas del repositorio con Mockito
        when(productoraRepository.findAll()).thenReturn(Arrays.asList(productora1, productora2, productora3));
        when(productoraRepository.findByIdProductora(1)).thenReturn(Optional.of(productora1));
        when(productoraRepository.findByIdProductora(2)).thenReturn(Optional.of(productora2));
        when(productoraRepository.findByIdProductora(3)).thenReturn(Optional.of(productora3));
        when(productoraRepository.findByNombre("Warner Bros")).thenReturn(Optional.of(productora1));
        when(productoraRepository.save(any(Productora.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @AfterEach
    void tearDown() {
        // Limpiar referencias después de cada prueba
        productora1 = null;
        productora2 = null;
        productora3 = null;

        // Resetear los mocks
        reset(productoraRepository);
    }

    // -------------------- TESTS CON JPA REPOSITORY --------------------

    @Test
    void testObtenerProductoras() {
        List<Productora> listaProductoras = productoraService.obtenerProductoras();

        assertNotNull(listaProductoras);
        assertEquals(3, listaProductoras.size());
        assertEquals("Warner Bros", listaProductoras.get(0).getNombre());
        assertEquals("Universal Pictures", listaProductoras.get(1).getNombre());
        assertEquals("Paramount Pictures", listaProductoras.get(2).getNombre());

        verify(productoraRepository, times(1)).findAll();
    }

    @Test
    void testObtenerProductoraPorId() {
        Optional<Productora> productora = productoraService.obtenerProductoraPorId(1);

        assertTrue(productora.isPresent());
        assertEquals("Warner Bros", productora.get().getNombre());
        assertEquals(1923, productora.get().getAnoFundacion().getYear());

        verify(productoraRepository, times(1)).findByIdProductora(1);
    }

    @Test
    void testObtenerProductoraPorNombre() {
        Optional<Productora> productora = productoraService.obtenerProductoraPorNombre("Warner Bros");

        assertTrue(productora.isPresent());
        assertEquals(1923, productora.get().getAnoFundacion().getYear());

        verify(productoraRepository, times(1)).findByNombre("Warner Bros");
    }

    @Test
    void testInsertarProductora() {
        Productora nuevaProductora = new Productora(4, "Netflix Studios", LocalDate.of(2010, 3, 1));
        productoraService.insertarProductora(nuevaProductora);

        verify(productoraRepository, times(1)).save(nuevaProductora);
    }

    @Test
    void testActualizarProductora() {
        Productora productoraActualizada = new Productora(1, "Warner Bros Updated", LocalDate.of(1923, 4, 4));

        when(productoraRepository.findByIdProductora(1)).thenReturn(Optional.of(productora1));
        when(productoraRepository.save(any(Productora.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Productora resultado = productoraService.actualizarProductora(1, productoraActualizada);

        assertNotNull(resultado);
        assertEquals("Warner Bros Updated", resultado.getNombre());

        verify(productoraRepository, times(1)).findByIdProductora(1);
        verify(productoraRepository, times(1)).save(any(Productora.class));
    }

    @Test
    void testEliminarProductora() {
        when(productoraRepository.findByIdProductora(1)).thenReturn(Optional.of(productora1));

        productoraService.eliminarProductora(1);

        verify(productoraRepository, times(1)).delete(productora1);
    }

    // -------------------- TESTS CON CRITERIA API --------------------

    @Test
    void testObtenerProductorasCriteria() {
        when(productoraCriteriaRepository.listarProductoras()).thenReturn(
                Arrays.asList(productora1, productora2, productora3));

        List<Productora> listaProductoras = productoraService.obtenerProductorasCriteria();

        assertNotNull(listaProductoras);
        assertEquals(3, listaProductoras.size());
        verify(productoraCriteriaRepository, times(1)).listarProductoras();
    }

    @Test
    void testObtenerProductoraPorIdCriteria() {
        when(productoraCriteriaRepository.buscarProductora(1)).thenReturn(productora1);

        Productora productora = productoraService.obtenerProductoraPorIdCriteria(1);

        assertNotNull(productora);
        assertEquals("Warner Bros", productora.getNombre());
        verify(productoraCriteriaRepository, times(1)).buscarProductora(1);
    }

    @Test
    void testInsertarProductoraCriteria() {
        Productora nuevaProductora = new Productora(4, "Netflix Studios", LocalDate.of(2010, 3, 1));

        productoraService.insertarProductoraCriteria(nuevaProductora);

        verify(productoraCriteriaRepository, times(1)).insertarProductora(nuevaProductora);
    }

    @Test
    void testActualizarProductoraCriteria() {
        Productora productoraActualizada = new Productora(1, "Warner Bros Updated", LocalDate.of(1923, 4, 4));

        productoraService.actualizarProductoraCriteria(1, productoraActualizada);

        verify(productoraCriteriaRepository, times(1)).actualizarProductora(1, productoraActualizada);
    }

    @Test
    void testEliminarProductoraCriteria() {
        productoraService.eliminarProductoraCriteria(1);

        verify(productoraCriteriaRepository, times(1)).borrarProductoraPorId(1);
    }
}
