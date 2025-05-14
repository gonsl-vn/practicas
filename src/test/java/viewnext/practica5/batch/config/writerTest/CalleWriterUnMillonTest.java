package viewnext.practica5.batch.config.writerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import viewnext.practica5.batch.config.writer.CalleWriterUnMillon;
import viewnext.practica5.model.Calle;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class CalleWriterUnMillonTest {

    private DataSource dataSource; // Mock del DataSource
    private CalleWriterUnMillon calleWriterUnMillon; // Instancia del escritor de Calles para un millón de registros a probar

    @BeforeEach
    void setUp() {
        dataSource = mock(DataSource.class); // Crea un mock del DataSource
        calleWriterUnMillon = new CalleWriterUnMillon(); // Crea una instancia del escritor
    }

    @Test
    void testWriterCreation() {
        // Prueba la creación del JdbcBatchItemWriter para escribir un millón de objetos Calle a la base de datos
        JdbcBatchItemWriter<Calle> writer = calleWriterUnMillon.writer(dataSource);

        assertNotNull(writer,
                "El writer no debería ser null"); // Asegura que el escritor se creó correctamente (no es nulo)
    }
}