package viewnext.practica5.batch.config.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import viewnext.practica5.batch.config.processor.CalleProcessor;
import viewnext.practica5.batch.config.reader.CalleCsvReader;
import viewnext.practica5.batch.config.writer.CalleItemWriter;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

/**
 * The type Cargar calles step config.
 */
@Configuration
@RequiredArgsConstructor
public class CargarCallesStepConfig {

    private final StepBuilderFactory stepBuilderFactory; // Steps de Spring Batch
    private final CalleCsvReader calleCsvReader; // Lector de archivos CSV para objetos CalleDto
    private final CalleProcessor calleProcessor; // Procesador de objetos CalleDto a Calle
    private final CalleItemWriter calleItemWriter; // Escritor de objetos Calle a la base de datos

    /**
     * Cargar calles step step.
     *
     * @return the step
     */
    @Bean
    public Step cargarCallesStep() {
        // Define un nuevo Step llamado "cargarCallesStep"
        return stepBuilderFactory.get("cargarCallesStep")
                // Define el tamaño del chunk
                .<CalleDto, Calle>chunk(10)
                // Asigna el lector configurado
                .reader(calleCsvReader.reader())
                // Asigna el procesador configurado
                .processor(calleProcessor)
                // Asigna el escritor configurado
                .writer(calleItemWriter.writer())
                // Configura la tolerancia a fallos
                .faultTolerant()
                // Especifica qué tipo de excepción se debe omitir
                .skip(Exception.class)
                // Define el límite de omisiones permitidas
                .skipLimit(100)
                // Construye y retorna el Step configurado
                .build();
    }
}