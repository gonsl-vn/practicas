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
public class CopiarTodasLasCallesStep {

    @Bean
    public Step copiarTodasCallesStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            FlatFileItemReader<Calle> calleCsvReader, ItemProcessor<Calle, Calle> calleProcessor,
            @Qualifier("calleCsvwriter") ItemWriter<Calle> calleCsvwriter,
            SkipListenerConfig skipListener,
            SkipCheckingListener skipCheckingListener){
        return new StepBuilder("calleStep", jobRepository)
                .<Calle, Calle>chunk(10, transactionManager)
                .reader(calleCsvReader)
                .processor(calleProcessor)
                .writer(calleCsvwriter)
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skipLimit(20)
                .listener(skipListener)
                .listener(skipCheckingListener)
                .build();
    }
}
