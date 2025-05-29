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
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import viewnext.practica5.batch.config.job.GenerarDistritosJobConfig;
import viewnext.practica5.batch.config.processor.DistritoPassthroughProcessor;
import viewnext.practica5.batch.config.reader.DistritoResumenReader;
import viewnext.practica5.batch.config.writer.DistritoWriter;
import viewnext.practica5.model.Distrito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * The type Generar distritos job config test.
 */
@ExtendWith(MockitoExtension.class)
class GenerarDistritosJobConfigTest {

    @Mock
    private JobBuilderFactory jobBuilderFactory;

    @Mock
    private StepBuilderFactory stepBuilderFactory;

    @Mock
    private DistritoResumenReader distritoResumenReader;

    @Mock
    private DistritoPassthroughProcessor distritoPassthroughProcessor;

    @Mock
    private DistritoWriter distritoWriter;

    @Mock
    private JobBuilder jobBuilder;

    @Mock
    private SimpleJobBuilder simpleJobBuilder;

    @Mock
    private StepBuilder stepBuilder;

    private SimpleStepBuilder<Distrito, Distrito> simpleStepBuilder;

    @InjectMocks
    private GenerarDistritosJobConfig config;

    private Step stepReal;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        @SuppressWarnings("unchecked") SimpleStepBuilder<Distrito, Distrito> simpleStepBuilderMock = mock(
                SimpleStepBuilder.class);
        simpleStepBuilder = simpleStepBuilderMock;

        TaskletStep taskletStepMock = mock(TaskletStep.class);

        when(stepBuilderFactory.get("generarDistritosStep")).thenReturn(stepBuilder);
        when(stepBuilder.<Distrito, Distrito>chunk(10)).thenReturn(simpleStepBuilder);

        lenient().when(simpleStepBuilder.reader(distritoResumenReader)).thenReturn(simpleStepBuilder);
        lenient().when(simpleStepBuilder.processor(distritoPassthroughProcessor)).thenReturn(simpleStepBuilder);
        lenient().when(simpleStepBuilder.writer(distritoWriter)).thenReturn(simpleStepBuilder);
        lenient().when(simpleStepBuilder.build()).thenReturn(taskletStepMock);

        stepReal = config.generarDistritosStep();

        when(jobBuilderFactory.get("generarDistritosJob")).thenReturn(jobBuilder); // IMPORTANTE
        when(jobBuilder.incrementer(any(RunIdIncrementer.class))).thenReturn(jobBuilder);
        when(jobBuilder.start(stepReal)).thenReturn(simpleJobBuilder);
        when(simpleJobBuilder.build()).thenReturn(mock(Job.class));
    }

    /**
     * Test generar distritos job.
     */
    @Test
    void testGenerarDistritosJob() {
        Job result = config.generarDistritosJob();
        assertNotNull(result);
        verify(jobBuilderFactory).get("generarDistritosJob");
        verify(jobBuilder).incrementer(any(RunIdIncrementer.class));
        verify(jobBuilder).start(stepReal);
        verify(simpleJobBuilder).build();
    }
}
