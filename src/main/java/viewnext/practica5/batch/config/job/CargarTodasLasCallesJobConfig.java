package viewnext.practica5.batch.config.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import viewnext.practica5.batch.config.step.CargarCallesStepConfig;

@Configuration
@RequiredArgsConstructor
public class CargarTodasLasCallesJobConfig {

    private final JobBuilderFactory jobBuilderFactory; // Construir jobs de Spring Batch
    private final CargarCallesStepConfig cargarCallesStepConfig; // Step para cargar calles

    @Bean
    public Job cargarTodasLasCallesJob() {
        return jobBuilderFactory.get("cargarTodasLasCallesJob")
                // Permite ejecutar el job varias veces incrementando un ID
                .incrementer(new RunIdIncrementer())
                // Define el primer step del job
                .start(cargarCallesStepConfig.cargarCallesStep())
                // Construye el job
                .build();
    }
}