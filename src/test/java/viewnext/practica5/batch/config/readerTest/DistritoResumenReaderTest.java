package viewnext.practica5.batch.config.readerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import viewnext.practica5.batch.config.reader.DistritoResumenReader;
import viewnext.practica5.model.Distrito;
import viewnext.practica5.repository.CalleRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The type Distrito resumen reader test.
 */
class DistritoResumenReaderTest {

    private CalleRepository calleRepository;
    private DistritoResumenReader distritoResumenReader;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        calleRepository = mock(CalleRepository.class);

        List<Object[]> mockData = Arrays.asList(new Object[] { "ESTE", 150L }, new Object[] { "NORTE", 200L });

        when(calleRepository.countViviendasPorDistrito()).thenReturn(mockData);

        distritoResumenReader = new DistritoResumenReader(calleRepository);
    }

    /**
     * Test read successfully reads multiple distritos.
     */
    @Test
    void testRead_successfullyReadsMultipleDistritos() {
        Distrito primero = distritoResumenReader.read();
        assertNotNull(primero);
        assertEquals("ESTE", primero.getNombreDistrito());
        assertEquals(150, primero.getNumeroViviendas());

        Distrito segundo = distritoResumenReader.read();
        assertNotNull(segundo);
        assertEquals("NORTE", segundo.getNombreDistrito());
        assertEquals(200, segundo.getNumeroViviendas());

        Distrito fin = distritoResumenReader.read();
        assertNull(fin);

        verify(calleRepository, times(1)).countViviendasPorDistrito();
    }
}
