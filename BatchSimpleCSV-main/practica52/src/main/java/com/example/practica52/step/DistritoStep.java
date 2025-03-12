package com.example.practica52.step;

import com.example.practica52.listener.SkipListenerConfig;
import com.example.practica52.model.Calle;
import com.example.practica52.model.Distrito;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DistritoStep {

    @Bean
    public Step distritoStep1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            FlatFileItemReader<Calle> reader, ItemProcessor<Calle, Distrito> distritoProcessor,
            ItemWriter<Distrito> distritoWritter, SkipListenerConfig skipListenerConfig){
        return new StepBuilder("distritoStep", jobRepository)
                .<Calle, Distrito>chunk(1, transactionManager)
                .reader(reader)
                .processor(distritoProcessor)
                .writer(distritoWritter)
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skipLimit(20)
                .listener(skipListenerConfig)
                .build();
    }
}
