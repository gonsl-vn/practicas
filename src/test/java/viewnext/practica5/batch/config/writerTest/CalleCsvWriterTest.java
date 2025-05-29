package viewnext.practica5.batch.config.writerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import viewnext.practica5.batch.config.writer.CalleCsvWriter;
import viewnext.practica5.model.Calle;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Calle csv writer test.
 */
class CalleCsvWriterTest {

    @Mock
    private DataSource dataSource;
    @InjectMocks
    private CalleCsvWriter calleCsvWriter;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test csv writer creates flat file item writer.
     */
    @Test
    void testCsvWriter_createsFlatFileItemWriter() {
        FlatFileItemWriter<Calle> writer = calleCsvWriter.writer();
        assertNotNull(writer, "El FlatFileItemWriter no debe ser null");
    }

    /**
     * Test jdbc writer creates jdbc batch item writer.
     */
    @Test
    void testJdbcWriter_createsJdbcBatchItemWriter() {
        JdbcBatchItemWriter<Calle> writer = calleCsvWriter.calleWriterCompleto();
        assertNotNull(writer, "El JdbcBatchItemWriter no debe ser null");
    }
}
