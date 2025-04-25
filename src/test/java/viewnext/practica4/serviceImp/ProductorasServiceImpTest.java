package viewnext.practica4.serviceImp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import viewnext.practica4.entities.Productoras;
import viewnext.practica4.entitiesDTOs.ProductorasDto;
import viewnext.practica4.repositories.ProductorasRepository;
import viewnext.practica4.servicesImp.ProductorasServiceImp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * The type Productoras service imp test.
 */
public class ProductorasServiceImpTest {
    @Mock
    private ProductorasRepository productorasRepository;

    @InjectMocks
    private ProductorasServiceImp productorasServiceImp;

    private Productoras productoras;
    private ProductorasDto productorasDto;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productoras = new Productoras();
        productoras.setIdProductora(1L);
        productoras.setNombre("Netflix");
        productoras.setPeliculas(Collections.emptySet());
        productoras.setSeries((Collections.emptySet()));
    }

    /**
     * Productoras listar productoras test.
     */
    @Test
    public void productoras_listarProductorasTest() {
        when(productorasRepository.findAllByOrderByNombreAsc()).thenReturn(Arrays.asList(productoras));

        List<ProductorasDto> productoras = productorasServiceImp.listarProductoras();

        assertNotNull(productoras);
        assertEquals(1, productoras.size());
    }

    /**
     * Productoras anadir productoras test.
     */
    @Test
    public void productoras_anadirProductorasTest() {
        when(productorasRepository.save(productoras)).thenReturn(productoras);

        Productoras productorasNueva = productorasServiceImp.anadirProductoras(productoras);

        assertNotNull(productorasNueva);
        assertEquals(1L, productorasNueva.getIdProductora());
    }

    /**
     * Productoras modificar productoras test.
     */
    @Test
    public void productoras_modificarProductorasTest() {
        Productoras productorasEnBD = new Productoras();
        productorasEnBD.setIdProductora(1L);
        productorasEnBD.setNombre("Netflix");
        productorasEnBD.setAnioFundacion(2001);
        productorasEnBD.setPeliculas(Collections.emptySet());
        productorasEnBD.setSeries((Collections.emptySet()));

        Productoras productorasModificado = new Productoras();
        productorasModificado.setIdProductora(1L);
        productorasModificado.setNombre("Netflix");
        productorasModificado.setAnioFundacion(2012);
        productorasModificado.setPeliculas(Collections.emptySet());
        productorasModificado.setSeries((Collections.emptySet()));

        when(productorasRepository.findById(1L)).thenReturn(Optional.of(productorasEnBD));
        when(productorasRepository.save(productorasEnBD)).thenReturn(productorasEnBD);

        Productoras resultado = productorasServiceImp.modificarProductoras(productorasModificado);

        assertNotNull(resultado);
        assertEquals(2012, resultado.getAnioFundacion());
    }

    /**
     * Productoras borrar productoras test.
     */
    @Test
    public void productoras_borrarProductorasTest() {
        doNothing().when(productorasRepository).deleteById(1L);

        assertDoesNotThrow(() -> productorasServiceImp.borrarProductoras(1L));
    }

    /**
     * Productoras buscar productoras con filtros test.
     */
    @Test
    public void productoras_buscarProductorasConFiltrosTest() {
        String nombre = "Netflix";
        Integer anioMin = 1990;
        Integer anioMax = 2025;

        when(productorasRepository.buscarProductorasConFiltros(nombre, anioMin, anioMax)).thenReturn(
                Arrays.asList(productoras));

        List<ProductorasDto> resultado = productorasServiceImp.buscarProductorasConFiltros(nombre, anioMin, anioMax);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Netflix", resultado.get(0).getNombre());
        assertEquals(0, resultado.get(0).getPeliculasTitulos().size());
        assertEquals(0, resultado.get(0).getSeriesTitulos().size());
    }
}
