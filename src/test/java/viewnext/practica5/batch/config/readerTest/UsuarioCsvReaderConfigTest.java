package viewnext.practica5.batch.config.readerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import viewnext.practica5.batch.config.reader.UsuarioCsvReaderConfig;
import viewnext.practica5.dto.UsuarioDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Usuario csv reader config test.
 */
@ExtendWith(MockitoExtension.class)
class UsuarioCsvReaderConfigTest {

    @InjectMocks
    private UsuarioCsvReaderConfig usuarioCsvReaderConfig;

    private FlatFileItemReader<UsuarioDTO> reader;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        reader = usuarioCsvReaderConfig.usuarioReader();
    }

    /**
     * Test usuario reader reads first record.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void testUsuarioReader_readsFirstRecord() throws Exception {
        reader.open(new ExecutionContext());

        UsuarioDTO usuario = reader.read();

        assertNotNull(usuario);

        assertEquals("Juan Pérez", usuario.getNombre());
        assertEquals("12345678A", usuario.getDni());
        assertEquals("Calle Falsa 123", usuario.getDireccion());
        assertEquals("Madrid", usuario.getCiudad());
        assertEquals("28001", usuario.getCodPostal());
        assertEquals(100.0, usuario.getImporte());
        assertEquals(1, usuario.getNumPedido());

        reader.close();
    }
}
