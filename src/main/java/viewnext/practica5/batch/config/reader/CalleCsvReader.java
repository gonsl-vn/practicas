package viewnext.practica5.batch.config.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import viewnext.practica5.dto.CalleDto;

@Component
public class CalleCsvReader {

    public FlatFileItemReader<CalleDto> reader() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(); // Define cómo se separan los campos en cada línea
        tokenizer.setDelimiter(","); // El delimitador es la coma
        tokenizer.setQuoteCharacter('"'); // Los campos pueden estar entre comillas
        tokenizer.setStrict(false); // No exige que todas las líneas tengan el mismo número de tokens
        tokenizer.setNames("codigoCalle", "tipoVia", "nombreCalle", "primerNumTramo", "ultimoNumTramo", "barrio",
                "codigoDistrito", "nombreDistrito"); // Nombres de las columnas en el archivo

        DefaultLineMapper<CalleDto> lineMapper = new DefaultLineMapper<>(); // Mapea cada línea a un objeto CalleDto
        lineMapper.setLineTokenizer(tokenizer); // Asigna el tokenizer al mapper
        lineMapper.setFieldSetMapper(
                new BeanWrapperFieldSetMapper<>() {{ // Utiliza BeanWrapper para asignar los valores a los campos del DTO
                    setTargetType(CalleDto.class); // Especifica la clase destino para el mapeo: ClaseDto.class
                }});

        return new FlatFileItemReaderBuilder<CalleDto>().name(
                        "calleReader") // Builder para configurar el FlatFileItemReader
                .resource(new ClassPathResource("tramos_calle_BarrioDismuni.csv")) // Ubicación del archivo CSV
                .linesToSkip(1) // Ignora la primera línea
                .lineMapper(lineMapper) // Asigna el LineMapper configurado
                .build(); // Construye el lector
    }

    @Bean
    public FlatFileItemReader<CalleDto> csvCompletoReader() {
        // Configura otro lector de archivos planos para objetos CalleDto que es muy parecido al anterior
        return new FlatFileItemReaderBuilder<CalleDto>().name("csvCompletoReader")
                .resource(new ClassPathResource("tramos_calle_BarrioDismuni.csv"))
                .lineTokenizer(new DelimitedLineTokenizer() {{
                    setDelimiter(",");
                    setQuoteCharacter('"');
                    setNames("codigoCalle", "tipoVia", "nombreCalle", "primerNumTramo", "ultimoNumTramo", "barrio",
                            "codigoDistrito", "nombreDistrito");
                }}).fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(CalleDto.class);
                }}).linesToSkip(1).build();
    }
}