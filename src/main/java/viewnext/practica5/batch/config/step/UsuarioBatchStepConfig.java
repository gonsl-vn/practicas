package viewnext.practica5.batch.config.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import viewnext.practica5.batch.config.processor.UsuarioCsvProcessorConfig;
import viewnext.practica5.batch.config.reader.UsuarioCsvReaderConfig;
import viewnext.practica5.batch.config.writer.UsuarioCsvWriterConfig;
import viewnext.practica5.dto.UsuarioDTO;

@Configuration
public class UsuarioBatchStepConfig {

    private final StepBuilderFactory stepBuilderFactory; // Steps de Spring Batch
    private final UsuarioCsvReaderConfig usuarioCsvReaderConfig; // Configuración del lector de archivos CSV para Usuarios
    private final UsuarioCsvProcessorConfig usuarioCsvProcessorConfig; // Configuración del procesador de Usuarios
    private final UsuarioCsvWriterConfig usuarioCsvWriterConfig; // Configuración del escritor de Usuarios

    public UsuarioBatchStepConfig(StepBuilderFactory stepBuilderFactory, UsuarioCsvReaderConfig usuarioCsvReaderConfig,
            UsuarioCsvProcessorConfig usuarioCsvProcessorConfig, UsuarioCsvWriterConfig usuarioCsvWriterConfig) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.usuarioCsvReaderConfig = usuarioCsvReaderConfig;
        this.usuarioCsvProcessorConfig = usuarioCsvProcessorConfig;
        this.usuarioCsvWriterConfig = usuarioCsvWriterConfig;
    }

    @Bean
    public Step importarUsuariosStep() {
        // Define un nuevo Step llamado "importarUsuariosStep"
        return stepBuilderFactory.get("importarUsuariosStep")
                // Define el tamaño del chunk
                .<UsuarioDTO, UsuarioDTO>chunk(10)
                // Asigna el lector de Usuarios configurado
                .reader(usuarioCsvReaderConfig.usuarioReader())
                // Asigna el procesador de Usuarios configurado
                .processor(usuarioCsvProcessorConfig.usuarioProcessor())
                // Asigna el escritor de Usuarios configurado
                .writer(usuarioCsvWriterConfig.usuarioWriter())
                // Construye y retorna el Step configurado
                .build();
    }
}