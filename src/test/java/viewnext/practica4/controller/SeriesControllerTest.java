package viewnext.practica4.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import viewnext.practica4.entities.Series;
import viewnext.practica4.entitiesDTOs.SeriesDto;
import viewnext.practica4.servicesImp.SeriesServiceImp;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * The type Series controller test.
 */
public class SeriesControllerTest {

    @Mock
    private SeriesServiceImp seriesService;

    @InjectMocks
    private SeriesController seriesController;

    private SeriesDto seriesDto;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        seriesDto = new SeriesDto();
        seriesDto.setIdSeries(1L);
        seriesDto.setTitulo("LQSA");
        seriesDto.setAnio(2005);
    }

    /**
     * Listar series test.
     */
    @Test
    void listarSeriesTest() {
        when(seriesService.listarSeries()).thenReturn(Arrays.asList(seriesDto));

        ResponseEntity<List<SeriesDto>> response = seriesController.listarSeries();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("LQSA", response.getBody().get(0).getTitulo());
    }

    /**
     * Crear serie test.
     */
    @Test
    void crearSerieTest() {
        when(seriesService.anadirSeries(seriesDto)).thenReturn(new Series());
        when(seriesService.mapToDto(new Series())).thenReturn(seriesDto);

        ResponseEntity<SeriesDto> response = seriesController.crearSerie(seriesDto);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("LQSA", response.getBody().getTitulo());
    }

    /**
     * Modificar serie test.
     */
    @Test
    void modificarSerieTest() {
        when(seriesService.modificarSeries(seriesDto)).thenReturn(new Series());
        when(seriesService.mapToDto(new Series())).thenReturn(seriesDto);

        ResponseEntity<SeriesDto> response = seriesController.modificarSerie(seriesDto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2005, response.getBody().getAnio());
    }

    /**
     * Borrar serie test.
     */
    @Test
    void borrarSerieTest() {
        doNothing().when(seriesService).borrarSeries(1L);

        ResponseEntity<Void> response = seriesController.borrarSerie(1L);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    /**
     * Buscar series con filtros test.
     */
    @Test
    void buscarSeriesConFiltrosTest() {
        when(seriesService.buscarSeriesConFiltros("LQSA", 2000, 2020, null, null)).thenReturn(Arrays.asList(seriesDto));

        ResponseEntity<List<SeriesDto>> response = seriesController.buscarConFiltros("LQSA", 2000, 2020, null, null);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("LQSA", response.getBody().get(0).getTitulo());
    }
}
