package com.example.practica52.step;

import com.example.practica52.listener.SkipCheckingListener;
import com.example.practica52.listener.SkipListenerConfig;
import com.example.practica52.model.Distrito;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ExportarDistritoACsvStep {

    @Bean
    public Step exportarTodosDistritosACsvStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<Distrito> csvDistritoReader,
            ItemProcessor<Distrito, Distrito> csvDProcessor,
            ItemWriter<Distrito> writeDistritoACsv,
            SkipListenerConfig skipListenerConfig,
            SkipCheckingListener skipCheckingListener){

        return new StepBuilder("exportarTodosDistritosACsvStep", jobRepository)
                .<Distrito, Distrito>chunk(20, transactionManager)
                .reader(csvDistritoReader)
                .processor(csvDProcessor)
                .writer(writeDistritoACsv)
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skipLimit(20)
                .listener(skipListenerConfig)
                .listener(skipCheckingListener)
                .build();
    }
}
