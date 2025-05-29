package viewnext.practica5.batch.config.processorTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import viewnext.practica5.batch.config.processor.CalleProcessorSinFiltro;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Calle processor sin filtro test.
 */
@ExtendWith(MockitoExtension.class)
public class CalleProcessorSinFiltroTest {

    @InjectMocks
    private CalleProcessorSinFiltro processor;

    private CalleDto dto;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        dto = new CalleDto();
        dto.setCodigoCalle(1);
        dto.setTipoVia("Avenida");
        dto.setNombreCalle("Libertad");
        dto.setPrimerNumTramo(10);
        dto.setUltimoNumTramo(20);
        dto.setBarrio("Centro");
        dto.setCodigoDistrito(5);
        dto.setNombreDistrito("NORTE");
    }

    /**
     * Process devuelve calle con mismos datos.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    void process_devuelveCalleConMismosDatos() throws Exception {
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
}
