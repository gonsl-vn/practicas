package viewnext.practica5.batch.config.readerTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.file.FlatFileItemReader;
import viewnext.practica5.batch.config.reader.CalleCsvReader;
import viewnext.practica5.dto.CalleDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Calle csv reader test.
 */
@ExtendWith(MockitoExtension.class)
class CalleCsvReaderTest {

    @InjectMocks
    private CalleCsvReader reader;

    /**
     * Reader no es nulo y config correcta.
     */
    @Test
    void reader_noEsNulo_yConfigCorrecta() {
        FlatFileItemReader<CalleDto> itemReader = reader.reader();
        assertNotNull(itemReader);
        assertEquals("calleReader", itemReader.getName());

    }

    /**
     * Csv completo reader no es nulo y config correcta.
     */
    @Test
    void csvCompletoReader_noEsNulo_yConfigCorrecta() {
        FlatFileItemReader<CalleDto> itemReader = reader.csvCompletoReader();
        assertNotNull(itemReader);
        assertEquals("csvCompletoReader", itemReader.getName());
    }
}
