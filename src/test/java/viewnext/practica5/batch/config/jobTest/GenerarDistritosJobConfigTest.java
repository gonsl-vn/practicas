package viewnext.practica5.batch.config.jobTest;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import viewnext.practica5.batch.config.job.GenerarDistritosJobConfig;
import viewnext.practica5.batch.config.processor.DistritoPassthroughProcessor;
import viewnext.practica5.batch.config.reader.DistritoResumenReader;
import viewnext.practica5.batch.config.writer.DistritoWriter;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class GenerarDistritosJobConfigTest {

    @Test
    void testGenerarDistritosJobConfig() {
        // Construye Jobs de Spring Batch
        JobBuilderFactory jobBuilderFactory = mock(JobBuilderFactory.class);
        // Lector de resúmenes de distritos
        DistritoResumenReader distritoResumenReader = mock(DistritoResumenReader.class);
        // Simula el procesador de distritos
        DistritoPassthroughProcessor distritoPassthroughProcessor = mock(DistritoPassthroughProcessor.class);
        // Simula el escritor de distritos
        DistritoWriter distritoWriter = mock(DistritoWriter.class);

        // Crea una instancia de la clase de configuración del Job a probar,
        // pasando las dependencias mockeadas en el constructor.
        GenerarDistritosJobConfig config = new GenerarDistritosJobConfig(jobBuilderFactory, null, distritoResumenReader,
                distritoPassthroughProcessor, distritoWriter);

        // Asegura que la instancia de la configuración se ha creado correctamente
        assertNotNull(config);
    }
}