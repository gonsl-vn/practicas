package viewnext.practica5.batch.config.processorTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import viewnext.practica5.batch.config.processor.DistritoEsteFilterProcessor;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Distrito este filter processor test.
 */
@ExtendWith(MockitoExtension.class)
public class DistritoEsteFilterProcessorTest {

    @InjectMocks
    private DistritoEsteFilterProcessor processor;

    private CalleDto dto;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        dto = new CalleDto();
        dto.setCodigoCalle(1);
        dto.setTipoVia("Avenida");
        dto.setNombreCalle("Principal");
        dto.setPrimerNumTramo(10);
        dto.setUltimoNumTramo(20);
        dto.setBarrio("Centro");
        dto.setCodigoDistrito(5);
        dto.setNombreDistrito("ESTE");
    }

    /**
     * Process con distrito este devuelve calle.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void process_conDistritoEste_devuelveCalle() throws Exception {
        Calle result = processor.process(dto);
        assertNotNull(result);
        assertEquals(dto.getCodigoCalle(), result.getCodigoCalle());
        assertEquals(dto.getNombreDistrito(), result.getNombreDistrito());
    }

    /**
     * Process con distrito distinto devuelve null.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void process_conDistritoDistinto_devuelveNull() throws Exception {
        dto.setNombreDistrito("OESTE");
        Calle result = processor.process(dto);
        assertNull(result);
    }

    /**
     * Process con dto nulo devuelve null.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void process_conDtoNulo_devuelveNull() throws Exception {
        assertNull(processor.process(null));
    }

    /**
     * Process con nombre distrito nulo devuelve null.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void process_conNombreDistritoNulo_devuelveNull() throws Exception {
        dto.setNombreDistrito(null);
        assertNull(processor.process(dto));
    }
}
