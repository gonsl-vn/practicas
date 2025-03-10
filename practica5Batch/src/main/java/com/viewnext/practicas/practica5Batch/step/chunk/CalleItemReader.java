package com.viewnext.practicas.practica5Batch.step.chunk;

import com.viewnext.practicas.practica5Batch.model.Calle;
import org.hibernate.annotations.Comment;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class CalleItemReader extends FlatFileItemReader<Calle> {
    public CalleItemReader(){
        setResource(new
                ClassPathResource("tramos_calle_BarrioDismuni.csv"));
        setLinesToSkip(1);
        DefaultLineMapper<Calle> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames("CODIGO_CALLE","TIPO_VIA","NOMBRE_CALLE",
                "PRIMER_NUM_TRAMO","ULTIMO_NUM_TRAMO","BARRIO",
                "COD_DISTRITO", "NOM_DISTRITO");
        BeanWrapperFieldSetMapper<Calle> fieldSetMapper = new
                BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Calle.class);
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        setLineMapper(lineMapper);

        System.out.println("Se han leido las calles");
    }
}
