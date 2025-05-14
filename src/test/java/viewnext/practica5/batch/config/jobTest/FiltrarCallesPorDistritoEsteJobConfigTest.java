package viewnext.practica5.batch.config.jobTest;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import viewnext.practica5.batch.config.job.FiltrarCallesPorDistritoEsteJobConfig;
import viewnext.practica5.batch.config.step.FiltrarCallesStepConfig;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class FiltrarCallesPorDistritoEsteJobConfigTest {

    @Test
    void testFiltrarCallesPorDistritoEsteJobSimple() {
        // Construye Jobs de Spring Batch
        JobBuilderFactory jobBuilderFactory = mock(JobBuilderFactory.class);
        // Step para filtrar calles
        FiltrarCallesStepConfig filtrarCallesStepConfig = mock(FiltrarCallesStepConfig.class);

        // Crea una instancia de la clase de configuración del Job a probar,
        // pasando las dependencias mockeadas en el constructor
        FiltrarCallesPorDistritoEsteJobConfig config = new FiltrarCallesPorDistritoEsteJobConfig(jobBuilderFactory,
                filtrarCallesStepConfig);

        // Asegura que la instancia de la configuración se ha creado correctamente (no es nula)
        assertNotNull(config);
    }

}