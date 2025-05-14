package viewnext.practica5.batch.config.writerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import viewnext.practica5.batch.config.writer.UsuarioCsvWriterConfig;
import viewnext.practica5.dto.UsuarioDTO;
import viewnext.practica5.model.Usuario;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UsuarioCsvWriterConfigTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UsuarioCsvWriterConfig usuarioCsvWriterConfig;

    @Mock
    private JdbcBatchItemWriter<Usuario> jdbcBatchItemWriter;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks utilizando la anotación @Mock
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testUsuarioWriter() {
        // Prueba la creación del JdbcBatchItemWriter para la entidad UsuarioDTO
        JdbcBatchItemWriter<UsuarioDTO> writer = usuarioCsvWriterConfig.usuarioWriter();

        assertNotNull(writer); // Asegura que el escritor se creó correctamente (no es nulo)
    }
}