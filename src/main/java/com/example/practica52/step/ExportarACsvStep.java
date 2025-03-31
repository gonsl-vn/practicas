package com.example.practica52.step;

import com.example.practica52.listener.SkipCheckingListener;
import com.example.practica52.listener.SkipListenerConfig;
import com.example.practica52.model.Calle;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ExportarACsvStep {

    @Bean
    public Step exportarTodoACsvStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            @Qualifier("calleCsvReader")
            FlatFileItemReader<Calle> csvCalleReader,
            ItemProcessor<Calle,Calle> csvCProcessor,
            ItemWriter<Calle> writeCalleACsv,
            SkipListenerConfig skipListenerConfig,
            SkipCheckingListener skipCheckingListener){

        return  new StepBuilder("exportaTodoACsvStep", jobRepository)
                .<Calle, Calle>chunk(20, transactionManager)
                .reader(csvCalleReader)
                .processor(csvCProcessor)
                .writer(writeCalleACsv)
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skipLimit(20)
                .listener(skipListenerConfig)
                .listener(skipCheckingListener)
                .build();
    }

}
