package com.example.practica52.config.writter;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Configuration
public class StringUsuACsvWritter {

    @Bean
    public ItemWriter<String> stringCsvWritter(){
        iniciarFicheroCsv();

        FlatFileItemWriter<String> writer = new FlatFileItemWriter<>();

        writer.setResource( new FileSystemResource("src/main/resources/string_compra_usuarios.csv"));

        LineAggregator<String> lineAggregator = new PassThroughLineAggregator<>();

        writer.setLineAggregator(lineAggregator);
        return writer;
    }

    private void iniciarFicheroCsv(){
        File file = new File("src/main/resources/string_compra_usuarios.csv");
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (Exception e){
                throw new RuntimeException("Error Creando el fichero csv de string_compra_usuarios", e);
            }
        }
    }
}
