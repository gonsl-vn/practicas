package viewnext.practica5.batch.config.jobTest;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import viewnext.practica5.batch.config.job.UsuarioBatchJobConfig;
import viewnext.practica5.batch.config.step.UsuarioBatchStepConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class UsuarioBatchJobConfigTest {

    @Test
    void testUsuarioBatchJobConfig() {
        // Construye Jobs de Spring Batch
        JobBuilderFactory jobBuilderFactory = mock(JobBuilderFactory.class);
        // Step para el procesamiento de usuarios
        UsuarioBatchStepConfig usuarioBatchStepConfig = mock(UsuarioBatchStepConfig.class);

        // Crea una instancia de la clase de configuración del Job a probar,
        // pasando las dependencias mockeadas en el constructor
        UsuarioBatchJobConfig config = new UsuarioBatchJobConfig(jobBuilderFactory, usuarioBatchStepConfig);

        // Asegura que la instancia de la configuración se ha creado correctamente (no es nula)
        assertNotNull(config);
    }
}