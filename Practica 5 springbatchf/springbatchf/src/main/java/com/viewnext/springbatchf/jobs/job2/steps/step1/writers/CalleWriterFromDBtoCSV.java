package com.viewnext.springbatchf.jobs.job2.steps.step1.writers;

import com.viewnext.springbatchf.model.Calle;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class CalleWriterFromDBtoCSV {

    @Bean
    public static FlatFileItemWriter<Calle> itemWriterstep1job2() {

        BeanWrapperFieldExtractor<Calle> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(
                new String[] { "codigoCalle", "tipoVia", "nombreCalle", "primerNumTramo", "ultimoNumTramo", "barrio",
                        "codDistrito", "nomDistrito" });

        DelimitedLineAggregator<Calle> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);

        return new FlatFileItemWriterBuilder<Calle>().name("calleWriterToCSV")
                .resource(new FileSystemResource("output/calles_exportadas.csv")).lineAggregator(lineAggregator)
                .headerCallback(writer -> writer.write(
                        "CODIGO_CALLE,TIPO_VIA,NOMBRE_CALLE,PRIMER_NUM_TRAMO,ULTIMO_NUM_TRAMO,BARRIO,COD_DISTRITO,NOM_DISTRITO"))
                .build();
    }
}
