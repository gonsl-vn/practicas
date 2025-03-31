package com.viewnext.springbatchf.jobs.job1.steps.reader;

import com.viewnext.springbatchf.models.Calle;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.Resource;

@Data
@NoArgsConstructor
public class CalleReaderFromCSVtoDB {

    public static ItemReader<Calle> calleItemReader(Resource inputCsv) throws UnexpectedInputException, ParseException {
        FlatFileItemReader<Calle> reader = new FlatFileItemReader<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();

        String[] tokens = { "CODIGO_CALLE", "TIPO_VIA", "NOMBRE_CALLE", "PRIMER_NUM_TRAMO", "ULTIMO_NUM_TRAMO",
                "BARRIO", "COD_DISTRITO", "NOM_DISTRITO" };
        tokenizer.setNames(tokens);
        tokenizer.setStrict(false);
        reader.setResource(inputCsv);

        DefaultLineMapper<Calle> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        BeanWrapperFieldSetMapper<Calle> mapper = new BeanWrapperFieldSetMapper<>();
        mapper.setTargetType(Calle.class);
        lineMapper.setFieldSetMapper(mapper);

        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(1);
        return reader;
    }
}
