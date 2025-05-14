package viewnext.practica5.batch.config.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import viewnext.practica5.batch.config.listener.FiltrarCallesJobListener;
import viewnext.practica5.batch.config.processor.DistritoEsteFilterProcessor;
import viewnext.practica5.batch.config.reader.CalleCsvReader;
import viewnext.practica5.batch.config.writer.CalleItemWriter;
import viewnext.practica5.dto.CalleDto;
import viewnext.practica5.model.Calle;

@Configuration
public class FiltrarCallesStepConfig {

    private final StepBuilderFactory stepBuilderFactory; // Steps de Spring Batch
    private final CalleCsvReader calleCsvReader; // Lector de archivos CSV para objetos CalleDto
    private final DistritoEsteFilterProcessor distritoEsteFilterProcessor; // Procesador para filtrar Calles por el distrito "ESTE"
    private final CalleItemWriter calleItemWriter; // Escritor de objetos Calle a la base de datos

    public FiltrarCallesStepConfig(StepBuilderFactory stepBuilderFactory, CalleCsvReader calleCsvReader,
            DistritoEsteFilterProcessor distritoEsteFilterProcessor, CalleItemWriter calleItemWriter) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.calleCsvReader = calleCsvReader;
        this.distritoEsteFilterProcessor = distritoEsteFilterProcessor;
        this.calleItemWriter = calleItemWriter;
    }

    @Bean
    public Step filtrarCallesStep() {
        // Define un nuevo Step llamado "filtrarCallesStep"
        return stepBuilderFactory.get("filtrarCallesStep")
                // Define el tamaño del chunk
                .<CalleDto, Calle>chunk(10)
                // Asigna el lector configurado
                .reader(calleCsvReader.reader())
                // Asigna el procesador configurado para filtrar por distrito
                .processor(distritoEsteFilterProcessor)
                // Asigna el escritor configurado
                .writer(calleItemWriter.writer())
                // Configura la tolerancia a fallos
                .faultTolerant()
                // Especifica qué tipo de excepción se debe omitir
                .skip(Exception.class)
                // Define el límite de omisiones permitidas
                .skipLimit(100)
                // Asigna un listener para este Step
                .listener(new FiltrarCallesJobListener())
                // Construye y retorna el Step configurado
                .build();
    }
}