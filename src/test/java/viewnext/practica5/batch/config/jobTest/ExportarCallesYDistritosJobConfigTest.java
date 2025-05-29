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
import viewnext.practica5.batch.config.job.ExportarCallesYDistritosJobConfig;
import viewnext.practica5.batch.config.step.ExportarCallesStepConfig;
import viewnext.practica5.batch.config.step.ExportarDistritosStepConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * The type Exportar calles y distritos job config test.
 */
@ExtendWith(MockitoExtension.class)
class ExportarCallesYDistritosJobConfigTest {

    @Mock
    private JobBuilderFactory jobBuilderFactory;

    @Mock
    private ExportarCallesStepConfig exportarCallesStepConfig;

    @Mock
    private ExportarDistritosStepConfig exportarDistritosStepConfig;

    @Mock
    private Step exportarCallesStep;

    @Mock
    private Step exportarDistritosStep;

    @Mock
    private JobBuilder jobBuilder;

    @Mock
    private SimpleJobBuilder simpleJobBuilder;

    @Mock
    private Job job;

    @InjectMocks
    private ExportarCallesYDistritosJobConfig jobConfig;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        when(jobBuilderFactory.get("exportarCallesYDistritosJob")).thenReturn(jobBuilder);
        when(jobBuilder.incrementer(any())).thenReturn(jobBuilder);
        when(exportarCallesStepConfig.exportarCallesStep()).thenReturn(exportarCallesStep);
        when(exportarDistritosStepConfig.exportarDistritosStep()).thenReturn(exportarDistritosStep);
        when(jobBuilder.start(exportarCallesStep)).thenReturn(simpleJobBuilder);
        when(simpleJobBuilder.next(exportarDistritosStep)).thenReturn(simpleJobBuilder);
        when(simpleJobBuilder.build()).thenReturn(job);
    }

    /**
     * Test exportar calles y distritos job.
     */
    @Test
    void testExportarCallesYDistritosJob() {
        Job result = jobConfig.exportarCallesYDistritosJob();

        assertNotNull(result);
        verify(jobBuilderFactory).get("exportarCallesYDistritosJob");
        verify(exportarCallesStepConfig).exportarCallesStep();
        verify(exportarDistritosStepConfig).exportarDistritosStep();
        verify(jobBuilder).start(exportarCallesStep);
        verify(simpleJobBuilder).next(exportarDistritosStep);
        verify(simpleJobBuilder).build();
    }
}
