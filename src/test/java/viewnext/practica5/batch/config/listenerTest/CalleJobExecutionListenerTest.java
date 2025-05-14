package viewnext.practica5.batch.config.listenerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import viewnext.practica5.batch.config.listener.CalleJobExecutionListener;
import viewnext.practica5.repository.CalleRepository;
import viewnext.practica5.repository.DistritoRepository;
import viewnext.practica5.repository.DistritoResumenRepository;

import java.util.Collections;

import static org.mockito.Mockito.*;

public class CalleJobExecutionListenerTest {

    private CalleRepository calleRepo; // Mock del repositorio de Calles
    private DistritoResumenRepository resumenRepo; // Mock del repositorio de Resúmenes de Distritos
    private DistritoRepository distritoRepo; // Mock del repositorio de Distritos
    private CalleJobExecutionListener listener; // Instancia del listener a probar

    @BeforeEach
    void preparar() {
        calleRepo = mock(CalleRepository.class); // Crea un mock del repositorio de Calles
        resumenRepo = mock(DistritoResumenRepository.class); // Crea un mock del repositorio de Resúmenes de Distritos
        distritoRepo = mock(DistritoRepository.class); // Crea un mock del repositorio de Distritos
        listener = new CalleJobExecutionListener(calleRepo, resumenRepo,
                distritoRepo); // Crea una instancia del listener con los mocks
    }

    @Test
    void antesDelJob_noFalla() {
        // Prueba que el método beforeJob se ejecuta sin lanzar excepciones
        JobExecution job = new JobExecution(1L); // Crea un objeto JobExecution simulado
        listener.beforeJob(job); // Llama al método beforeJob del listener
    }

    @Test
        // Indica que este método es una prueba JUnit
    void despuesDelJob_completado_guardarResumenYDistritos() {
        // Prueba que después de un Job exitoso, se guardan el resumen y la información de los distritos
        JobExecution job = new JobExecution(1L); // Crea un objeto JobExecution simulado
        job.setStatus(BatchStatus.COMPLETED); // Simula que el Job se completó exitosamente

        // Configura el comportamiento esperado de los mocks de los repositorios
        when(calleRepo.countByNombreDistrito("ESTE")).thenReturn(5L); // Cuando se cuenta por distrito "ESTE"
        when(calleRepo.countViviendasPorDistrito()).thenReturn(Collections.singletonList(new Object[] { "ESTE",
                100L })); // Cuando se cuentan viviendas por distrito, devuelve una lista con un distrito "ESTE" y 100 viviendas

        listener.afterJob(job); // Llama al método afterJob del listener

        // Verifica que se llamó al método save del repositorio de resúmenes una vez
        verify(resumenRepo, times(1)).save(any());
        // Verifica que se llamó al método save del repositorio de distritos una vez
        verify(distritoRepo, times(1)).save(any());
    }

    @Test
    void despuesDelJob_conError_noGuardaNada() {
        // Prueba que después de un Job con error, no se guarda ningún resumen ni información de distritos
        JobExecution job = new JobExecution(2L); // Crea un objeto JobExecution simulado
        job.setStatus(BatchStatus.FAILED); // Simula que el Job falló

        listener.afterJob(job); // Llama al método afterJob del listener

        // Verifica que el método save del repositorio de resúmenes nunca fue llamado
        verify(resumenRepo, never()).save(any());
        // Verifica que el método save del repositorio de distritos nunca fue llamado
        verify(distritoRepo, never()).save(any());
    }
}