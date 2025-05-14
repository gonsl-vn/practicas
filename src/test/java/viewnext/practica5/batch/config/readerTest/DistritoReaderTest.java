package viewnext.practica5.batch.config.readerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import viewnext.practica5.batch.config.reader.DistritoReader;
import viewnext.practica5.model.Distrito;
import viewnext.practica5.repository.CalleRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DistritoReaderTest {

    private CalleRepository calleRepository; // Mock del repositorio de Calles
    private DistritoReader distritoReader; // Instancia del lector de Distritos a probar

    @BeforeEach
    void setUp() {
        // Crea un mock del repositorio de Calles
        calleRepository = mock(CalleRepository.class);

        // Simula los datos que devolverá la consulta countViviendasPorDistrito
        List<Object[]> mockData = Arrays.asList(new Object[] { "Centro", 150L }, new Object[] { "Este", 230L });

        // Configura el comportamiento del mock del repositorio para devolver los datos simulados
        when(calleRepository.countViviendasPorDistrito()).thenReturn(mockData);

        // Crea una instancia del lector de Distritos con el repositorio mockeado
        distritoReader = new DistritoReader(calleRepository);
    }

    @Test
    void testReadMultipleDistritos() {
        // Prueba la lectura de múltiples distritos desde el lector
        Distrito primero = distritoReader.read(); // Lee el primer distrito
        assertNotNull(primero); // Asegura que se leyó un distrito
        assertEquals(1, primero.getId()); // Verifica el ID del primer distrito
        assertEquals("Centro", primero.getNombreDistrito()); // Verifica el nombre del primer distrito
        assertEquals(150, primero.getNumeroViviendas()); // Verifica el número de viviendas del primer distrito

        Distrito segundo = distritoReader.read(); // Lee el segundo distrito
        assertNotNull(segundo); // Asegura que se leyó otro distrito
        assertEquals(2, segundo.getId()); // Verifica el ID del segundo distrito
        assertEquals("Este", segundo.getNombreDistrito()); // Verifica el nombre del segundo distrito
        assertEquals(230, segundo.getNumeroViviendas()); // Verifica el número de viviendas del segundo distrito

        Distrito fin = distritoReader.read(); // Intenta leer un tercer distrito
        assertNull(fin); // Asegura que ya no hay más datos para leer
    }

    @Test
    void testReadSinResultados() {
        // Prueba el comportamiento del lector cuando la consulta del repositorio no devuelve resultados
        when(calleRepository.countViviendasPorDistrito()).thenReturn(
                List.of()); // Configura el repositorio para devolver una lista vacía

        DistritoReader emptyReader = new DistritoReader(
                calleRepository); // Crea un nuevo lector con el repositorio configurado
        Distrito resultado = emptyReader.read(); // Intenta leer un distrito

        assertNull(resultado); // Asegura que no se leyó ningún distrito (no hay datos)
    }
}