package viewnext.practica5.batch.config.processorTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import viewnext.practica5.batch.config.processor.CalleProcessor;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

import static org.junit.jupiter.api.Assertions.*;

public class CalleProcessorTest {

    private CalleProcessor processor; // Instancia del procesador a probar

    @BeforeEach
    void setUp() {
        processor = new CalleProcessor(); // Crea una instancia del procesador
    }

    @Test
    void procesaDtoCorrectamente() throws Exception {
        // Prueba que un CalleDto con datos válidos se procesa correctamente
        CalleDto dto = new CalleDto();
        dto.setCodigoCalle(123);
        dto.setTipoVia("Avenida");
        dto.setNombreCalle("Gran Vía");
        dto.setPrimerNumTramo(1);
        dto.setUltimoNumTramo(99);
        dto.setBarrio("Centro");
        dto.setCodigoDistrito(05);
        dto.setNombreDistrito("CENTRO");

        Calle calle = processor.process(dto); // Procesa el DTO

        assertNotNull(calle); // Asegura que el resultado no es nulo
        assertEquals(123, calle.getCodigoCalle()); // Verifica que el código de calle se mapeó correctamente
        assertEquals("Gran Vía", calle.getNombreCalle()); // Verifica que el nombre de la calle se mapeó correctamente
        assertEquals(99, calle.getUltimoNumTramo()); // Verifica que el último número de tramo se mapeó correctamente
    }

    @Test
    void retornaNullSiElDtoEsNull() throws Exception {
        // Prueba que si el CalleDto de entrada es nulo, el procesador devuelve nulo
        Calle calle = processor.process(null); // Procesa un DTO nulo
        assertNull(calle); // Asegura que el resultado es nulo (no se procesó)
    }
}