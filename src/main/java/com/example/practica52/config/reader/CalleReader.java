package com.example.practica52.config.reader;

import com.example.practica52.model.Calle;
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
public class CalleReader {
    @Bean
    FlatFileItemReader<Calle> calleCsvReader(){
        FlatFileItemReader<Calle> reader = new FlatFileItemReader<>();
        reader.setResource(
                new FileSystemResource("src/main/resources/tramos_calle_BarrioDismuni.csv"));
        reader.setLinesToSkip(1);

        DefaultLineMapper<Calle> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();

        tokenizer.setDelimiter(",");
        tokenizer.setNames("CODIGO_CALLE","TIPO_VIA","NOMBRE_CALLE",
                "PRIMER_NUM_TRAMO","ULTIMO_NUM_TRAMO","BARRIO",
                "COD_DISTRITO", "NOM_DISTRITO");

        BeanWrapperFieldSetMapper<Calle> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Calle.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
        reader.setSkippedLinesCallback(line -> System.out.println("Linea mal escrita"));
        reader.setStrict(false);
        log.info("Ha acabado de leer las calles");
        return reader;
    }
}
