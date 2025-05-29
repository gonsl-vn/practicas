package viewnext.practica5.batch.config.processorTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import viewnext.practica5.batch.config.processor.CalleItemProcessor;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Calle item processor test.
 */
@ExtendWith(MockitoExtension.class)
public class CalleItemProcessorTest {

    @InjectMocks
    private CalleItemProcessor processor;

    private CalleDto dto;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        dto = new CalleDto();
        dto.setCodigoCalle(1);
        dto.setTipoVia("Calle");
        dto.setNombreCalle("Principal");
        dto.setBarrio("Centro");
        dto.setCodigoDistrito(10);
        dto.setNombreDistrito("ESTE");
        dto.setPrimerNumTramo(100);
    }

    /**
     * Process filtra distritos distintos de este.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void process_filtraDistritosDistintosDeEste() throws Exception {
        dto.setNombreDistrito("OESTE");
        assertNull(processor.process(dto));
    }

    /**
     * Process convierte ultimo num tramo correctamente.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void process_convierteUltimoNumTramoCorrectamente() throws Exception {
        dto.setUltimoNumTramo(200);
        Calle result = processor.process(dto);
        assertNotNull(result);
        assertEquals(200, result.getUltimoNumTramo());
    }
}
