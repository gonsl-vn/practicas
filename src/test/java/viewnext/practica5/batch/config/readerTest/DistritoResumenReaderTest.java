package viewnext.practica5.batch.config.readerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import viewnext.practica5.batch.config.reader.DistritoResumenReader;
import viewnext.practica5.model.Distrito;
import viewnext.practica5.repository.CalleRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DistritoResumenReaderTest {

    private CalleRepository calleRepository; // Mock del repositorio de Calles
    private DistritoResumenReader distritoResumenReader; // Instancia del lector de resúmenes de distritos a probar

    @BeforeEach
    void setUp() {
        // Crea un mock del repositorio de Calles
        calleRepository = mock(CalleRepository.class);

        // Simula los datos que devolverá la consulta countViviendasPorDistrito
        List<Object[]> mockData = Arrays.asList(new Object[] { "ESTE", 150L }, new Object[] { "NORTE", 200L });

        // Configura el comportamiento del mock del repositorio para devolver los datos simulados
        when(calleRepository.countViviendasPorDistrito()).thenReturn((List) mockData);

        // Crea una instancia del lector de resúmenes de distritos con el repositorio mockeado
        distritoResumenReader = new DistritoResumenReader(calleRepository);
    }

    @Test
    void testRead_successfullyReadsMultipleDistritos() {
        // Prueba la lectura exitosa de múltiples resúmenes de distritos
        Distrito primero = distritoResumenReader.read(); // Lee el primer resumen de distrito
        assertNotNull(primero); // Asegura que se leyó un distrito
        assertEquals("ESTE", primero.getNombreDistrito()); // Verifica el nombre del primer distrito
        assertEquals(150, primero.getNumeroViviendas()); // Verifica el número de viviendas del primer distrito

        Distrito segundo = distritoResumenReader.read(); // Lee el segundo resumen de distrito
        assertNotNull(segundo); // Asegura que se leyó otro distrito
        assertEquals("NORTE", segundo.getNombreDistrito()); // Verifica el nombre del segundo distrito
        assertEquals(200, segundo.getNumeroViviendas()); // Verifica el número de viviendas del segundo distrito

        // Ya no hay más datos
        Distrito fin = distritoResumenReader.read(); // Intenta leer un tercer resumen de distrito
        assertNull(fin); // Asegura que ya no hay más datos para leer
    }
}