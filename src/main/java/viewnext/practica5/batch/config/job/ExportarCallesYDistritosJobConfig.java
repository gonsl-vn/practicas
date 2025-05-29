package viewnext.practica5.batch.config.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import viewnext.practica5.batch.config.step.ExportarCallesStepConfig;
import viewnext.practica5.batch.config.step.ExportarDistritosStepConfig;

/**
 * The type Exportar calles y distritos job config.
 */
@Configuration
@RequiredArgsConstructor
public class ExportarCallesYDistritosJobConfig {

    private final JobBuilderFactory jobBuilderFactory; // Construir jobs de Spring Batch
    private final ExportarCallesStepConfig exportarCallesStepConfig; // Step para exportar calles
    private final ExportarDistritosStepConfig exportarDistritosStepConfig; // step para exportar distritos

    /**
     * Exportar calles y distritos job job.
     *
     * @return the job
     */
    @Bean
    public Job exportarCallesYDistritosJob() {
        return jobBuilderFactory.get("exportarCallesYDistritosJob")
                // Permite ejecutar el trabajo varias veces con un ID único
                .incrementer(new RunIdIncrementer())
                // Define el primer step a ejecutar: exportar calles
                .start(exportarCallesStepConfig.exportarCallesStep())
                // Define el siguiente step a ejecutar DESPUÉS del anterior: exportar distritos
                .next(exportarDistritosStepConfig.exportarDistritosStep())
                // Construye y finaliza la configuración del trabajo
                .build();
    }
}