package viewnext.practica5.batch.config.processorTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.ItemProcessor;
import viewnext.practica5.batch.config.processor.CalleCsvProcessor;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * The type Calle csv processor test.
 */
@ExtendWith(MockitoExtension.class)
public class CalleCsvProcessorTest {

    @InjectMocks
    private CalleCsvProcessor processor;

    private ItemProcessor<CalleDto, Calle> processorCsv;
    private ItemProcessor<CalleDto, Calle> csvCompletoProcessor;

    /**
     * Init.
     */
    @BeforeEach
    void init() {
        processorCsv = processor.processorCsv();
        csvCompletoProcessor = processor.csvCompletoProcessor();
    }

    /**
     * Processor csv filtro distrito.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void processorCsv_filtroDistrito() throws Exception {
        CalleDto dto = new CalleDto();
        dto.setNombreDistrito("OESTE");
        assertNull(processorCsv.process(dto));
    }

    /**
     * Processor csv procesa correctamente.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void processorCsv_procesaCorrectamente() throws Exception {
        CalleDto dto = new CalleDto();
        dto.setCodigoCalle(1);
        dto.setNombreDistrito("ESTE");
        dto.setNombreCalle("Calle 1");
        assertNotNull(processorCsv.process(dto));
    }

    /**
     * Csv completo processor retorna null si faltan campos.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void csvCompletoProcessor_retornaNullSiFaltanCampos() throws Exception {
        CalleDto dto = new CalleDto();
        assertNull(csvCompletoProcessor.process(dto));
    }

    /**
     * Csv completo processor procesa correctamente.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void csvCompletoProcessor_procesaCorrectamente() throws Exception {
        CalleDto dto = new CalleDto();
        dto.setCodigoCalle(2);
        dto.setNombreCalle("Calle 2");
        assertNotNull(csvCompletoProcessor.process(dto));
    }
}
