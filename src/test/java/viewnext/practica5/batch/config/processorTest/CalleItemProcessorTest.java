package viewnext.practica5.batch.config.processorTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import viewnext.practica5.batch.config.processor.CalleItemProcessor;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

import static org.junit.jupiter.api.Assertions.*;

public class CalleItemProcessorTest {

    private CalleItemProcessor processor; // Instancia del procesador a probar

    @BeforeEach
    void setUp() {
        processor = new CalleItemProcessor(); // Crea una instancia del procesador
    }

    @Test
    void procesaCalleDelDistritoEste() throws Exception {
        // Prueba que una CalleDto del distrito "ESTE" se procesa correctamente
        CalleDto dto = new CalleDto();
        dto.setCodigoCalle(001);
        dto.setTipoVia("Calle");
        dto.setNombreCalle("Alcalá");
        dto.setBarrio("Centro");
        dto.setCodigoDistrito(01);
        dto.setNombreDistrito("ESTE"); // Distrito correcto
        dto.setPrimerNumTramo(1);
        dto.setUltimoNumTramo(100);

        Calle calle = processor.process(dto); // Procesa el DTO

        assertNotNull(calle); // Asegura que el resultado no es nulo (se procesó)
        assertEquals(001, calle.getCodigoCalle()); // Verifica que el código de calle se mapeó correctamente
        assertEquals(100,
                calle.getUltimoNumTramo()); // Verifica que el último número de tramo se convirtió a Integer correctamente
    }

    @Test
    void ignoraCalleDeOtroDistrito() throws Exception {
        // Prueba que una CalleDto de un distrito diferente a "ESTE" se ignora (devuelve null)
        CalleDto dto = new CalleDto();
        dto.setNombreDistrito("NORTE"); // Distrito incorrecto

        Calle calle = processor.process(dto); // Procesa el DTO

        assertNull(calle); // Asegura que el resultado es nulo
    }

    @Test
    void procesaCalleConUltimoNumTramoNoNumerico() throws Exception {
        CalleDto dto = new CalleDto();
        dto.setCodigoCalle(002);
        dto.setTipoVia("Avenida");
        dto.setNombreCalle("Diagonal");
        dto.setBarrio("Eixample");
        dto.setCodigoDistrito(02);
        dto.setNombreDistrito("ESTE");
        dto.setPrimerNumTramo(5);
        dto.setUltimoNumTramo(100);

        Calle calle = processor.process(dto);

        assertNotNull(calle); // El registro del distrito "ESTE" debe pasar el filtro inicial
        assertEquals(002, calle.getCodigoCalle());
        assertNull(calle.getUltimoNumTramo()); // El ultimoNumTramo no numérico debe ser tratado como null
    }
}