package viewnext.practica5.batch.config.jobTest;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import viewnext.practica5.batch.config.job.ImportarCallesUnMillonJobConfig;
import viewnext.practica5.batch.config.step.ImportarCallesUnMillonStepConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ImportarCallesUnMillonJobConfigTest {

    @Test
    void testImportarCallesUnMillonJobSimple() {
        // Construye Jobs de Spring Batch
        JobBuilderFactory jobBuilderFactory = mock(JobBuilderFactory.class);
        // Step para importar un millón de calles
        ImportarCallesUnMillonStepConfig stepConfig = mock(ImportarCallesUnMillonStepConfig.class);

        // Simula los Steps individuales definidos en ImportarCallesUnMillonStepConfig
        var pasoNormal = mock(org.springframework.batch.core.Step.class);
        var pasoMultihilo = mock(org.springframework.batch.core.Step.class);

        // Configura el mock de ImportarCallesUnMillonStepConfig para que devuelva los Steps simulados
        when(stepConfig.importarCallesPasoNormal()).thenReturn(pasoNormal);
        when(stepConfig.importarCallesPasoMultihilo()).thenReturn(pasoMultihilo);

        // Crea una instancia de la clase de configuración del Job a probar,
        // pasando las dependencias mockeadas en el constructor
        ImportarCallesUnMillonJobConfig config = new ImportarCallesUnMillonJobConfig(jobBuilderFactory, stepConfig);

        // Asegura que la instancia de la configuración se ha creado correctamente (no es nula)
        assertNotNull(config);
    }
}