package viewnext.practica4.serviceImp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import viewnext.practica4.entities.Actores;
import viewnext.practica4.entitiesDTOs.ActoresDto;
import viewnext.practica4.repositories.ActoresRepository;
import viewnext.practica4.servicesImp.ActoresServiceImp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * The type Actores service imp test.
 */
public class ActoresServiceImpTest {

    @Mock
    private ActoresRepository actoresRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Actores> typedQuery;

    @InjectMocks
    private ActoresServiceImp actoresServiceImp;

    private Actores actores;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        actores = new Actores();
        actores.setIdActor("00000000H");
        actores.setNombre("Marcos");
        actores.setApellido("González");
        actores.setEdad(19);
        actores.setNacionalidad("Española");
        actores.setPeliculas(Collections.emptySet());
        actores.setSeries(Collections.emptySet());
    }

    /**
     * Actor listar actores test.
     */
    @Test
    void actor_listarActoresTest() {
        when(actoresRepository.findAllByOrderByNombreAsc()).thenReturn(Arrays.asList(actores));

        List<ActoresDto> actoresList = actoresServiceImp.listarActores();

        assertNotNull(actoresList);
        assertEquals(1, actoresList.size());
        assertEquals("Marcos", actoresList.get(0).getNombre());
    }

    /**
     * Actor guardar actores test.
     */
    @Test
    void actor_guardarActoresTest() {
        when(actoresRepository.save(actores)).thenReturn(actores);

        Actores actorNuevo = actoresServiceImp.guardarActores(actores);

        assertNotNull(actorNuevo);
        assertEquals("00000000H", actorNuevo.getIdActor());
        assertEquals("Marcos", actorNuevo.getNombre());
    }

    /**
     * Actor modificar actores test.
     */
    @Test
    void actor_modificarActoresTest() {
        Actores actorEnBD = new Actores();
        actorEnBD.setIdActor("00000000H");
        actorEnBD.setNombre("Marcos");
        actorEnBD.setApellido("González");
        actorEnBD.setEdad(19);
        actorEnBD.setNacionalidad("Española");
        actorEnBD.setPeliculas(Collections.emptySet());
        actorEnBD.setSeries(Collections.emptySet());

        Actores actorModificado = new Actores();
        actorModificado.setIdActor("00000000H");
        actorModificado.setNombre("Marcos");
        actorModificado.setApellido("González");
        actorModificado.setEdad(19);
        actorModificado.setNacionalidad("Brasileña");
        actorModificado.setPeliculas(Collections.emptySet());
        actorModificado.setSeries(Collections.emptySet());

        when(actoresRepository.findById("00000000H")).thenReturn(Optional.of(actorEnBD));
        when(actoresRepository.save(actorEnBD)).thenReturn(actorEnBD);

        Actores resultado = actoresServiceImp.modificarActores(actorModificado);

        assertNotNull(resultado);
        assertEquals("Brasileña", resultado.getNacionalidad());
    }

    /**
     * Actor borrar actores test.
     */
    @Test
    void actor_borrarActoresTest() {
        doNothing().when(actoresRepository).deleteById("00000000H");

        assertDoesNotThrow(() -> actoresServiceImp.borrarActores("00000000H"));
    }

    /**
     * Actor buscar actores con filtros test.
     */
    @Test
    void actor_buscarActoresConFiltrosTest() {
        String nombre = "Marcos";
        String nacionalidad = "Española";
        Integer edadMin = 18;
        Integer edadMax = 30;

        when(actoresRepository.buscarActoresConFiltros(nombre, nacionalidad, edadMin, edadMax)).thenReturn(
                Arrays.asList(actores));

        List<ActoresDto> actoresList = actoresServiceImp.buscarActoresConFiltros(nombre, nacionalidad, edadMin,
                edadMax);

        assertNotNull(actoresList);
        assertEquals(1, actoresList.size());
        assertEquals("Marcos", actoresList.get(0).getNombre());
    }
}
