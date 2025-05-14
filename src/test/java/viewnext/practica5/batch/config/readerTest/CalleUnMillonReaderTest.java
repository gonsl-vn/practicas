package viewnext.practica5.batch.config.readerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.ClassPathResource;
import viewnext.practica5.batch.config.reader.CalleUnMillonReader;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CalleUnMillonReaderTest {

    @InjectMocks
    private CalleUnMillonReader calleUnMillonReader;

    @Mock
    private ExecutionContext executionContext;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks utilizando la anotación @Mock
        MockitoAnnotations.openMocks(this);
        // En este caso, calleUnMillonReader tendrá su dependencia executionContext inyectada
    }

    @Test
    void testResourceFileLoading() {
        // Prueba que el archivo de recursos CSV para el lector de un millón de calles se carga correctamente
        ClassPathResource resource = new ClassPathResource("tramos_calle_BarrioDismuniOneMillion.csv");
        // Verifica que el recurso existe en la ruta especificada dentro del classpath
        assertTrue(resource.exists(), "El archivo CSV debería existir en la ruta especificada");
    }
}