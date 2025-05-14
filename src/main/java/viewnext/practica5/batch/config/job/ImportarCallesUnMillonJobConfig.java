package viewnext.practica5.batch.config.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import viewnext.practica5.batch.config.step.ImportarCallesUnMillonStepConfig;

@Configuration
@RequiredArgsConstructor
public class ImportarCallesUnMillonJobConfig {

    private final JobBuilderFactory jobBuilderFactory; // Jobs de Spring Batch
    private final ImportarCallesUnMillonStepConfig importarCallesUnMillonStepConfig; // Step para importar calles

    @Bean
    public Job importarCallesUnMillonJob() {
        return jobBuilderFactory.get("importarCallesUnMillonJob")
                // Permite ejecutar el Job varias veces incrementando un ID
                .incrementer(new RunIdIncrementer())
                // Define el primer Step del Job (importar calles de forma normal)
                .start(importarCallesUnMillonStepConfig.importarCallesPasoNormal())
                // Siguiente Step del job (importar calles de forma multihilo)
                .next(importarCallesUnMillonStepConfig.importarCallesPasoMultihilo())
                // Construye y retorna el Job configurado
                .build();
    }
}