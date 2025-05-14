package viewnext.practica5.batch.config.processorTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ItemProcessor;
import viewnext.practica5.batch.config.processor.UsuarioCsvProcessorConfig;
import viewnext.practica5.dto.UsuarioDTO;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioCsvProcessorConfigTest {

    private ItemProcessor<UsuarioDTO, UsuarioDTO> processor; // Instancia del procesador a probar

    @BeforeEach
    void setUp() {
        UsuarioCsvProcessorConfig config = new UsuarioCsvProcessorConfig(); // Crea una instancia de la configuración del procesador
        processor = config.usuarioProcessor(); // Obtiene el procesador de usuarios
    }

    @Test
    void devuelveElMismoUsuario_conTodosLosCampos() throws Exception {
        // Prueba que el procesador devuelve el mismo objeto UsuarioDTO sin modificarlo
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setNombre("Ana López");
        usuario.setDni("12345678A");
        usuario.setDireccion("Calle Falsa 123");
        usuario.setCiudad("Madrid");
        usuario.setCodPostal("28080");
        usuario.setImporte(99.99);
        usuario.setNumPedido(5);

        UsuarioDTO resultado = processor.process(usuario); // Procesa el objeto UsuarioDTO

        // Se espera que sea el mismo objeto
        assertSame(usuario, resultado);

        // Verificamos que los datos se mantengan
        assertEquals("Ana López", resultado.getNombre());
        assertEquals("12345678A", resultado.getDni());
        assertEquals("Calle Falsa 123", resultado.getDireccion());
        assertEquals("Madrid", resultado.getCiudad());
        assertEquals("28080", resultado.getCodPostal());
        assertEquals(99.99, resultado.getImporte());
        assertEquals(5, resultado.getNumPedido());
    }

    @Test
    void devuelveNull_siElUsuarioEsNull() throws Exception {
        // Prueba que si el objeto UsuarioDTO de entrada es null, el procesador devuelve null
        UsuarioDTO resultado = processor.process(null);
        assertNull(resultado);
    }
}