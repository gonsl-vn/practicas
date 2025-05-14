package viewnext.practica5.batch.config.jobTest;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import viewnext.practica5.batch.config.job.CargarTodasLasCallesJobConfig;
import viewnext.practica5.batch.config.step.CargarCallesStepConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CargarTodasLasCallesJobConfigTest {

    @Test
    void testJobCreado() {
        // Construye Jobs de Spring Batch
        JobBuilderFactory factory = mock(JobBuilderFactory.class);
        // Step para cargar calles
        CargarCallesStepConfig stepConfig = mock(CargarCallesStepConfig.class);

        // Step de Spring Batch
        Step step = mock(Step.class);
        // Configura el mock de CargarCallesStepConfig para que devuelva el Step simulado
        when(stepConfig.cargarCallesStep()).thenReturn(step);

        // Crea una instancia de la clase de configuración del Job a probar
        CargarTodasLasCallesJobConfig config = new CargarTodasLasCallesJobConfig(factory, stepConfig);

        // Asegura que la instancia de la configuración se ha creado correctamente
        assertNotNull(config);

    }
}