package viewnext.practica4.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import viewnext.practica4.entities.Directores;
import viewnext.practica4.entitiesDTOs.DirectoresDto;
import viewnext.practica4.servicesImp.DirectoresServiceImp;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * The type Directores controller test.
 */
public class DirectoresControllerTest {

    @Mock
    private DirectoresServiceImp directoresService;

    @InjectMocks
    private DirectoresController directoresController;

    private Directores director;
    private DirectoresDto directorDto;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        director = new Directores();
        director.setIdDirector("1");
        director.setNombre("Pedro");
        director.setApellido("Almodóvar");
        director.setEdad(70);
        director.setNacionalidad("Española");

        directorDto = new DirectoresDto();
        directorDto.setIdDirector("1");
        directorDto.setNombre("Pedro");
        directorDto.setApellido("Almodóvar");
        directorDto.setEdad(70);
        directorDto.setNacionalidad("Española");
    }

    /**
     * Listar directores test.
     */
    @Test
    void listarDirectoresTest() {
        when(directoresService.listarDirectores()).thenReturn(Arrays.asList(directorDto));

        ResponseEntity<List<DirectoresDto>> response = directoresController.listarDirectores();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Pedro", response.getBody().get(0).getNombre());
    }

    /**
     * Anadir director test.
     */
    @Test
    void anadirDirectorTest() {
        when(directoresService.anadirDirectores(director)).thenReturn(director);
        when(directoresService.mapToDto(director)).thenReturn(directorDto);

        ResponseEntity<DirectoresDto> response = directoresController.anadirDirectores(director);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Pedro", response.getBody().getNombre());
    }

    /**
     * Modificar director test.
     */
    @Test
    void modificarDirectorTest() {
        when(directoresService.modificarDirectores(director)).thenReturn(director);
        when(directoresService.mapToDto(director)).thenReturn(directorDto);

        ResponseEntity<DirectoresDto> response = directoresController.modificarDirectores(director);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(70, response.getBody().getEdad());
    }

    /**
     * Eliminar director test.
     */
    @Test
    void eliminarDirectorTest() {
        doNothing().when(directoresService).eliminarDirectores("1");

        ResponseEntity<Void> response = directoresController.eliminarDirectores("1");

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    /**
     * Buscar directores con filtros test.
     */
    @Test
    void buscarDirectoresConFiltrosTest() {
        when(directoresService.buscarDirectoresConFiltros("Pedro", "Española", 60, 80)).thenReturn(
                Arrays.asList(directorDto));

        ResponseEntity<List<DirectoresDto>> response = directoresController.buscarDirectoresConFiltros("Pedro",
                "Española", 60, 80);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Pedro", response.getBody().get(0).getNombre());
    }
}
