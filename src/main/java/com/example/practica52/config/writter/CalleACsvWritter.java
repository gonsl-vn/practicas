package com.example.practica52.config.writter;

import com.example.practica52.model.Calle;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

@Configuration
public class CalleACsvWritter {

    @Bean
    public ItemWriter<Calle> writeCalleACsv(){
        iniciaFicheroCsv();
        FlatFileItemWriter<Calle> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("src/main/resources/calles_exportadas.csv"));

        BeanWrapperFieldExtractor<Calle> fieldExtractor =  new BeanWrapperFieldExtractor<>();
        //fieldExtractor.setNames( new String[]{"Id", "CODIGO_CALLE", "TIPO_VIA", "NOMBRE_CALLE",
          //      "PRIMER_NUM_TRAMO", "ULTIMO_NUM_TRAMO", "BARRIO", "COD_DISTRITO",
            //    "NOM_DISTRITO"});

        fieldExtractor.setNames( new String[]{"barrio", "codigoCalle",
        "codigoDistrito", "nombreCalle", "nombreDistrito", "primerNumTramo",
                "tipoVia", "ultimoNumTramo"} );

        DelimitedLineAggregator<Calle> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);

        writer.setLineAggregator(lineAggregator);
        writer.setHeaderCallback(writer1 -> writer1.write("BARRIO, CODIGOCALLE,"+
                "CODIGODISTRITO, NOMBRECALLE, NOMBREDISTRITO, PRIMERNUMTRAMO,"+
                "TIPOVIA, ULTIMONUMTRAMO"));
        return writer;
    }

    private void iniciaFicheroCsv(){
        File file = new File("src/main/resources/calles_exportadas.csv");
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch(Exception e){
                throw new RuntimeException("Error creando el fichero csv",e);
            }
        }
    }
}
