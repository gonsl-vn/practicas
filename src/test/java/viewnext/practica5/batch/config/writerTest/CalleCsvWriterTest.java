package viewnext.practica5.batch.config.writerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import viewnext.practica5.batch.config.writer.CalleCsvWriter;
import viewnext.practica5.model.Calle;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class CalleCsvWriterTest {

    private DataSource dataSource; // Mock del DataSource
    private CalleCsvWriter calleCsvWriter; // Instancia del escritor CSV de Calles a probar

    @BeforeEach
    void setUp() {
        dataSource = mock(DataSource.class); // Crea un mock del DataSource
        calleCsvWriter = new CalleCsvWriter(dataSource); // Crea una instancia del escritor con el DataSource mockeado
    }

    @Test
    void testCsvWriter() {
        // Prueba la creación del FlatFileItemWriter para escribir a un archivo CSV
        FlatFileItemWriter<Calle> writer = calleCsvWriter.writer();
        assertNotNull(writer); // Asegura que el escritor se creó correctamente
    }

    @Test
    void testJdbcWriter() {
        // Prueba la creación del JdbcBatchItemWriter para escribir a la base de datos
        JdbcBatchItemWriter<Calle> writer = calleCsvWriter.calleWriterCompleto();
        assertNotNull(writer); // Asegura que el escritor se creó correctamente
    }
}