package viewnext.practica5.batch.config.listenerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import viewnext.practica5.batch.config.listener.FiltrarCallesJobListener;

import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * The type Filtrar calles job listener test.
 */
public class FiltrarCallesJobListenerTest {

    @Mock
    private JobExecution jobExecution;

    private FiltrarCallesJobListener listener;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listener = new FiltrarCallesJobListener();
    }

    /**
     * After job imprime excepciones si fallo.
     */
    @Test
    void afterJob_imprimeExcepcionesSiFallo() {
        when(jobExecution.getStatus()).thenReturn(BatchStatus.FAILED);
        when(jobExecution.getAllFailureExceptions()).thenReturn(
                Collections.singletonList(new RuntimeException("Error")));

        listener.afterJob(jobExecution);

        verify(jobExecution).getStatus();
        verify(jobExecution).getAllFailureExceptions();
    }

    /**
     * After job no hace nada si no fallo.
     */
    @Test
    void afterJob_noHaceNadaSiNoFallo() {
        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);

        listener.afterJob(jobExecution);

        verify(jobExecution).getStatus();
        verify(jobExecution, never()).getAllFailureExceptions();
    }

    /**
     * Before job no hace nada.
     */
    @Test
    void beforeJob_noHaceNada() {
        listener.beforeJob(jobExecution);
        verifyNoInteractions(jobExecution);
    }
}
