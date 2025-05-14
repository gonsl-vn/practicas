package viewnext.practica5.batch.config.processorTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import viewnext.practica5.batch.config.processor.DistritoEsteFilterProcessor;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

import static org.junit.jupiter.api.Assertions.*;

class DistritoEsteFilterProcessorTest {

    private DistritoEsteFilterProcessor processor; // Instancia del procesador de filtro a probar

    @BeforeEach
    void setUp() {
        processor = new DistritoEsteFilterProcessor(); // Crea una instancia del procesador de filtro
    }

    @Test
    void devuelveCalleSiDistritoEsEste() throws Exception {
        // Prueba que si el distrito de la CalleDto es "ESTE", se devuelve un objeto Calle
        CalleDto dto = new CalleDto();
        dto.setCodigoCalle(123);
        dto.setTipoVia("Calle");
        dto.setNombreCalle("Mayor");
        dto.setPrimerNumTramo(1);
        dto.setUltimoNumTramo(99);
        dto.setBarrio("Centro");
        dto.setCodigoDistrito(01);
        dto.setNombreDistrito("ESTE"); // Distrito correcto

        Calle result = processor.process(dto); // Procesa el DTO

        assertNotNull(result); // Asegura que el resultado no es nulo
        assertEquals(123, result.getCodigoCalle()); // Verifica que el código de calle se mapeó
        assertEquals("Mayor", result.getNombreCalle()); // Verifica que el nombre de calle se mapeó
    }

    @Test
    void devuelveNullSiDistritoNoEsEste() throws Exception {
        // Prueba que si el distrito de la CalleDto no es "ESTE", se devuelve null
        CalleDto dto = new CalleDto();
        dto.setNombreDistrito("OESTE"); // Distrito incorrecto

        Calle result = processor.process(dto); // Procesa el DTO

        assertNull(result); // Asegura que el resultado es nulo
    }

    @Test
    void devuelveNullSiDtoEsNull() throws Exception {
        // Prueba que si el CalleDto de entrada es nulo, se devuelve null
        Calle result = processor.process(null); // Procesa un DTO nulo
        assertNull(result); // Asegura que el resultado es nulo
    }

    @Test
    void devuelveNullSiNombreDistritoEsNull() throws Exception {
        // Prueba que si el nombre del distrito en el CalleDto es nulo, se devuelve null
        CalleDto dto = new CalleDto();
        dto.setNombreDistrito(null); // Nombre del distrito nulo

        Calle result = processor.process(dto); // Procesa el DTO

        assertNull(result); // Asegura que el resultado es nulo
    }
}