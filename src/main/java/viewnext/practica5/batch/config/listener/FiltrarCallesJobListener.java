package viewnext.practica5.batch.config.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * The type Filtrar calles job listener.
 */
@Component
public class FiltrarCallesJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // Este método se ejecuta antes de que comience el Job
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // Este método se ejecuta después de que finaliza el Job
        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            // Comprueba si el estado del Job es FALLIDO
            System.out.println("Job failed: " + jobExecution.getAllFailureExceptions());
            // Si falló, imprime la lista de todas las excepciones que causaron el fallo
        }
    }
}