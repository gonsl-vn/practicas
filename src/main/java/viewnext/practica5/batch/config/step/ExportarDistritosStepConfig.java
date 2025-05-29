package viewnext.practica5.batch.config.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import viewnext.practica5.batch.config.reader.DistritoReader;
import viewnext.practica5.batch.config.writer.DistritoCsvWriter;
import viewnext.practica5.model.Distrito;

/**
 * The type Exportar distritos step config.
 */
@Configuration
public class ExportarDistritosStepConfig {

    private final StepBuilderFactory stepBuilderFactory; // Steps de Spring Batch
    private final DistritoReader distritoReader; // Lector de objetos Distrito desde la base de datos (o donde se obtengan)
    private final DistritoCsvWriter distritoCsvWriter; // Escritor de objetos Distrito a un archivo CSV

    /**
     * Instantiates a new Exportar distritos step config.
     *
     * @param stepBuilderFactory
     *         the step builder factory
     * @param distritoReader
     *         the distrito reader
     * @param distritoCsvWriter
     *         the distrito csv writer
     */
    public ExportarDistritosStepConfig(StepBuilderFactory stepBuilderFactory, DistritoReader distritoReader,
            DistritoCsvWriter distritoCsvWriter) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.distritoReader = distritoReader;
        this.distritoCsvWriter = distritoCsvWriter;
    }

    /**
     * Exportar distritos step step.
     *
     * @return the step
     */
    @Bean
    public Step exportarDistritosStep() {
        // Define un nuevo Step llamado "exportarDistritosStep"
        return stepBuilderFactory.get("exportarDistritosStep")
                // Define el tamaño del chunk
                .<Distrito, Distrito>chunk(10)
                // Asigna el lector configurado
                .reader(distritoReader)
                // Asigna el escritor configurado
                .writer(distritoCsvWriter.writer())
                // Construye y retorna el Step configurado
                .build();
    }
}