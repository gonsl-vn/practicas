package viewnext.practica5.batch.config.writerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.file.FlatFileItemWriter;
import viewnext.practica5.batch.config.writer.DistritoCsvWriter;
import viewnext.practica5.model.Distrito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Distrito csv writer test.
 */
class DistritoCsvWriterTest {

    @InjectMocks
    private DistritoCsvWriter distritoCsvWriter;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test writer creation.
     */
    @Test
    void testWriterCreation() {
        FlatFileItemWriter<Distrito> writer = distritoCsvWriter.writer();

        assertNotNull(writer, "El writer no debe ser null");
        assertEquals("distritoCsvWriter", writer.getName(), "El nombre del writer no es el esperado");
    }
}
