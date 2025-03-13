package com.example.practica52.config.reader;

import com.example.practica52.model.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Slf4j
@Configuration
public class UsuarioReader {

    @Bean
    FlatFileItemReader<Usuario> usuarioCsvReader(){
        FlatFileItemReader<Usuario> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(
                "C:\\Users\\6003351\\Desktop\\Practicas\\BatchSimpleCSV-main\\practica52\\src\\main\\resources\\ficheroUsuarios.csv"));
        reader.setLinesToSkip(1);

        DefaultLineMapper<Usuario> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();

        tokenizer.setDelimiter(";");
        tokenizer.setNames("nombre", "DNI", "direccion","ciudad","importe","numpedido");

        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Usuario.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
        reader.setSkippedLinesCallback( line -> {log.warn("Esta mal escrita la linea");});
        return reader;
    }
}
