package viewnext.practica5.batch.config.jobTest;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import viewnext.practica5.batch.config.job.ExportarCallesYDistritosJobConfig;
import viewnext.practica5.batch.config.step.ExportarCallesStepConfig;
import viewnext.practica5.batch.config.step.ExportarDistritosStepConfig;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class ExportarCallesYDistritosJobConfigTest {

    @Test
    void testExportarCallesYDistritosJob() {
        // Construye Jobs de Spring Batch
        JobBuilderFactory factory = mock(JobBuilderFactory.class);
        // Step para exportar calles
        ExportarCallesStepConfig calles = mock(ExportarCallesStepConfig.class);
        // Step para exportar distritos
        ExportarDistritosStepConfig distritos = mock(ExportarDistritosStepConfig.class);

        // Crea una instancia de la clase de configuración del Job a probar,
        // pasando las dependencias mockeadas en el constructor
        ExportarCallesYDistritosJobConfig config = new ExportarCallesYDistritosJobConfig(factory, calles, distritos);

        // Asegura que la instancia de la configuración se ha creado correctamente
        assertNotNull(config);
        
    }
}