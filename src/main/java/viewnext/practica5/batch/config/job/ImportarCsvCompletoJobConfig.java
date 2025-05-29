package viewnext.practica5.batch.config.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import viewnext.practica5.batch.config.step.ImportarCsvCompletoStepConfig;

/**
 * The type Importar csv completo job config.
 */
@Configuration
@RequiredArgsConstructor
public class ImportarCsvCompletoJobConfig {

    private final JobBuilderFactory jobBuilderFactory; // Jobs de Spring Batch
    private final ImportarCsvCompletoStepConfig importarCsvCompletoStepConfig; // Step para importar el CSV completo

    /**
     * Importar csv completo job job.
     *
     * @return the job
     */
    @Bean
    public Job importarCsvCompletoJob() {
        return jobBuilderFactory.get("importarCsvCompletoJob")
                // Permite ejecutar el Job varias veces incrementando un ID
                .incrementer(new RunIdIncrementer())
                // Define el único Step que se ejecutará en este Job
                .start(importarCsvCompletoStepConfig.importarCsvCompletoStep())
                // Construye y retorna el Job configurado
                .build();
    }
}