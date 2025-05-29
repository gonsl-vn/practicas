package viewnext.practica5.batch.config.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import viewnext.practica5.batch.config.step.FiltrarCallesStepConfig;

/**
 * The type Filtrar calles por distrito este job config.
 */
@Configuration
@RequiredArgsConstructor
public class FiltrarCallesPorDistritoEsteJobConfig {

    private final JobBuilderFactory jobBuilderFactory; // Jobs de Spring Batch
    private final FiltrarCallesStepConfig filtrarCallesStepConfig; // Step de filtrado

    /**
     * Filtrar calles por este job job.
     *
     * @return the job
     */
    @Bean
    public Job filtrarCallesPorEsteJob() {
        return jobBuilderFactory.get("filtrarCallesPorEsteJob")
                // Permite la ejecución varias veces
                .incrementer(new RunIdIncrementer())
                // Define el inicio del Job con el Step de filtrado de calles
                .start(filtrarCallesStepConfig.filtrarCallesStep())
                // Construye y retorna el Job configurado
                .build();
    }
}