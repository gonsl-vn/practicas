package viewnext.practica5.batch.config.processorTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ItemProcessor;
import viewnext.practica5.batch.config.processor.CalleCsvProcessor;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

import static org.junit.jupiter.api.Assertions.*;

public class CalleCsvProcessorTest {

    private ItemProcessor<CalleDto, Calle> processor; // Instancia del procesador a probar

    @BeforeEach
    void setUp() {
        CalleCsvProcessor calleCsvProcessor = new CalleCsvProcessor(); // Crea una instancia del procesador
        processor = calleCsvProcessor.processorCsv(); // Obtiene el procesador específico a probar
    }

    @Test
    void procesaCalleCorrecta() throws Exception {
        // Prueba que una CalleDto con datos válidos y del distrito "ESTE" se procesa correctamente
        CalleDto dto = new CalleDto();
        dto.setCodigoCalle(123);
        dto.setTipoVia("Calle");
        dto.setNombreCalle("Gran Vía");
        dto.setPrimerNumTramo(1);
        dto.setUltimoNumTramo(10);
        dto.setBarrio("Centro");
        dto.setCodigoDistrito(1);
        dto.setNombreDistrito("ESTE"); // Distrito correcto

        Calle result = processor.process(dto); // Procesa el DTO

        assertNotNull(result); // Asegura que el resultado no es nulo (se procesó)
        assertEquals(123, result.getCodigoCalle()); // Verifica que el código de calle se mapeó correctamente
        assertEquals("Gran Vía", result.getNombreCalle()); // Verifica que el nombre de calle se mapeó correctamente
        assertEquals("ESTE", result.getNombreDistrito()); // Verifica que el nombre del distrito se mapeó correctamente
    }

    @Test
    void ignoraCalleDeOtroDistrito() throws Exception {
        // Prueba que una CalleDto de un distrito diferente a "ESTE" se ignora (devuelve null)
        CalleDto dto = new CalleDto();
        dto.setCodigoCalle(123);
        dto.setNombreCalle("Gran Vía");
        dto.setNombreDistrito("OESTE"); // Distrito incorrecto

        Calle result = processor.process(dto); // Procesa el DTO

        assertNull(result); // Asegura que el resultado es nulo
    }

    @Test
    void ignoraCalleConDatosFaltantes() throws Exception {
        // Prueba que una CalleDto con datos obligatorios faltantes (nombreCalle nulo) se ignora
        CalleDto dto = new CalleDto();
        dto.setNombreDistrito("ESTE");
        dto.setNombreCalle(null); // Falta el nombre de la calle

        Calle result = processor.process(dto); // Procesa el DTO

        assertNull(result); // Asegura que el resultado es nulo
    }

    @Test
    void ignoraSiDtoEsNull() throws Exception {
        // Prueba que si el CalleDto de entrada es nulo, el procesador devuelve nulo
        Calle result = processor.process(null); // Procesa un DTO nulo
        assertNull(result); // Asegura que el resultado es nulo
    }
}