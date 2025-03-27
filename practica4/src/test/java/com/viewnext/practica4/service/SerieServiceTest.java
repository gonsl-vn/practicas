package com.viewnext.practica4.service;

import com.viewnext.practica4.models.Director;
import com.viewnext.practica4.models.Productora;
import com.viewnext.practica4.models.Serie;
import com.viewnext.practica4.repositorys.SerieCriteriaRepository;
import com.viewnext.practica4.repositorys.SerieRepository;
import com.viewnext.practica4.services.SerieService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SerieServiceTest {

    @Mock
    private SerieRepository serieRepository;

    @Mock
    private SerieCriteriaRepository serieCriteriaRepository;

    @InjectMocks
    private SerieService serieService;

    private Serie serie1;
    private Serie serie2;
    private Serie serie3;
    private Serie serie;

    @BeforeEach
    void setUp() {
        // Crear instancias de director, productora y actores para las series
        Director director = new Director(1, "12345678A", "Vince", "Gilligan", 57, "EE.UU");
        Productora productora = new Productora(1, "AMC", LocalDate.of(1994, 1, 1));

        // Crear instancias de series de prueba
        serie1 = new Serie(101, "Breaking Bad", LocalDate.of(2008, 1, 20), director, productora, List.of());
        serie2 = new Serie(102, "Better Call Saul", LocalDate.of(2015, 2, 8), director, productora, List.of());
        serie3 = new Serie(103, "The Walking Dead", LocalDate.of(2010, 10, 31), director, productora, List.of());

        serie = new Serie();
        serie.setIdSerie(10);
        serie.setTitulo("The Walking Dead");
        serie.setAno(LocalDate.of(2010, 10, 31));
        // Simular respuestas del repositorio con Mockito
        when(serieRepository.findAll()).thenReturn(Arrays.asList(serie1, serie2, serie3));
        when(serieRepository.findById(101)).thenReturn(Optional.of(serie1));
        when(serieRepository.findById(102)).thenReturn(Optional.of(serie2));
        when(serieRepository.findById(103)).thenReturn(Optional.of(serie3));
        when(serieRepository.findByTitulo("Breaking Bad")).thenReturn(Optional.of(serie1));
        when(serieRepository.save(any(Serie.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @AfterEach
    void tearDown() {
        // Limpiar referencias después de cada prueba
        serie1 = null;
        serie2 = null;
        serie3 = null;

        // Resetear los mocks
        reset(serieRepository);
    }

    // -------------------- TESTS CON JPA REPOSITORY --------------------

    @Test
    void testObtenerSeries() {
        List<Serie> listaSeries = serieService.obtenerSeries();

        assertNotNull(listaSeries);
        assertEquals(3, listaSeries.size());

        verify(serieRepository, times(1)).findAll();
    }

    @Test
    void testObtenerSeriesKO() {
        when(serieRepository.findAll()).thenReturn(null);

        List<Serie> listaSeries = serieService.obtenerSeries();

        assertNull(listaSeries);
        verify(serieRepository, times(1)).findAll();
    }

    // --------------------------------------------------

    @Test
    void testObtenerSeriePorId() {
        Optional<Serie> serie = serieService.obtenerSeriePorId(101);

        assertTrue(serie.isPresent());
        assertEquals("Breaking Bad", serie.get().getTitulo());

        verify(serieRepository, times(1)).findById(101);
    }

    @Test
    void testObtenerSeriePorIdKO() {
        when(serieRepository.findById(101)).thenReturn(Optional.empty());

        Optional<Serie> serie = serieService.obtenerSeriePorId(101);

        assertFalse(serie.isPresent());
        verify(serieRepository, times(1)).findById(101);
    }

    // --------------------------------------------------

    @Test
    void testInsertarSerie() {
        Serie nuevaSerie = new Serie(104, "Game of Thrones", LocalDate.of(2011, 4, 17), null, null, List.of());

        serieService.insertarSerie(nuevaSerie);

        verify(serieRepository, times(1)).save(nuevaSerie);
    }

    @Test
    void testInsertarSerieKO() {
        Serie nuevaSerie = new Serie(104, "", LocalDate.of(2011, 4, 17), null, null, List.of());

        when(serieRepository.save(nuevaSerie)).thenThrow(new IllegalArgumentException("No se puede guardar la serie"));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> serieService.insertarSerie(nuevaSerie));

        assertEquals("No se puede guardar la serie", exception.getMessage());
    }

    // --------------------------------------------------

    @Test
    void testActualizarSerie() {
        Serie serieActualizada = new Serie(101, "Breaking Bad - Extended", LocalDate.of(2008, 1, 20), null, null,
                List.of());

        when(serieRepository.findById(101)).thenReturn(Optional.of(serie1));
        when(serieRepository.save(any(Serie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Serie resultado = serieService.actualizarSerie(101, serieActualizada);

        assertNotNull(resultado);
        assertEquals("Breaking Bad - Extended", resultado.getTitulo());

        verify(serieRepository, times(1)).findById(101);
        verify(serieRepository, times(1)).save(any(Serie.class));
    }

    @Test
    void testActualizarSerieKO() {
        Serie serieActualizada = new Serie(101, "", LocalDate.of(2008, 1, 20), null, null, List.of());

        when(serieRepository.findById(101)).thenReturn(Optional.ofNullable(serie1));
        when(serieRepository.save(any(Serie.class))).thenThrow(new IllegalArgumentException("Datos inválidos"));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> serieService.actualizarSerie(101, serieActualizada));

        assertEquals("Datos inválidos", exception.getMessage());
    }

    // --------------------------------------------------

    @Test
    void testEliminarSerie() {
        when(serieRepository.findById(101)).thenReturn(Optional.of(serie1));

        serieService.eliminarSerie(101);

        verify(serieRepository, times(1)).delete(serie1);
    }

    @Test
    void testEliminarSerieKO() {
        when(serieRepository.findById(101)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> serieService.eliminarSerie(101));

        verify(serieRepository, times(0)).delete(any());
    }

    @Test
    void testObtenerSeriesPaginacionyOrdenados_OK() {

        // Configurar el pageable
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nombre").ascending());

        // Configurar el resultado esperado
        Page<Serie> resultadoEsperado = new PageImpl<>(Arrays.asList(serie1, serie2, serie3), pageable, 3);

        // Configurar el mock
        when(serieRepository.findAll(pageable)).thenReturn(resultadoEsperado);

        // Llamar al método a probar
        Page<Serie> resultado = serieService.obtenerSerie(pageable);

        // Verificación del resultado
        assertNotNull(resultado);
        assertEquals(10, resultado.getSize());
        verify(serieRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testObtenerSeriesPaginacionyOrdenados_KO() {

        // Simular la respuesta del repositorio
        when(serieRepository.findAll(any(Pageable.class))).thenReturn(null);

        // Llamar al método a probar
        Page<Serie> resultado = serieService.obtenerSerie(PageRequest.of(0, 10, Sort.by("nombre")));

        // Verificaciones
        assertNull(resultado);
        verify(serieRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testFiltrarSeries_OK() {
        // 1) Simulamos que el repositorio devuelve una lista con una Serie
        List<Serie> mockResult = Arrays.asList(serie, serie1, serie2, serie3);
        when(serieCriteriaRepository.filtrarSeries("Walking", "Kirkman", "AMC", "2010")).thenReturn(mockResult);

        // 2) Llamamos al servicio
        List<Serie> result = serieService.filtrarSeries("Walking", "Kirkman", "AMC", "2010");

        // 3) Verificamos el resultado
        assertNotNull(result, "La lista no debe ser nula");
        assertFalse(result.isEmpty(), "La lista no debe estar vacía");
        assertEquals(4, result.size(), "Debe devolver exactamente un resultado");
        assertEquals(serie, result.get(0), "La serie devuelta debe coincidir con la de prueba");

        // 4) Verificamos que el repositorio se llamó exactamente 1 vez con esos parámetros
        verify(serieCriteriaRepository, times(1)).filtrarSeries("Walking", "Kirkman", "AMC", "2010");
    }

    @Test
    void testFiltrarSeries_KO() {
        // 1) Simulamos que el repositorio devuelve una lista vacía
        when(serieCriteriaRepository.filtrarSeries(anyString(), anyString(), anyString(), anyString())).thenReturn(
                Collections.emptyList());

        // 2) Llamamos al servicio con cualquier valor
        List<Serie> result = serieService.filtrarSeries("foo", "bar", "baz", "2020");

        // 3) Verificamos que está vacía
        assertNotNull(result, "La lista debe existir aunque esté vacía");
        assertTrue(result.isEmpty(), "La lista debe estar vacía");

        // 4) Verificamos la interacción con el repositorio
        verify(serieCriteriaRepository).filtrarSeries("foo", "bar", "baz", "2020");
    }

    @Test
    void testFiltrarSeries_KO_Exception() {
        // 1) Simulamos que el repositorio lanza una excepción
        doThrow(new RuntimeException("Error interno en Criteria")).when(serieCriteriaRepository)
                .filtrarSeries("Walking", "Kirkman", "AMC", "2010");

        // 2) Verificamos que el servicio propaga la excepción
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> serieService.filtrarSeries("Walking", "Kirkman", "AMC", "2010"));

        // 3) Comprobamos el mensaje de la excepción
        assertEquals("Error interno en Criteria", ex.getMessage());

        // 4) Verificamos la interacción
        verify(serieCriteriaRepository).filtrarSeries("Walking", "Kirkman", "AMC", "2010");
    }

    // -------------------- TESTS CON CRITERIA API --------------------

    @Test
    void testObtenerSeriesCriteria() {
        when(serieCriteriaRepository.listarSeries()).thenReturn(Arrays.asList(serie1, serie2, serie3));

        List<Serie> listaSeries = serieService.obtenerSeriesCriteria();

        assertNotNull(listaSeries);
        assertEquals(3, listaSeries.size());
        verify(serieCriteriaRepository, times(1)).listarSeries();
    }

    @Test
    void testObtenerSeriesCriteriaKO() {
        when(serieCriteriaRepository.listarSeries()).thenReturn(null);

        List<Serie> listaSeries = serieService.obtenerSeriesCriteria();

        assertNull(listaSeries);
        verify(serieCriteriaRepository, times(1)).listarSeries();
    }

    @Test
    void testObtenerSeriePorIdCriteria() {
        when(serieCriteriaRepository.buscarSerie(101)).thenReturn(serie1);

        Serie serie = serieService.obtenerSeriePorIdCriteria(101);

        assertNotNull(serie);
        assertEquals("Breaking Bad", serie.getTitulo());
        verify(serieCriteriaRepository, times(1)).buscarSerie(101);
    }

    @Test
    void testObtenerSeriePorIdCriteriaKO() {
        when(serieCriteriaRepository.buscarSerie(999)).thenReturn(null);

        Serie serie = serieService.obtenerSeriePorIdCriteria(999);

        assertNull(serie);
        verify(serieCriteriaRepository, times(1)).buscarSerie(999);
    }

    @Test
    void testInsertarSerieCriteria() {
        Serie nuevaSerie = new Serie(104, "Game of Thrones", LocalDate.of(2011, 4, 17), null, null, List.of());

        serieService.insertarSerieCriteria(nuevaSerie);

        verify(serieCriteriaRepository, times(1)).insertarSerie(nuevaSerie);
    }

    @Test
    void testInsertarSerieCriteriaKO() {
        Serie nuevaSerie = new Serie(104, "", LocalDate.of(2011, 4, 17), null, null, List.of());

        doThrow(new IllegalArgumentException("No se puede guardar la serie")).when(serieCriteriaRepository)
                .insertarSerie(nuevaSerie);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> serieService.insertarSerieCriteria(nuevaSerie));

        assertEquals("No se puede guardar la serie", exception.getMessage());
    }

    @Test
    void testActualizarSerieCriteria() {
        Serie serieActualizada = new Serie(101, "Breaking Bad - Extended", LocalDate.of(2008, 1, 20), null, null,
                List.of());

        serieService.actualizarSerieCriteria(101, serieActualizada);

        verify(serieCriteriaRepository, times(1)).actualizarSerie(101, serieActualizada);
    }

    @Test
    void testActualizarSerieCriteriaKO() {
        Serie serieActualizada = new Serie(101, "", LocalDate.of(2008, 1, 20), null, null, List.of());

        doThrow(new IllegalArgumentException("Datos inválidos")).when(serieCriteriaRepository)
                .actualizarSerie(101, serieActualizada);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> serieService.actualizarSerieCriteria(101, serieActualizada));

        assertEquals("Datos inválidos", exception.getMessage());
    }

    @Test
    void testEliminarSerieCriteria() {
        serieService.eliminarSerieCriteria(101);

        verify(serieCriteriaRepository, times(1)).borrarSeriePorId(101);
    }

    @Test
    void testEliminarSerieCriteriaKO() {
        doThrow(new RuntimeException("No se pudo eliminar la serie, no encontrada")).when(serieCriteriaRepository)
                .borrarSeriePorId(999);

        Exception exception = assertThrows(RuntimeException.class, () -> serieService.eliminarSerieCriteria(999));

        assertEquals("No se pudo eliminar la serie, no encontrada", exception.getMessage());
    }
}
