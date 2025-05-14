package viewnext.practica5.batch.config.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import viewnext.practica5.dto.UsuarioDTO;

@Configuration
public class UsuarioCsvReaderConfig {

    @Bean
    public FlatFileItemReader<UsuarioDTO> usuarioReader() {
        // Configura un lector de archivos planos para objetos UsuarioDTO
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(); // Define cómo se separan los campos en cada línea
        tokenizer.setDelimiter(";"); // El delimitador es el punto y coma
        tokenizer.setQuoteCharacter('"'); // Los campos pueden estar entre comillas
        tokenizer.setStrict(false); // No exige que todas las líneas tengan el mismo número de tokens
        tokenizer.setNames("nombre", "dni", "direccion", "ciudad", "codPostal", "importe",
                "numPedido"); // Nombres de las columnas en el archivo

        DefaultLineMapper<UsuarioDTO> lineMapper = new DefaultLineMapper<>(); // Mapea cada línea a un objeto UsuarioDTO
        lineMapper.setLineTokenizer(tokenizer); // Asigna el tokenizer al mapper
        lineMapper.setFieldSetMapper(
                new BeanWrapperFieldSetMapper<>() {{ // Utiliza BeanWrapper para asignar los valores a los campos del DTO
                    setTargetType(UsuarioDTO.class); // Especifica la clase destino para el mapeo: UsuarioDTO.class
                }});

        return new FlatFileItemReaderBuilder<UsuarioDTO>().name(
                        "usuarioReader") // Builder para configurar el FlatFileItemReader
                .resource(new ClassPathResource("ficheroUsuarios.csv")) // Ubicación del archivo CSV
                .linesToSkip(1) // Ignora la primera línea
                .lineMapper(lineMapper) // Asigna el LineMapper configurado
                .build(); // Construye el lector
    }
}