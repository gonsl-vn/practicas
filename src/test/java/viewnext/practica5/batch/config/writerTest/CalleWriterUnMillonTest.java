package viewnext.practica5.batch.config.writerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import viewnext.practica5.batch.config.writer.CalleWriterUnMillon;
import viewnext.practica5.model.Calle;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Calle writer un millon test.
 */
class CalleWriterUnMillonTest {

    @Mock
    private DataSource dataSource;

    @InjectMocks
    private CalleWriterUnMillon calleWriterUnMillon;

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
        JdbcBatchItemWriter<Calle> writer = calleWriterUnMillon.writer(dataSource);
        assertNotNull(writer, "El writer no debería ser null");
    }
}
