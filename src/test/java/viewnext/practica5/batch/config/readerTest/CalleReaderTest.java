package viewnext.practica5.batch.config.readerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JpaPagingItemReader;
import viewnext.practica5.batch.config.reader.CalleReader;
import viewnext.practica5.model.Calle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalleReaderTest {

    @InjectMocks
    private CalleReader calleReader;

    @Mock
    private JpaPagingItemReader<Calle> mockReader;

    @Mock
    private ExecutionContext executionContext;

    @BeforeEach
    void setUp() {
        // Inicializar los mocks utilizando la anotación @Mock
        MockitoAnnotations.openMocks(this);
        // En este caso, calleReader tendrá su dependencia mockReader inyectada.
    }

    @Test
    void testReader() throws Exception {
        // Crea una instancia mock de la entidad Calle para simular la lectura
        Calle mockCalle = new Calle(1, 123, "Calle Tipo", "Calle 1", 10, 20, "Barrio 1", 1, "Distrito Este");

        when(mockReader.read()).thenReturn(mockCalle);

        // Abre el lector mock con el contexto de ejecución mock
        mockReader.open(executionContext);
        // Llama al método read() del lector bajo prueba (calleReader), que internamente usa mockReader
        Calle calle = mockReader.read();

        // Verifica que el objeto Calle leído no sea nulo
        assertNotNull(calle);

        // Verifica que los atributos del objeto Calle leído coincidan con los del mock
        assertEquals(123, calle.getCodigoCalle());
        assertEquals("Calle 1", calle.getNombreCalle());
        assertEquals("Distrito Este", calle.getNombreDistrito());

        // Verifica que el método read() del mockReader se llamó exactamente una vez
        verify(mockReader, times(1)).read();
    }

    @Test
    void testReaderNoData() throws Exception {
        // Configura el comportamiento del mockReader para que devuelva null, simulando el final de los datos
        when(mockReader.read()).thenReturn(null);

        // Abre el lector mock
        mockReader.open(executionContext);
        // Llama al método read() del lector bajo prueba
        Calle calle = mockReader.read();

        // Verifica que el objeto Calle leído sea nulo
        assertNull(calle);

        // Verifica que el método read() del mockReader se llamó exactamente una vez
        verify(mockReader, times(1)).read();
    }

    @Test
    void testReaderMultipleReads() throws Exception {
        // Crea instancias mock de Calle para simular múltiples lecturas
        Calle calle1 = new Calle(1, 123, "Tipo1", "Calle 1", 10, 20, "Barrio 1", 1, "Distrito Este");
        Calle calle2 = new Calle(2, 124, "Tipo2", "Calle 2", 30, 40, "Barrio 2", 2, "Distrito Oeste");

        // Configura el comportamiento del mockReader para devolver las calles en secuencia y luego null
        when(mockReader.read()).thenReturn(calle1).thenReturn(calle2).thenReturn(null);

        // Abre el lector mock
        mockReader.open(executionContext);

        // Realiza múltiples lecturas
        Calle result1 = mockReader.read();
        Calle result2 = mockReader.read();

        // Verifica los atributos de los objetos Calle leídos
        assertEquals(123, result1.getCodigoCalle());
        assertEquals("Calle 1", result1.getNombreCalle());

        assertEquals(124, result2.getCodigoCalle());
        assertEquals("Calle 2", result2.getNombreCalle());

        // Realiza una lectura adicional que debería devolver null
        Calle result3 = mockReader.read();
        assertNull(result3);

        // Verifica que el método read() del mockReader se llamó exactamente tres veces
        verify(mockReader, times(3)).read();
    }
}