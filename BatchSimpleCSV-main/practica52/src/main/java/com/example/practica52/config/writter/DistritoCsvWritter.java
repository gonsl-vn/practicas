package com.example.practica52.config.writter;

import com.example.practica52.model.Distrito;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Slf4j
@Configuration
public class DistritoCsvWritter {

    //private final Logger logger = LoggerFactory.getLogger(Slf4j.class);

    @Bean
    public ItemWriter<Distrito> writeDistritoACsv(){
        iniciaFicheroCsv();
        FlatFileItemWriter<Distrito> writer = new FlatFileItemWriter<>();
        writer.setResource( new FileSystemResource("C:\\Users\\6003351\\Desktop\\Practicas\\BatchSimpleCSV-main\\practica52\\src\\main\\resources\\distritos_exportados.csv"));

        BeanWrapperFieldExtractor<Distrito> fieldExtractor =
                new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"id", "nombreDistrito", "numeroViviendas"});

        DelimitedLineAggregator<Distrito> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);

        writer.setLineAggregator(lineAggregator);
        return writer;
    }

    private void iniciaFicheroCsv(){
        File file = new File("C:\\Users\\6003351\\Desktop\\Practicas\\BatchSimpleCSV-main\\practica52\\src\\main\\resources\\distritos_exportados.csv");
        if(!file.exists()){
            try{
                log.info("Creando el archivo distritos_exportados.csv");
                file.createNewFile();
            }catch(Exception e){
                log.warn("Error iniciando el fichero distritos csv");
                throw new RuntimeException("Error creando el fichero de distritos csv",e);
            }
        }
    }
}
