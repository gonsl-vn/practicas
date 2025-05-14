package viewnext.practica5.batch.config.readerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import viewnext.practica5.batch.config.reader.CalleCsvReader;
import viewnext.practica5.dto.CalleDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CalleCsvReaderTest {

    private CalleCsvReader calleCsvReader; // Instancia del lector CSV de Calles a probar

    @BeforeEach
    void setUp() {
        calleCsvReader = new CalleCsvReader(); // Crea una instancia del lector CSV de Calles
    }

    @Test
    void testReader() throws Exception {
        // Prueba la configuración del lector de Calles
        FlatFileItemReader<CalleDto> reader = calleCsvReader.reader();

        // Creamos un ExecutionContext vacío necesario para abrir el reader
        ExecutionContext executionContext = new ExecutionContext();
        reader.open(executionContext);

        // Leemos la primera línea del archivo
        CalleDto calle = reader.read();

        // Verificamos que se leyó un objeto CalleDto
        assertNotNull(calle);
        // Verificamos algunos de los valores de la primera línea
        assertEquals("AARON", calle.getNombreCalle());
        assertEquals("CIUDAD JARDIN", calle.getNombreDistrito());

        // Cerramos el reader después de la prueba
        reader.close();
    }

    @Test
    void testCsvCompletoReader() throws Exception {
        // Prueba la configuración del lector de Calles para el archivo CSV completo
        FlatFileItemReader<CalleDto> reader = calleCsvReader.csvCompletoReader();

        ExecutionContext executionContext = new ExecutionContext();
        reader.open(executionContext);

        CalleDto calle = reader.read();

        assertNotNull(calle);
        assertEquals("AARON", calle.getNombreCalle());
        assertEquals("CIUDAD JARDIN", calle.getNombreDistrito());

        reader.close();
    }
}