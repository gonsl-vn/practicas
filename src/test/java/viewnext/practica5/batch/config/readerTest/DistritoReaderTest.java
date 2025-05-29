package viewnext.practica5.batch.config.reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import viewnext.practica5.model.Distrito;
import viewnext.practica5.repository.CalleRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The type Distrito reader test.
 */
class DistritoReaderTest {

    @Mock
    private CalleRepository calleRepository;

    @InjectMocks
    private DistritoReader distritoReader;

    /**
     * Sets .
     */
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test read returns distritos correctamente.
     */
    @Test
    void testRead_ReturnsDistritosCorrectamente() {
        List<Object[]> resultadosMock = Arrays.asList(new Object[] { "Distrito1", 10L },
                new Object[] { "Distrito2", 20L });

        when(calleRepository.countViviendasPorDistrito()).thenReturn(resultadosMock);

        Distrito d1 = distritoReader.read();
        assertNotNull(d1);
        assertEquals(1, d1.getId());
        assertEquals("Distrito1", d1.getNombreDistrito());
        assertEquals(10, d1.getNumeroViviendas());

        Distrito d2 = distritoReader.read();
        assertNotNull(d2);
        assertEquals(2, d2.getId());
        assertEquals("Distrito2", d2.getNombreDistrito());
        assertEquals(20, d2.getNumeroViviendas());

        Distrito d3 = distritoReader.read();
        assertNull(d3);

        verify(calleRepository, times(1)).countViviendasPorDistrito();
    }
}
