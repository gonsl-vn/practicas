package viewnext.practica5.batch.config.writerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.file.FlatFileItemWriter;
import viewnext.practica5.batch.config.writer.DistritoCsvWriter;
import viewnext.practica5.model.Distrito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DistritoCsvWriterTest {

    private DistritoCsvWriter distritoCsvWriter; // Instancia del escritor CSV de Distritos a probar

    @BeforeEach
    void setUp() {
        distritoCsvWriter = new DistritoCsvWriter(); // Crea una instancia del escritor CSV de Distritos
    }

    @Test
    void testWriterCreation() {
        // Prueba la creación del FlatFileItemWriter para escribir objetos Distrito a un archivo CSV
        FlatFileItemWriter<Distrito> writer = distritoCsvWriter.writer();

        assertNotNull(writer,
                "El writer no debe ser null"); // Asegura que el escritor se creó correctamente (no es nulo)
        assertEquals("distritoCsvWriter", writer.getName(),
                "El nombre del writer no es el esperado"); // Verifica que el nombre del writer sea el configurado

    }
}