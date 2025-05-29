package viewnext.practica5.batch.config.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import viewnext.practica5.batch.config.processor.CalleCsvProcessor;
import viewnext.practica5.batch.config.reader.CalleCsvReader;
import viewnext.practica5.batch.config.writer.CalleCsvWriter;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

/**
 * The type Importar csv completo step config.
 */
@Configuration
public class ImportarCsvCompletoStepConfig {

    private final StepBuilderFactory stepBuilderFactory; // Steps de Spring Batch
    private final CalleCsvReader calleCsvReader; // Lector de archivos CSV para objetos CalleDto
    private final CalleCsvProcessor calleCsvProcessor; // Procesador de objetos CalleDto a Calle
    private final CalleCsvWriter calleCsvWriter; // Escritor de objetos Calle a un archivo CSV

    /**
     * Instantiates a new Importar csv completo step config.
     *
     * @param stepBuilderFactory
     *         the step builder factory
     * @param calleCsvReader
     *         the calle csv reader
     * @param calleCsvProcessor
     *         the calle csv processor
     * @param calleCsvWriter
     *         the calle csv writer
     */
    public ImportarCsvCompletoStepConfig(StepBuilderFactory stepBuilderFactory, CalleCsvReader calleCsvReader,
            CalleCsvProcessor calleCsvProcessor, CalleCsvWriter calleCsvWriter) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.calleCsvReader = calleCsvReader;
        this.calleCsvProcessor = calleCsvProcessor;
        this.calleCsvWriter = calleCsvWriter;
    }

    /**
     * Importar csv completo step step.
     *
     * @return the step
     */
    @Bean
    public Step importarCsvCompletoStep() {
        // Define un nuevo Step llamado "importarCsvCompletoStep"
        return stepBuilderFactory.get("importarCsvCompletoStep")
                // Define el tamaño del chunk
                .<CalleDto, Calle>chunk(1000)
                // Asigna el lector configurado para leer el CSV completo
                .reader(calleCsvReader.csvCompletoReader())
                // Asigna el procesador configurado para el procesamiento del CSV completo
                .processor(calleCsvProcessor.csvCompletoProcessor())
                // Asigna el escritor configurado para escribir a un archivo CSV
                .writer(calleCsvWriter.calleWriterCompleto())
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