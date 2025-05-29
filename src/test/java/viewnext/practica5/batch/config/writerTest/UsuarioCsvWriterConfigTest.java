package viewnext.practica5.batch.config.writerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import viewnext.practica5.batch.config.writer.UsuarioCsvWriterConfig;
import viewnext.practica5.dto.UsuarioDTO;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Usuario csv writer config test.
 */
class UsuarioCsvWriterConfigTest {

    @Mock
    private DataSource dataSource;

    @InjectMocks
    private UsuarioCsvWriterConfig usuarioCsvWriterConfig;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test usuario writer.
     */
    @Test
    void testUsuarioWriter() {
        JdbcBatchItemWriter<UsuarioDTO> writer = usuarioCsvWriterConfig.usuarioWriter();

        assertNotNull(writer, "El writer no debe ser null");
    }
}
