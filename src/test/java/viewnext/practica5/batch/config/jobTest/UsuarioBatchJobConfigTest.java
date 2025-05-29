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
import viewnext.practica5.batch.config.job.UsuarioBatchJobConfig;
import viewnext.practica5.batch.config.step.UsuarioBatchStepConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * The type Usuario batch job config test.
 */
@ExtendWith(MockitoExtension.class)
class UsuarioBatchJobConfigTest {

    @Mock
    private JobBuilderFactory jobBuilderFactory;

    @Mock
    private UsuarioBatchStepConfig usuarioBatchStepConfig;

    @Mock
    private JobBuilder jobBuilder;

    @Mock
    private SimpleJobBuilder simpleJobBuilder;

    @Mock
    private Step importarUsuariosStep;

    @InjectMocks
    private UsuarioBatchJobConfig jobConfig;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        when(jobBuilderFactory.get("importarUsuariosJob")).thenReturn(jobBuilder);
        when(jobBuilder.incrementer(any(RunIdIncrementer.class))).thenReturn(jobBuilder);

        when(usuarioBatchStepConfig.importarUsuariosStep()).thenReturn(importarUsuariosStep);

        when(jobBuilder.start(importarUsuariosStep)).thenReturn(simpleJobBuilder);
        when(simpleJobBuilder.build()).thenReturn(mock(Job.class));
    }

    /**
     * Test importar usuarios job.
     */
    @Test
    void testImportarUsuariosJob() {
        Job job = jobConfig.importarUsuariosJob();
        assertNotNull(job);

        verify(jobBuilderFactory).get("importarUsuariosJob");
        verify(jobBuilder).incrementer(any(RunIdIncrementer.class));
        verify(usuarioBatchStepConfig).importarUsuariosStep();
        verify(jobBuilder).start(importarUsuariosStep);
        verify(simpleJobBuilder).build();
    }
}
