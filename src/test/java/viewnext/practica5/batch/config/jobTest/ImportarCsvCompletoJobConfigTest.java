package viewnext.practica5.batch.config.jobTest;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import viewnext.practica5.batch.config.job.ImportarCsvCompletoJobConfig;
import viewnext.practica5.batch.config.step.ImportarCsvCompletoStepConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ImportarCsvCompletoJobConfigTest {

    @Test
    void testImportarCsvCompletoJob() {
        // Construye Jobs de Spring Batch
        JobBuilderFactory jobBuilderFactory = mock(JobBuilderFactory.class);
        // Step para importar el CSV completo
        ImportarCsvCompletoStepConfig stepConfig = mock(ImportarCsvCompletoStepConfig.class);
        // Step de Spring Batch
        Step stepMock = mock(Step.class);

        // Configura el mock de ImportarCsvCompletoStepConfig para que devuelva el Step simulado
        when(stepConfig.importarCsvCompletoStep()).thenReturn(stepMock);

        // Crea una instancia de la clase de configuración del Job a probar,
        // pasando las dependencias mockeadas en el constructor
        ImportarCsvCompletoJobConfig config = new ImportarCsvCompletoJobConfig(jobBuilderFactory, stepConfig);

        // Asegura que la instancia de la configuración se ha creado correctamente
        assertNotNull(config);
    }
}