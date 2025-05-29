package viewnext.practica5.batch.config.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import viewnext.practica5.batch.config.reader.CalleReader;
import viewnext.practica5.batch.config.writer.CalleCsvWriter;
import viewnext.practica5.model.Calle;

/**
 * The type Exportar calles step config.
 */
@Configuration
public class ExportarCallesStepConfig {

    private final StepBuilderFactory stepBuilderFactory; // Steps de Spring Batch
    private final CalleReader calleReader; // Lector de objetos Calle desde la base de datos
    private final CalleCsvWriter calleCsvWriter; // Escritor de objetos Calle a un archivo CSV

    /**
     * Instantiates a new Exportar calles step config.
     *
     * @param stepBuilderFactory
     *         the step builder factory
     * @param calleReader
     *         the calle reader
     * @param calleCsvWriter
     *         the calle csv writer
     */
    public ExportarCallesStepConfig(StepBuilderFactory stepBuilderFactory, CalleReader calleReader,
            CalleCsvWriter calleCsvWriter) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.calleReader = calleReader;
        this.calleCsvWriter = calleCsvWriter;
    }

    /**
     * Exportar calles step step.
     *
     * @return the step
     */
    @Bean
    public Step exportarCallesStep() {
        // Define un nuevo Step llamado "exportarCallesStep"
        return stepBuilderFactory.get("exportarCallesStep")
                // Define el tamaño del chunk
                .<Calle, Calle>chunk(10)
                // Asigna el lector configurado
                .reader(calleReader.reader())
                // Asigna el escritor configurado
                .writer(calleCsvWriter.writer())
                // Construye y retorna el Step configurado
                .build();
    }
}