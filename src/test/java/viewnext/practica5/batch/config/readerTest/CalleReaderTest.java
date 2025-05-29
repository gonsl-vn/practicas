package viewnext.practica5.batch.config.reader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.database.JpaPagingItemReader;

import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Calle reader test.
 */
@ExtendWith(MockitoExtension.class)
class CalleReaderTest {

    @Mock
    private EntityManagerFactory entityManagerFactory;

    @InjectMocks
    private CalleReader calleReader;

    /**
     * Reader no es nulo y tiene nombre correcto.
     */
    @Test
    void reader_noEsNulo_yTieneNombreCorrecto() {
        JpaPagingItemReader<?> reader = calleReader.reader();
        assertNotNull(reader);
        assertEquals("calleReader", reader.getName());
    }
}
