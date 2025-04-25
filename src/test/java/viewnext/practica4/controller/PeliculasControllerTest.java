package viewnext.practica4.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import viewnext.practica4.entities.Peliculas;
import viewnext.practica4.entitiesDTOs.PeliculasDto;
import viewnext.practica4.servicesImp.PeliculasServiceImp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * The type Peliculas controller test.
 */
public class PeliculasControllerTest {

    @Mock
    private PeliculasServiceImp peliculasService;

    @InjectMocks
    private PeliculasController peliculasController;

    private Peliculas pelicula;
    private PeliculasDto peliculaDto;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pelicula = new Peliculas();
        pelicula.setIdPelicula(1L);
        pelicula.setTitulo("Origen");
        pelicula.setAnio(2010);
        pelicula.setActores(new HashSet<>());
        pelicula.setDirectores(null);
        pelicula.setProductoras(null);

        peliculaDto = new PeliculasDto();
        peliculaDto.setIdPelicula(1L);
        peliculaDto.setTitulo("Origen");
        peliculaDto.setAnio(2010);
    }

    /**
     * Listar peliculas test.
     */
    @Test
    void listarPeliculasTest() {
        when(peliculasService.listarPeliculas()).thenReturn(Arrays.asList(peliculaDto));

        ResponseEntity<List<PeliculasDto>> response = peliculasController.listarPeliculas();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Origen", response.getBody().get(0).getTitulo());
    }

    /**
     * Anadir pelicula test.
     */
    @Test
    void anadirPeliculaTest() {
        when(peliculasService.anadirPelicula(pelicula)).thenReturn(pelicula);
        when(peliculasService.mapToDto(pelicula)).thenReturn(peliculaDto);

        ResponseEntity<PeliculasDto> response = peliculasController.anadirPelicula(pelicula);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Origen", response.getBody().getTitulo());
    }

    /**
     * Modificar pelicula test.
     */
    @Test
    void modificarPeliculaTest() {
        when(peliculasService.modificarPeliculas(pelicula)).thenReturn(pelicula);
        when(peliculasService.mapToDto(pelicula)).thenReturn(peliculaDto);

        ResponseEntity<PeliculasDto> response = peliculasController.modificarPeliculas(pelicula);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2010, response.getBody().getAnio());
    }

    /**
     * Borrar pelicula test.
     */
    @Test
    void borrarPeliculaTest() {
        doNothing().when(peliculasService).borrarPeliculas(1L);

        ResponseEntity<Void> response = peliculasController.borrarPeliculas(1L);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    /**
     * Buscar peliculas con filtros test.
     */
    @Test
    void buscarPeliculasConFiltrosTest() {
        when(peliculasService.buscarPeliculasConFiltros("Origen", 2000, 2020, null, null)).thenReturn(
                Arrays.asList(peliculaDto));

        ResponseEntity<List<PeliculasDto>> response = peliculasController.buscarPeliculasConFiltros("Origen", 2000,
                2020, null, null);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Origen", response.getBody().get(0).getTitulo());
    }
}
