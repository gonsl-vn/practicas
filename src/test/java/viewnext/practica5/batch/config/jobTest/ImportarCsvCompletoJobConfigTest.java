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
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import viewnext.practica5.batch.config.job.ImportarCsvCompletoJobConfig;
import viewnext.practica5.batch.config.step.ImportarCsvCompletoStepConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * The type Importar csv completo job config test.
 */
@ExtendWith(MockitoExtension.class)
class ImportarCsvCompletoJobConfigTest {

    @Mock
    private JobBuilderFactory jobBuilderFactory;

    @Mock
    private ImportarCsvCompletoStepConfig importarCsvCompletoStepConfig;

    @Mock
    private JobBuilder jobBuilder;

    @Mock
    private SimpleJobBuilder simpleJobBuilder;

    @Mock
    private Step importarCsvCompletoStep;

    @InjectMocks
    private ImportarCsvCompletoJobConfig jobConfig;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        when(jobBuilderFactory.get("importarCsvCompletoJob")).thenReturn(jobBuilder);
        when(jobBuilder.incrementer(any(RunIdIncrementer.class))).thenReturn(jobBuilder);

        when(importarCsvCompletoStepConfig.importarCsvCompletoStep()).thenReturn(importarCsvCompletoStep);

        when(jobBuilder.start(importarCsvCompletoStep)).thenReturn(simpleJobBuilder);
        when(simpleJobBuilder.build()).thenReturn(mock(Job.class));
    }

    /**
     * Test importar csv completo job.
     */
    @Test
    void testImportarCsvCompletoJob() {
        Job job = jobConfig.importarCsvCompletoJob();
        assertNotNull(job);

        verify(jobBuilderFactory).get("importarCsvCompletoJob");
        verify(jobBuilder).incrementer(any(RunIdIncrementer.class));
        verify(importarCsvCompletoStepConfig).importarCsvCompletoStep();
        verify(jobBuilder).start(importarCsvCompletoStep);
        verify(simpleJobBuilder).build();
    }
}
