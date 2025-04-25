package viewnext.practica4.serviceImp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import viewnext.practica4.entities.Peliculas;
import viewnext.practica4.entitiesDTOs.PeliculasDto;
import viewnext.practica4.repositories.PeliculasRepository;
import viewnext.practica4.servicesImp.PeliculasServiceImp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * The type Peliculas service imp test.
 */
public class PeliculasServiceImpTest {
    @Mock
    private PeliculasRepository peliculasRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Peliculas> typedQuery;

    @InjectMocks
    private PeliculasServiceImp peliculasServiceImp;

    private Peliculas peliculas;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        peliculas = new Peliculas();
        peliculas.setIdPelicula(1L);
        peliculas.setTitulo("Cars");
        peliculas.setAnio(1999);
        peliculas.setActores(new HashSet<>());
        peliculas.setProductoras(null);
        peliculas.setDirectores(null);
    }

    /**
     * Peliculas listar peliculas test.
     */
    @Test
    public void peliculas_listarPeliculasTest() {
        when(peliculasRepository.findAllByOrderByTituloAsc()).thenReturn(Arrays.asList(peliculas));

        List<PeliculasDto> peliculas = peliculasServiceImp.listarPeliculas();

        assertNotNull(peliculas);
        assertEquals(1, peliculas.size());
    }

    /**
     * Peliculas anadir pelicula test.
     */
    @Test
    public void peliculas_anadirPeliculaTest() {
        when(peliculasRepository.save(peliculas)).thenReturn(peliculas);

        Peliculas peliculasNueva = peliculasServiceImp.anadirPelicula(peliculas);

        assertNotNull(peliculasNueva);
        assertEquals(1L, peliculasNueva.getIdPelicula());
    }

    /**
     * Peliculas modificar peliculas test.
     */
    @Test
    public void peliculas_modificarPeliculasTest() {

        Peliculas peliculasEnBD = new Peliculas();
        peliculasEnBD.setIdPelicula(1L);
        peliculasEnBD.setTitulo("Cars");
        peliculasEnBD.setAnio(1999);
        peliculasEnBD.setActores(new HashSet<>());
        peliculasEnBD.setProductoras(null);
        peliculasEnBD.setDirectores(null);

        Peliculas peliculaModificada = new Peliculas();
        peliculaModificada.setIdPelicula(1L);
        peliculaModificada.setTitulo("Cars");
        peliculaModificada.setAnio(2010);
        peliculaModificada.setActores(new HashSet<>());
        peliculaModificada.setProductoras(null);
        peliculaModificada.setDirectores(null);

        when(peliculasRepository.findById(1L)).thenReturn(Optional.of(peliculasEnBD));
        when(peliculasRepository.save(peliculasEnBD)).thenReturn(peliculasEnBD);

        Peliculas resultado = peliculasServiceImp.modificarPeliculas(peliculaModificada);

        assertNotNull(resultado);
        assertEquals(2010, resultado.getAnio());
    }

    /**
     * Peliculas eliminar peliculas test.
     */
    @Test
    public void peliculas_eliminarPeliculasTest() {
        doNothing().when(peliculasRepository).deleteById(1L);

        assertDoesNotThrow(() -> peliculasServiceImp.borrarPeliculas(1L));
    }

    /**
     * Peliculas buscar peliculas con filtros test.
     */
    @Test
    void peliculas_buscarPeliculasConFiltrosTest() {

        String titulo = "Cars";
        Integer anioMin = 1995;
        Integer anioMax = 2020;
        String productora = null;
        String director = null;

        when(peliculasRepository.buscarPeliculasConFiltros(titulo, anioMin, anioMax, productora, director)).thenReturn(
                Arrays.asList(peliculas));

        List<PeliculasDto> peliculasList = peliculasServiceImp.buscarPeliculasConFiltros(titulo, anioMin, anioMax,
                productora, director);

        assertNotNull(peliculasList);
        assertEquals(1, peliculasList.size());
        assertEquals("Cars", peliculasList.get(0).getTitulo());
    }
}
