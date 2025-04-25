package viewnext.practica4.serviceImp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import viewnext.practica4.entities.Directores;
import viewnext.practica4.entitiesDTOs.DirectoresDto;
import viewnext.practica4.repositories.DirectoresRepository;
import viewnext.practica4.servicesImp.DirectoresServiceImp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * The type Directores service imp test.
 */
public class DirectoresServiceImpTest {
    @Mock
    private DirectoresRepository directoresRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Directores> typedQuery;

    @InjectMocks
    private DirectoresServiceImp directoresServiceImp;

    private Directores directores;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        directores = new Directores();
        directores.setIdDirector("00000000H");
        directores.setNombre("Juan");
        directores.setApellido("Lopez");
        directores.setEdad(23);
        directores.setNacionalidad("Española");
        directores.setPeliculas(Collections.emptySet());
        directores.setSeries(Collections.emptySet());
    }

    /**
     * Director list directores test.
     */
    @Test
    public void director_listDirectoresTest() {
        when(directoresRepository.findAllByOrderByNombreAsc()).thenReturn(Arrays.asList(directores));

        List<DirectoresDto> directores = directoresServiceImp.listarDirectores();

        assertNotNull(directores);
        assertEquals(1, directores.size());
    }

    /**
     * Director guardar directores test.
     */
    @Test
    public void director_guardarDirectoresTest() {
        when(directoresRepository.save(directores)).thenReturn(directores);

        Directores directoresNuevo = directoresServiceImp.anadirDirectores(directores);

        assertNotNull(directoresNuevo);
        assertEquals("00000000H", directoresNuevo.getIdDirector());
    }

    /**
     * Director modificar directores test.
     */
    @Test
    public void director_modificarDirectoresTest() {

        Directores directorEnBD = new Directores();
        directorEnBD.setIdDirector("00000000H");
        directorEnBD.setNombre("Juan");
        directorEnBD.setApellido("Lopez");
        directorEnBD.setEdad(23);
        directorEnBD.setNacionalidad("Española");
        directorEnBD.setPeliculas(Collections.emptySet());
        directorEnBD.setSeries(Collections.emptySet());

        Directores directorModificado = new Directores();
        directorModificado.setIdDirector("00000000H");
        directorModificado.setNombre("Juan");
        directorModificado.setApellido("Lopez");
        directorModificado.setEdad(23);
        directorModificado.setNacionalidad("Brasileña");
        directorModificado.setPeliculas(Collections.emptySet());
        directorModificado.setSeries(Collections.emptySet());

        when(directoresRepository.findById("00000000H")).thenReturn(Optional.of(directorEnBD));
        when(directoresRepository.save(directorEnBD)).thenReturn(directorEnBD);

        Directores resultado = directoresServiceImp.modificarDirectores(directorModificado);

        assertNotNull(resultado);
        assertEquals("Brasileña", resultado.getNacionalidad());
    }

    /**
     * Director borrar directores test.
     */
    @Test
    public void director_borrarDirectoresTest() {
        doNothing().when(directoresRepository).deleteById("00000000H");

        assertDoesNotThrow(() -> directoresServiceImp.eliminarDirectores("00000000H"));
    }

    /**
     * Actor buscar directores con filtros test.
     */
    @Test
    void actor_buscarDirectoresConFiltrosTest() {

        String nombre = "Juan";
        String nacionalidad = "Española";
        Integer edadMin = 18;
        Integer edadMax = 30;

        when(directoresRepository.buscarDirectoresConFiltros(nombre, nacionalidad, edadMin, edadMax)).thenReturn(
                Arrays.asList(directores));

        List<DirectoresDto> directoresDtoList = directoresServiceImp.buscarDirectoresConFiltros(nombre, nacionalidad,
                edadMin, edadMax);

        assertNotNull(directoresDtoList);
        assertEquals(1, directoresDtoList.size());
        assertEquals("Juan", directoresDtoList.get(0).getNombre());
    }
}
