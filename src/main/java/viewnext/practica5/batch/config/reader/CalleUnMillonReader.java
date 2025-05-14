package viewnext.practica5.batch.config.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import viewnext.practica5.dto.CalleDto;

@Component
public class CalleUnMillonReader extends FlatFileItemReader<CalleDto> {

    public CalleUnMillonReader() {
        // Configura un lector de archivos planos para objetos CalleDto
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(); // Define cómo se separan los campos en cada línea
        tokenizer.setDelimiter(","); // El delimitador es la coma
        tokenizer.setQuoteCharacter('"'); // Los campos pueden estar entre comillas
        tokenizer.setStrict(false); // No exige que todas las líneas tengan el mismo número de tokens
        tokenizer.setNames("codigoCalle", "tipoVia", "nombreCalle", "primerNumTramo", "ultimoNumTramo", "barrio",
                "codigoDistrito", "nombreDistrito"); // Nombres de las columnas en el archivo

        DefaultLineMapper<CalleDto> lineMapper = new DefaultLineMapper<>(); // Mapea cada línea a un objeto CalleDto
        lineMapper.setLineTokenizer(tokenizer); // Asigna el tokenizer al mapper
        lineMapper.setFieldSetMapper(
                new BeanWrapperFieldSetMapper<>() {{ // Se utiliza BeanWrapper para asignar los valores a los campos del DTO
                    setTargetType(CalleDto.class); // Se especifica la clase destino para el mapeo: CalleDto.class
                }});

        setResource(
                new ClassPathResource("tramos_calle_BarrioDismuniOneMillion.csv")); // Ubicación del archivo CSV grande
        setLinesToSkip(1); // Ignora la primera línea
        setLineMapper(lineMapper); // Asigna el LineMapper configurado
    }
}