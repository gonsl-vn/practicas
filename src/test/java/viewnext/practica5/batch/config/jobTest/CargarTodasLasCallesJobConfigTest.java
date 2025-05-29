package viewnext.practica5.batch.config.jobTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import viewnext.practica5.batch.config.job.CargarTodasLasCallesJobConfig;
import viewnext.practica5.batch.config.step.CargarCallesStepConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * The type Cargar todas las calles job config test.
 */
@ExtendWith(MockitoExtension.class)
class CargarTodasLasCallesJobConfigTest {

    @Mock
    private JobBuilderFactory jobBuilderFactory;

    @Mock
    private CargarCallesStepConfig cargarCallesStepConfig;

    @Mock
    private Step cargarCallesStep;

    @Mock
    private JobBuilder jobBuilder;

    @Mock
    private SimpleJobBuilder simpleJobBuilder;

    @Mock
    private Job job;

    @InjectMocks
    private CargarTodasLasCallesJobConfig jobConfig;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        when(cargarCallesStepConfig.cargarCallesStep()).thenReturn(cargarCallesStep);
        when(jobBuilderFactory.get("cargarTodasLasCallesJob")).thenReturn(jobBuilder);
        when(jobBuilder.incrementer(any())).thenReturn(jobBuilder);
        when(jobBuilder.start(cargarCallesStep)).thenReturn(simpleJobBuilder);
        when(simpleJobBuilder.build()).thenReturn(job);
    }

    /**
     * Test cargar todas las calles job.
     */
    @Test
    void testCargarTodasLasCallesJob() {
        Job result = jobConfig.cargarTodasLasCallesJob();

        assertNotNull(result);
        verify(jobBuilderFactory).get("cargarTodasLasCallesJob");
        verify(cargarCallesStepConfig).cargarCallesStep();
        verify(jobBuilder).start(cargarCallesStep);
        verify(simpleJobBuilder).build();
    }
}
