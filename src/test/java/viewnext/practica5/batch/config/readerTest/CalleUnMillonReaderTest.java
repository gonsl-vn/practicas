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

/**
 * The type Calle un millon reader test.
 */
class CalleUnMillonReaderTest {

    @InjectMocks
    private CalleUnMillonReader calleUnMillonReader;

    @Mock
    private ExecutionContext executionContext;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test resource file loading.
     */
    @Test
    void testResourceFileLoading() {
        ClassPathResource resource = new ClassPathResource("tramos_calle_BarrioDismuniOneMillion.csv");
        assertTrue(resource.exists(), "El archivo CSV debería existir en la ruta especificada");
    }
}