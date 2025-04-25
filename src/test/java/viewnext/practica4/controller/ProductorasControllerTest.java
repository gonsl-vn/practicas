package viewnext.practica4.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import viewnext.practica4.entities.Productoras;
import viewnext.practica4.entitiesDTOs.ProductorasDto;
import viewnext.practica4.servicesImp.ProductorasServiceImp;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * The type Productoras controller test.
 */
public class ProductorasControllerTest {

    @Mock
    private ProductorasServiceImp productorasService;

    @InjectMocks
    private ProductorasController productorasController;

    private Productoras productora;
    private ProductorasDto productoraDto;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productora = new Productoras();
        productora.setIdProductora(1L);
        productora.setNombre("Miramax");
        productora.setAnioFundacion(1980);

        productoraDto = new ProductorasDto();
        productoraDto.setIdProductora(1L);
        productoraDto.setNombre("Miramax");
        productoraDto.setAnioFundacion(1980);
    }

    /**
     * Listar productoras test.
     */
    @Test
    void listarProductorasTest() {
        when(productorasService.listarProductoras()).thenReturn(Arrays.asList(productoraDto));

        ResponseEntity<List<ProductorasDto>> response = productorasController.listarProductoras();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Miramax", response.getBody().get(0).getNombre());
    }

    /**
     * Guardar productora test.
     */
    @Test
    void guardarProductoraTest() {
        when(productorasService.anadirProductoras(productora)).thenReturn(productora);
        when(productorasService.mapToDto(productora)).thenReturn(productoraDto);

        ResponseEntity<ProductorasDto> response = productorasController.guardarProductora(productora);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Miramax", response.getBody().getNombre());
    }

    /**
     * Modificar productora test.
     */
    @Test
    void modificarProductoraTest() {
        when(productorasService.modificarProductoras(productora)).thenReturn(productora);
        when(productorasService.mapToDto(productora)).thenReturn(productoraDto);

        ResponseEntity<ProductorasDto> response = productorasController.modificarProductora(productora);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1980, response.getBody().getAnioFundacion());
    }

    /**
     * Borrar productora test.
     */
    @Test
    void borrarProductoraTest() {
        doNothing().when(productorasService).borrarProductoras(1L);

        ResponseEntity<Void> response = productorasController.borrarProductora(1L);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    /**
     * Buscar productoras con filtros test.
     */
    @Test
    void buscarProductorasConFiltrosTest() {
        when(productorasService.buscarProductorasConFiltros("Miramax", 1970, 1990)).thenReturn(
                Arrays.asList(productoraDto));

        ResponseEntity<List<ProductorasDto>> response = productorasController.buscarConFiltros("Miramax", 1970, 1990);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Miramax", response.getBody().get(0).getNombre());
    }
}
