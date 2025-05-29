package viewnext.practica5.batch.config.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import viewnext.practica5.batch.config.listener.CalleSkipListener;
import viewnext.practica5.batch.config.processor.CalleProcessorSinFiltro;
import viewnext.practica5.batch.config.reader.CalleUnMillonReader;
import viewnext.practica5.batch.config.writer.CalleWriterUnMillon;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

import javax.sql.DataSource;
import java.net.BindException;

/**
 * The type Importar calles un millon step config.
 */
@Configuration
@RequiredArgsConstructor
public class ImportarCallesUnMillonStepConfig {

    private final StepBuilderFactory stepBuilderFactory; // Steps de Spring Batch
    private final DataSource dataSource; // DataSource para la conexión a la base de datos
    private final CalleUnMillonReader calleUnMillonReader; // Lector optimizado para leer el archivo grande de Calles
    private final CalleProcessorSinFiltro calleProcessorSinFiltro; // Procesador de CalleDto a Calle
    private final CalleWriterUnMillon calleWriterUnMillon; // Escritor optimizado para escribir Calles a la base de datos
    private final CalleSkipListener calleSkipListener; // Listener para manejar registros omitidos durante el procesamiento
    private final TaskExecutor taskExecutor; // Executor de tareas para habilitar el procesamiento multihilo

    /**
     * Importar calles paso normal step.
     *
     * @return the step
     */
    @Bean
    public Step importarCallesPasoNormal() {
        // Define un nuevo Step llamado "importarCallesPasoNormal"
        return stepBuilderFactory.get("importarCallesPasoNormal")
                // Define el tamaño del chunk
                .<CalleDto, Calle>chunk(1_000_000)
                // Asigna el lector configurado para el archivo grande
                .reader(calleUnMillonReader)
                // Asigna el procesador configurado
                .processor(calleProcessorSinFiltro)
                // Asigna el escritor configurado, pasando el DataSource
                .writer(calleWriterUnMillon.writer(dataSource))
                // Configura la tolerancia a fallos
                .faultTolerant()
                // Especifica los tipos de excepciones que se deben omitir
                .skip(org.springframework.batch.item.file.FlatFileParseException.class).skip(BindException.class)
                // Define el límite de omisiones permitidas
                .skipLimit(1000)
                // Asigna el listener para manejar los registros omitidos
                .listener(calleSkipListener)
                // Construye y retorna el Step configurado
                .build();
    }

    /**
     * Importar calles paso multihilo step.
     *
     * @return the step
     */
    @Bean
    public Step importarCallesPasoMultihilo() {
        // Define un nuevo Step llamado "importarCallesPasoMultihilo"
        return stepBuilderFactory.get("importarCallesPasoMultihilo")
                // Define el tamaño del chunk para este paso
                .<CalleDto, Calle>chunk(1_000_000)
                // Asigna el mismo lector configurado
                .reader(calleUnMillonReader)
                // Asigna el mismo procesador configurado
                .processor(calleProcessorSinFiltro)
                // Asigna el mismo escritor configurado
                .writer(calleWriterUnMillon.writer(dataSource))
                // Configura la tolerancia a fallos
                .faultTolerant().skip(org.springframework.batch.item.file.FlatFileParseException.class)
                .skip(BindException.class).skipLimit(1000)
                // Asigna el mismo listener para los registros omitidos
                .listener(calleSkipListener)
                // Configura el uso de un TaskExecutor para paralelizar el procesamiento
                .taskExecutor(taskExecutor)
                // Define el límite de hilos concurrentes
                .throttleLimit(10)
                // Construye y retorna el Step configurado para procesamiento multihilo
                .build();
    }
}