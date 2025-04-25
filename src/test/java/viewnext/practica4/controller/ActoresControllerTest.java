package viewnext.practica4.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import viewnext.practica4.entities.Actores;
import viewnext.practica4.entitiesDTOs.ActoresDto;
import viewnext.practica4.servicesImp.ActoresServiceImp;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * The type Actores controller test.
 */
public class ActoresControllerTest {

    @Mock
    private ActoresServiceImp actoresService;

    @InjectMocks
    private ActoresController actoresController;

    private Actores actor;
    private ActoresDto actorDto;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        actor = new Actores();
        actor.setIdActor("1");
        actor.setNombre("Brad Pitt");
        actor.setNacionalidad("USA");
        actor.setEdad(57);

        actorDto = new ActoresDto();
        actorDto.setIdActor("1");
        actorDto.setNombre("Brad Pitt");
        actorDto.setNacionalidad("USA");
        actorDto.setEdad(57);
    }

    /**
     * Listar actores test.
     */
    @Test
    void listarActoresTest() {
        when(actoresService.listarActores()).thenReturn(Arrays.asList(actorDto));

        ResponseEntity<List<ActoresDto>> response = actoresController.listarActores();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Brad Pitt", response.getBody().get(0).getNombre());
    }

    /**
     * Guardar actor test.
     */
    @Test
    void guardarActorTest() {
        when(actoresService.guardarActores(actor)).thenReturn(actor);
        when(actoresService.mapToDto(actor)).thenReturn(actorDto);

        ResponseEntity<ActoresDto> response = actoresController.guardarActor(actor);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Brad Pitt", response.getBody().getNombre());
    }

    /**
     * Modificar actor test.
     */
    @Test
    void modificarActorTest() {
        when(actoresService.modificarActores(actor)).thenReturn(actor);
        when(actoresService.mapToDto(actor)).thenReturn(actorDto);

        ResponseEntity<ActoresDto> response = actoresController.modificarActor(actor);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(57, response.getBody().getEdad());
    }

    /**
     * Borrar actor test.
     */
    @Test
    void borrarActorTest() {
        doNothing().when(actoresService).borrarActores("1");

        ResponseEntity<Void> response = actoresController.borrarActor("1");

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    /**
     * Buscar actores con filtros test.
     */
    @Test
    void buscarActoresConFiltrosTest() {
        when(actoresService.buscarActoresConFiltros("Brad Pitt", "USA", 50, 60)).thenReturn(Arrays.asList(actorDto));

        ResponseEntity<List<ActoresDto>> response = actoresController.buscarActoresConFiltros("Brad Pitt", "USA", 50,
                60);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Brad Pitt", response.getBody().get(0).getNombre());
    }
}
