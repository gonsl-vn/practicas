package com.example.practica52.config.reader;

import com.example.practica52.model.Calle;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Slf4j
@Configuration
public class MillonCalleReader {

    //private static final Logger logger = LoggerFactory.getLogger(Slf4j.class);

    @Bean
    FlatFileItemReader<Calle> millonCalleCsvReader(){
        FlatFileItemReader<Calle> reader = new FlatFileItemReader<>();
        reader.setResource(
                new FileSystemResource("src/main/resources/tramos_calle_BarrioDismuniOneMillion.csv"));
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
        reader.setSkippedLinesCallback(line -> log.warn("Linea mal escrita"));
        reader.setStrict(false);
        return reader;
    }
}
