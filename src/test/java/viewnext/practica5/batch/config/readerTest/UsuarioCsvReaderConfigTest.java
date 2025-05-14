package viewnext.practica5.batch.config.readerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.ClassPathResource;
import viewnext.practica5.batch.config.reader.UsuarioCsvReaderConfig;
import viewnext.practica5.dto.UsuarioDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UsuarioCsvReaderConfigTest {

    private FlatFileItemReader<UsuarioDTO> reader; // Instancia del lector CSV de Usuarios a probar

    @BeforeEach
    void setUp() {
        UsuarioCsvReaderConfig config = new UsuarioCsvReaderConfig(); // Crea una instancia de la configuración del lector de Usuarios
        reader = config.usuarioReader(); // Obtiene el lector de Usuarios

        // Configura el recurso del lector para que lea desde el archivo "ficheroUsuarios.csv"
        reader.setResource(new ClassPathResource("ficheroUsuarios.csv"));
    }

    @Test
    void testUsuarioReader_readsCorrectly() throws Exception {
        // Prueba que el lector de Usuarios lee correctamente los datos del archivo CSV
        reader.open(new ExecutionContext()); // Abre el lector con un contexto de ejecución vacío

        UsuarioDTO usuario = reader.read(); // Lee la primera línea del archivo

        // Verifica que se leyó un objeto UsuarioDTO
        assertNotNull(usuario);
        // Verifica los valores de los campos del objeto UsuarioDTO leído (dependiendo del contenido de "ficheroUsuarios.csv")
        assertEquals("Juan Pérez", usuario.getNombre());
        assertEquals("12345678A", usuario.getDni());
        assertEquals("Calle Falsa 123", usuario.getDireccion());
        assertEquals("Madrid", usuario.getCiudad());
        assertEquals("28001", usuario.getCodPostal());
        assertEquals(100.0, usuario.getImporte());
        assertEquals(1, usuario.getNumPedido());

    }
}