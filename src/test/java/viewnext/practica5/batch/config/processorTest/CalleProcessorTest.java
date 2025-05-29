package viewnext.practica5.batch.config.processorTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import viewnext.practica5.batch.config.processor.CalleProcessor;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Calle processor test.
 */
@ExtendWith(MockitoExtension.class)
public class CalleProcessorTest {

    @InjectMocks
    private CalleProcessor processor;

    private CalleDto dto;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        dto = new CalleDto();
        dto.setCodigoCalle(100);
        dto.setTipoVia("Calle");
        dto.setNombreCalle("Mayor");
        dto.setPrimerNumTramo(1);
        dto.setUltimoNumTramo(50);
        dto.setBarrio("Centro");
        dto.setCodigoDistrito(2);
        dto.setNombreDistrito("SUR");
    }

    /**
     * Process con dto no nulo devuelve objeto calle con datos iguales.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void process_conDtoNoNulo_devuelveObjetoCalleConDatosIguales() throws Exception {
        Calle result = processor.process(dto);

        assertNotNull(result);
        assertEquals(dto.getCodigoCalle(), result.getCodigoCalle());
        assertEquals(dto.getTipoVia(), result.getTipoVia());
        assertEquals(dto.getNombreCalle(), result.getNombreCalle());
        assertEquals(dto.getPrimerNumTramo(), result.getPrimerNumTramo());
        assertEquals(dto.getUltimoNumTramo(), result.getUltimoNumTramo());
        assertEquals(dto.getBarrio(), result.getBarrio());
        assertEquals(dto.getCodigoDistrito(), result.getCodigoDistrito());
        assertEquals(dto.getNombreDistrito(), result.getNombreDistrito());
    }

    /**
     * Process con dto nulo devuelve null.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void process_conDtoNulo_devuelveNull() throws Exception {
        Calle result = processor.process(null);
        assertNull(result);
    }
}
