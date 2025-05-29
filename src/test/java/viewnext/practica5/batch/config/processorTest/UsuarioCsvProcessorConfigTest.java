package viewnext.practica5.batch.config.processorTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.ItemProcessor;
import viewnext.practica5.batch.config.processor.UsuarioCsvProcessorConfig;
import viewnext.practica5.dto.UsuarioDTO;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Usuario csv processor config test.
 */
@ExtendWith(MockitoExtension.class)
public class UsuarioCsvProcessorConfigTest {

    @InjectMocks
    private UsuarioCsvProcessorConfig config;

    /**
     * Usuario processor devuelve mismo objeto.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void usuarioProcessor_devuelveMismoObjeto() throws Exception {
        ItemProcessor<UsuarioDTO, UsuarioDTO> processor = config.usuarioProcessor();

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setNombre("Juan");
        usuario.setCiudad("Madrid");
        usuario.setDireccion("Su calle");
        usuario.setDni("22435212H");

        UsuarioDTO resultado = processor.process(usuario);

        assertNotNull(resultado);
        assertEquals(usuario, resultado);
    }

    /**
     * Usuario processor con null devuelve null.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void usuarioProcessor_conNull_devuelveNull() throws Exception {
        ItemProcessor<UsuarioDTO, UsuarioDTO> processor = config.usuarioProcessor();
        assertNull(processor.process(null));
    }
}
