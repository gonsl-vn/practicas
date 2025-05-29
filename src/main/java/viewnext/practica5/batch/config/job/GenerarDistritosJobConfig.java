package viewnext.practica5.batch.config.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import viewnext.practica5.batch.config.processor.DistritoPassthroughProcessor;
import viewnext.practica5.batch.config.reader.DistritoResumenReader;
import viewnext.practica5.batch.config.writer.DistritoWriter;
import viewnext.practica5.model.Distrito;

/**
 * The type Generar distritos job config.
 */
@Configuration
@RequiredArgsConstructor
public class GenerarDistritosJobConfig {

    private final JobBuilderFactory jobBuilderFactory; // Jobs de Spring Batch
    private final StepBuilderFactory stepBuilderFactory; // Para construir Steps
    private final DistritoResumenReader distritoResumenReader; // Lector de datos de Distritos
    private final DistritoPassthroughProcessor distritoPassthroughProcessor; // Procesador que pasa los datos sin procesar
    private final DistritoWriter distritoWriter; // Escritor de los datos de Distritos

    /**
     * Generar distritos step step.
     *
     * @return the step
     */
    @Bean
    public Step generarDistritosStep() {
        return stepBuilderFactory.get("generarDistritosStep")
                // Procesa los datos en bloques de 10 elementos
                .<Distrito, Distrito>chunk(10)
                // Define el lector para este Step
                .reader(distritoResumenReader)
                // Define el procesador para este Step
                .processor(distritoPassthroughProcessor)
                // Define el escritor para este Step
                .writer(distritoWriter)
                // Construye y retorna el Step configurado
                .build();
    }

    /**
     * Generar distritos job job.
     *
     * @return the job
     */
    @Bean
    public Job generarDistritosJob() {
        return jobBuilderFactory.get("generarDistritosJob")
                // Permite la ejecución varias veces
                .incrementer(new RunIdIncrementer())
                // Define el Step que se ejecutará en este Job
                .start(generarDistritosStep())
                // Construye y retorna el Job configurado
                .build();
    }
}