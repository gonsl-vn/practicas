package viewnext.practica5.batch.config.processorTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import viewnext.practica5.batch.config.processor.DistritoPassthroughProcessor;
import viewnext.practica5.model.Distrito;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Distrito passthrough processor test.
 */
@ExtendWith(MockitoExtension.class)
public class DistritoPassthroughProcessorTest {

    @InjectMocks
    private DistritoPassthroughProcessor processor;

    private Distrito distrito;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        distrito = new Distrito();
        distrito.setNombreDistrito("CENTRO");
        distrito.setId(1);
    }

    /**
     * Process devuelve mismo objeto.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void process_devuelveMismoObjeto() throws Exception {
        Distrito result = processor.process(distrito);
        assertNotNull(result);
        assertEquals(distrito, result);
    }

    /**
     * Process con null devuelve null.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void process_conNull_devuelveNull() throws Exception {
        assertNull(processor.process(null));
    }
}
