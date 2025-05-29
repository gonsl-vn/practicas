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
import viewnext.practica5.batch.config.job.ImportarCallesUnMillonJobConfig;
import viewnext.practica5.batch.config.step.ImportarCallesUnMillonStepConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * The type Importar calles un millon job config test.
 */
@ExtendWith(MockitoExtension.class)
class ImportarCallesUnMillonJobConfigTest {

    @Mock
    private JobBuilderFactory jobBuilderFactory;

    @Mock
    private ImportarCallesUnMillonStepConfig importarCallesUnMillonStepConfig;

    @Mock
    private JobBuilder jobBuilder;

    @Mock
    private SimpleJobBuilder simpleJobBuilder;

    @Mock
    private Step stepNormal;

    @Mock
    private Step stepMultihilo;

    @InjectMocks
    private ImportarCallesUnMillonJobConfig jobConfig;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        when(jobBuilderFactory.get("importarCallesUnMillonJob")).thenReturn(jobBuilder);
        when(jobBuilder.incrementer(any(RunIdIncrementer.class))).thenReturn(jobBuilder);
        when(importarCallesUnMillonStepConfig.importarCallesPasoNormal()).thenReturn(stepNormal);
        when(importarCallesUnMillonStepConfig.importarCallesPasoMultihilo()).thenReturn(stepMultihilo);

        when(jobBuilder.start(stepNormal)).thenReturn(simpleJobBuilder);
        when(simpleJobBuilder.next(stepMultihilo)).thenReturn(simpleJobBuilder);
        when(simpleJobBuilder.build()).thenReturn(mock(Job.class));
    }

    /**
     * Test importar calles un millon job.
     */
    @Test
    void testImportarCallesUnMillonJob() {
        Job job = jobConfig.importarCallesUnMillonJob();
        assertNotNull(job);

        verify(jobBuilderFactory).get("importarCallesUnMillonJob");
        verify(jobBuilder).incrementer(any(RunIdIncrementer.class));
        verify(importarCallesUnMillonStepConfig).importarCallesPasoNormal();
        verify(importarCallesUnMillonStepConfig).importarCallesPasoMultihilo();
        verify(jobBuilder).start(stepNormal);
        verify(simpleJobBuilder).next(stepMultihilo);
        verify(simpleJobBuilder).build();
    }
}
