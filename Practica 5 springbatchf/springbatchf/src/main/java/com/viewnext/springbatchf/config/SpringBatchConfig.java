package com.viewnext.springbatchf.config;

import com.viewnext.springbatchf.components.SkipListenerCalle;
import com.viewnext.springbatchf.model.Calle;
import com.viewnext.springbatchf.processor.CalleItemProcessor;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchConfig {

    private Calle calle;

    @Value("classpath:tramos_calle_BarrioDismuni.csv")
    private Resource inputCsv;

    @Value("file:xml/output.xml")
    private Resource outputXml;

    @Bean
    public ItemReader<Calle> calleItemReader() throws UnexpectedInputException, ParseException {
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

    @Bean
    public ItemProcessor<Calle, Calle> itemProcessor() {
        return new CalleItemProcessor();
    }

    @Bean
    public JpaItemWriter<Calle> itemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Calle> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public Step stepCalles(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            ItemReader<Calle> reader, ItemProcessor<Calle, Calle> processor, ItemWriter<Calle> writer,
            SkipListenerCalle skipListenerCalle) {

        return new StepBuilder("CallesStep", jobRepository).<Calle, Calle>chunk(10, transactionManager)
                .listener(skipListenerCalle).reader(reader).processor(processor).writer(writer).faultTolerant()
                .skip(IllegalArgumentException.class).skip(FlatFileParseException.class)
                .skipLimit(1000) // máximo de errores permitidos
                .faultTolerant().listener(skipListenerCalle).build();
    }

    @Bean
    public Job jobCalles(JobRepository jobRepository, Step stepCalles) {
        return new JobBuilder("CallesJob", jobRepository).start(stepCalles).build();
    }

}
