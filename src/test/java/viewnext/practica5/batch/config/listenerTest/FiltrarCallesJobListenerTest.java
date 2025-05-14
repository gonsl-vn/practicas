package viewnext.practica5.batch.config.listenerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import viewnext.practica5.batch.config.listener.FiltrarCallesJobListener;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class FiltrarCallesJobListenerTest {

    private FiltrarCallesJobListener listener; // Instancia del listener a probar

    @BeforeEach
    void setUp() {
        listener = new FiltrarCallesJobListener(); // Crea una instancia del listener
    }

    @Test
    void afterJob_jobFallido_noLanzaErrores() {
        // Prueba que el método afterJob se ejecuta sin errores cuando el Job falla
        JobExecution jobExecution = new JobExecution(1L); // Crea un objeto JobExecution simulado
        jobExecution.setStatus(BatchStatus.FAILED); // Simula que el Job falló
        jobExecution.addFailureException(
                new RuntimeException("Fallo simulado")); // Añade una excepción de fallo simulada

        assertDoesNotThrow(
                () -> listener.afterJob(jobExecution)); // Asegura que la llamada al método no lanza ninguna excepción
    }

    @Test
    void afterJob_jobExitoso_noHaceNada() {
        // Prueba que el método afterJob no realiza ninguna acción cuando el Job se completa exitosamente
        JobExecution jobExecution = new JobExecution(2L); // Crea un objeto JobExecution simulado
        jobExecution.setStatus(BatchStatus.COMPLETED); // Simula que el Job se completó exitosamente

        assertDoesNotThrow(
                () -> listener.afterJob(jobExecution)); // Asegura que la llamada al método no lanza ninguna excepción
    }

    @Test
    void beforeJob_noHaceNada() {
        // Prueba que el método beforeJob no realiza ninguna acción
        JobExecution jobExecution = new JobExecution(3L); // Crea un objeto JobExecution simulado

        assertDoesNotThrow(
                () -> listener.beforeJob(jobExecution)); // Asegura que la llamada al método no lanza ninguna excepción
    }
}