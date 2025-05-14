package viewnext.practica5.batch.config.processorTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import viewnext.practica5.batch.config.processor.CalleProcessorSinFiltro;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CalleProcessorSinFiltroTest {

    private CalleProcessorSinFiltro processor; // Instancia del procesador a probar

    @BeforeEach
    void setUp() {
        processor = new CalleProcessorSinFiltro(); // Crea una instancia del procesador sin filtro
    }

    @Test
    void procesaCorrectamenteUnDto() {
        // Prueba que un CalleDto se procesa correctamente sin aplicar ningún filtro
        CalleDto dto = new CalleDto();
        dto.setCodigoCalle(456);
        dto.setTipoVia("Calle");
        dto.setNombreCalle("Alcalá");
        dto.setPrimerNumTramo(10);
        dto.setUltimoNumTramo(100);
        dto.setBarrio("Salamanca");
        dto.setCodigoDistrito(02);
        dto.setNombreDistrito("ESTE");

        Calle calle = processor.process(dto); // Procesa el DTO

        assertNotNull(calle); // Asegura que el resultado no es nulo (se procesó)
        assertEquals(456, calle.getCodigoCalle()); // Verifica que el código de calle se mapeó correctamente
        assertEquals("Calle", calle.getTipoVia()); // Verifica que el tipo de vía se mapeó correctamente
        assertEquals("Alcalá", calle.getNombreCalle()); // Verifica que el nombre de la calle se mapeó correctamente
        assertEquals(10, calle.getPrimerNumTramo()); // Verifica que el primer número de tramo se mapeó correctamente
        assertEquals(100, calle.getUltimoNumTramo()); // Verifica que el último número de tramo se mapeó correctamente
        assertEquals("Salamanca", calle.getBarrio()); // Verifica que el barrio se mapeó correctamente
        assertEquals(02, calle.getCodigoDistrito()); // Verifica que el código de distrito se mapeó correctamente
        assertEquals("ESTE", calle.getNombreDistrito()); // Verifica que el nombre del distrito se mapeó correctamente
    }
}