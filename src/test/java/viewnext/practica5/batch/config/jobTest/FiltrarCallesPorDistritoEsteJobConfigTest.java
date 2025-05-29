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
import viewnext.practica5.batch.config.job.FiltrarCallesPorDistritoEsteJobConfig;
import viewnext.practica5.batch.config.step.FiltrarCallesStepConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * The type Filtrar calles por distrito este job config test.
 */
@ExtendWith(MockitoExtension.class)
class FiltrarCallesPorDistritoEsteJobConfigTest {

    @Mock
    private JobBuilderFactory jobBuilderFactory;

    @Mock
    private FiltrarCallesStepConfig filtrarCallesStepConfig;

    @Mock
    private Step filtrarCallesStep;

    @Mock
    private JobBuilder jobBuilder;

    @Mock
    private SimpleJobBuilder simpleJobBuilder;

    @Mock
    private Job job;

    @InjectMocks
    private FiltrarCallesPorDistritoEsteJobConfig jobConfig;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        when(jobBuilderFactory.get("filtrarCallesPorEsteJob")).thenReturn(jobBuilder);
        when(jobBuilder.incrementer(any())).thenReturn(jobBuilder);
        when(filtrarCallesStepConfig.filtrarCallesStep()).thenReturn(filtrarCallesStep);
        when(jobBuilder.start(filtrarCallesStep)).thenReturn(simpleJobBuilder);
        when(simpleJobBuilder.build()).thenReturn(job);
    }

    /**
     * Test filtrar calles por este job.
     */
    @Test
    void testFiltrarCallesPorEsteJob() {
        Job result = jobConfig.filtrarCallesPorEsteJob();

        assertNotNull(result);
        verify(jobBuilderFactory).get("filtrarCallesPorEsteJob");
        verify(filtrarCallesStepConfig).filtrarCallesStep();
        verify(jobBuilder).start(filtrarCallesStep);
        verify(simpleJobBuilder).build();
    }
}
