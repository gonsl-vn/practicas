package viewnext.practica4.serviceImp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import viewnext.practica4.entities.Actores;
import viewnext.practica4.entities.Directores;
import viewnext.practica4.entities.Productoras;
import viewnext.practica4.entities.Series;
import viewnext.practica4.entitiesDTOs.SeriesDto;
import viewnext.practica4.repositories.ActoresRepository;
import viewnext.practica4.repositories.DirectoresRepository;
import viewnext.practica4.repositories.ProductorasRepository;
import viewnext.practica4.repositories.SeriesRepository;
import viewnext.practica4.servicesImp.SeriesServiceImp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * The type Series service imp test.
 */
public class SeriesServiceImpTest {

    @Mock
    private SeriesRepository seriesRepository;

    @Mock
    private DirectoresRepository directoresRepository;

    @Mock
    private ProductorasRepository productorasRepository;

    @Mock
    private ActoresRepository actoresRepository;

    @InjectMocks
    private SeriesServiceImp seriesServiceImp;

    private SeriesDto seriesDto;
    private Series series;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        series = new Series();
        series.setIdSeries(1L);
        series.setTitulo("LQSA");
        series.setAnio(2005);
        series.setDirectores(null);
        series.setProductoras(null);
        series.setActores(new HashSet<>());

        seriesDto = new SeriesDto();
        seriesDto.setTitulo("LQSA");
        seriesDto.setAnio(2005);
    }

    /**
     * Series listar series test.
     */
    @Test
    public void series_listarSeriesTest() {
        when(seriesRepository.findAllByOrderByTituloAsc()).thenReturn(Arrays.asList(series));

        List<SeriesDto> seriesList = seriesServiceImp.listarSeries();

        assertNotNull(seriesList);
        assertEquals(1, seriesList.size());
        assertEquals("LQSA", seriesList.get(0).getTitulo());
        assertEquals(2005, seriesList.get(0).getAnio());
    }

    /**
     * Series anadir series test.
     */
    @Test
    public void series_anadirSeriesTest() {
        Directores director = new Directores();
        director.setNombre("Quentin");

        Productoras productora = new Productoras();
        productora.setNombre("Miramax");

        Actores actor = new Actores();
        actor.setNombre("Brad");

        when(directoresRepository.findByNombre("Quentin")).thenReturn(Optional.of(director));
        when(productorasRepository.findByNombre("Miramax")).thenReturn(Optional.of(productora));
        when(actoresRepository.findByNombre("Brad")).thenReturn(Optional.of(actor));

        seriesDto.setTitulo("LQSA");
        seriesDto.setAnio(2005);
        seriesDto.setNombreDirector("Quentin");
        seriesDto.setNombreProductora("Miramax");
        seriesDto.setNombresActores(new HashSet<>(Arrays.asList("Brad")));

        Series serieGuardada = new Series();
        serieGuardada.setIdSeries(1L);
        serieGuardada.setTitulo(seriesDto.getTitulo());
        serieGuardada.setAnio(seriesDto.getAnio());
        serieGuardada.setDirectores(director);
        serieGuardada.setProductoras(productora);
        serieGuardada.setActores(new HashSet<>(Arrays.asList(actor)));

        when(seriesRepository.save(any(Series.class))).thenReturn(serieGuardada);

        Series serieNueva = seriesServiceImp.anadirSeries(seriesDto);

        assertNotNull(serieNueva, "La serie no debe ser nula");
        assertEquals(1L, serieNueva.getIdSeries());
        assertEquals("LQSA", serieNueva.getTitulo());
        assertEquals(2005, serieNueva.getAnio());
        assertEquals("Quentin", serieNueva.getDirectores().getNombre());
        assertEquals("Miramax", serieNueva.getProductoras().getNombre());
        assertTrue(serieNueva.getActores().stream().anyMatch(a -> a.getNombre().equals("Brad")));
    }

    /**
     * Series modificar series test.
     */
    @Test
    public void series_modificarSeriesTest() {
        Series serieOriginal = new Series();
        serieOriginal.setIdSeries(1L);
        serieOriginal.setTitulo("LQSA");
        serieOriginal.setAnio(2005);
        serieOriginal.setActores(new HashSet<>());
        serieOriginal.setDirectores(new Directores());
        serieOriginal.setProductoras(new Productoras());

        SeriesDto dtoModificado = new SeriesDto();
        dtoModificado.setIdSeries(1L);
        dtoModificado.setTitulo("LQSAA");
        dtoModificado.setAnio(2006);
        dtoModificado.setNombreDirector("Tarantino");
        dtoModificado.setNombreProductora("Universal");
        dtoModificado.setNombresActores(new HashSet<>(List.of("Brad", "Angelina")));

        Directores director = new Directores();
        director.setNombre("Tarantino");

        Productoras productora = new Productoras();
        productora.setNombre("Universal");

        Actores actor1 = new Actores();
        actor1.setNombre("Brad");

        Actores actor2 = new Actores();
        actor2.setNombre("Angelina");

        when(seriesRepository.findById(1L)).thenReturn(Optional.of(serieOriginal));
        when(directoresRepository.findByNombre("Tarantino")).thenReturn(Optional.of(director));
        when(productorasRepository.findByNombre("Universal")).thenReturn(Optional.of(productora));
        when(actoresRepository.findByNombre("Brad")).thenReturn(Optional.of(actor1));
        when(actoresRepository.findByNombre("Angelina")).thenReturn(Optional.of(actor2));
        when(seriesRepository.save(any(Series.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Series resultado = seriesServiceImp.modificarSeries(dtoModificado);

        assertNotNull(resultado);
        assertEquals("LQSAA", resultado.getTitulo());
        assertEquals(2006, resultado.getAnio());
        assertEquals("Tarantino", resultado.getDirectores().getNombre());
        assertEquals("Universal", resultado.getProductoras().getNombre());
        assertEquals(2, resultado.getActores().size());
    }

    /**
     * Series eliminar series test.
     */
    @Test
    public void series_eliminarSeriesTest() {
        doNothing().when(seriesRepository).deleteById(1L);
        assertDoesNotThrow(() -> seriesServiceImp.borrarSeries(1L));
    }
}
