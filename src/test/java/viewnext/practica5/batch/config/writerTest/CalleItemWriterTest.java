package viewnext.practica5.batch.config.writerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import viewnext.practica5.batch.config.writer.CalleItemWriter;
import viewnext.practica5.model.Calle;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class CalleItemWriterTest {

    private DataSource dataSource; // Mock del DataSource
    private CalleItemWriter calleItemWriter; // Instancia del escritor de Calles a probar

    @BeforeEach
    void setUp() {
        dataSource = mock(DataSource.class); // Crea un mock del DataSource
        calleItemWriter = new CalleItemWriter(dataSource); // Crea una instancia del escritor con el DataSource mockeado
    }

    @Test
    void testWriter() {
        // Prueba la creación del JdbcBatchItemWriter para escribir objetos Calle a la base de datos
        JdbcBatchItemWriter<Calle> writer = calleItemWriter.writer();

        assertNotNull(writer); // Asegura que el escritor se creó correctamente (no es nulo)
    }
}