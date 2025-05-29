package viewnext.practica5.batch.config.writerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import viewnext.practica5.batch.config.writer.CalleItemWriter;
import viewnext.practica5.model.Calle;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Calle item writer test.
 */
class CalleItemWriterTest {

    @Mock
    private DataSource dataSource;

    @InjectMocks
    private CalleItemWriter calleItemWriter;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test writer.
     */
    @Test
    void testWriter() {
        JdbcBatchItemWriter<Calle> writer = calleItemWriter.writer();
        assertNotNull(writer, "El JdbcBatchItemWriter no debe ser null");
    }
}
