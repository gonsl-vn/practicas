package viewnext.practica5.batch.config.listener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import viewnext.practica5.model.Distrito;
import viewnext.practica5.model.DistritoResumen;
import viewnext.practica5.repository.CalleRepository;
import viewnext.practica5.repository.DistritoRepository;
import viewnext.practica5.repository.DistritoResumenRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * The type Calle job execution listener test.
 */
class CalleJobExecutionListenerTest {

    @Mock
    private CalleRepository calleRepository;

    @Mock
    private DistritoResumenRepository resumenRepository;

    @Mock
    private DistritoRepository distritoRepository;

    @InjectMocks
    private CalleJobExecutionListener listener;

    @Mock
    private JobExecution jobExecution;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Before job logs execution id.
     */
    @Test
    void beforeJob_logsExecutionId() {
        when(jobExecution.getId()).thenReturn(123L);

        listener.beforeJob(jobExecution);

        verify(jobExecution).getId();
    }

    /**
     * After job when completed saves resumen and distritos.
     */
    @Test
    void afterJob_whenCompleted_savesResumenAndDistritos() {
        when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);

        long totalCalles = 5L;
        when(calleRepository.countByNombreDistrito("ESTE")).thenReturn(totalCalles);

        // Mock para resultados del conteo de viviendas por distrito
        List<Object[]> resultados = List.of(new Object[] { "Distrito1", 10L }, new Object[] { "Distrito2", 20L });
        when(calleRepository.countViviendasPorDistrito()).thenReturn(resultados);

        listener.afterJob(jobExecution);

        ArgumentCaptor<DistritoResumen> resumenCaptor = ArgumentCaptor.forClass(DistritoResumen.class);
        verify(resumenRepository).save(resumenCaptor.capture());
        DistritoResumen resumenGuardado = resumenCaptor.getValue();

        assertEquals("ESTE", resumenGuardado.getFiltroUsado());
        assertEquals(totalCalles, resumenGuardado.getNumeroRegistros());
        assertEquals(BatchStatus.COMPLETED.toString(), resumenGuardado.getEstadoBatch());
        assertNotNull(resumenGuardado.getTimestamp());

        ArgumentCaptor<Distrito> distritoCaptor = ArgumentCaptor.forClass(Distrito.class);
        verify(distritoRepository, times(2)).save(distritoCaptor.capture());

        List<Distrito> distritosGuardados = distritoCaptor.getAllValues();
        assertEquals("Distrito1", distritosGuardados.get(0).getNombreDistrito());
        assertEquals(10, distritosGuardados.get(0).getNumeroViviendas());
        assertEquals("Distrito2", distritosGuardados.get(1).getNombreDistrito());
        assertEquals(20, distritosGuardados.get(1).getNumeroViviendas());
    }

    /**
     * After job when not completed logs warning.
     */
    @Test
    void afterJob_whenNotCompleted_logsWarning() {
        when(jobExecution.getStatus()).thenReturn(BatchStatus.FAILED);

        listener.afterJob(jobExecution);

        verifyNoInteractions(calleRepository);
        verifyNoInteractions(resumenRepository);
        verifyNoInteractions(distritoRepository);
    }
}
