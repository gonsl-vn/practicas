package com.viewnext.practicas.practica5Batch.config;

import com.viewnext.practicas.practica5Batch.model.Calle;
import com.viewnext.practicas.practica5Batch.step.chunk.CalleItemProcessor;
import com.viewnext.practicas.practica5Batch.step.chunk.CalleItemReader;
import com.viewnext.practicas.practica5Batch.step.chunk.CalleItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job calleJob(JobRepository jobRepository,
            Step calleStep){
        return new JobBuilder("calleJob",jobRepository).
                start(calleStep).build();
    }

    @Bean
    public Step calleStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            CalleItemReader calleItemReader,
            CalleItemProcessor calleItemProcessor,
            CalleItemWriter calleItemWriter){
        return new StepBuilder("calleStep", jobRepository)
                .<Calle, Calle>chunk(10,transactionManager)
                .reader(calleItemReader)
                .processor(calleItemProcessor)
                .writer(calleItemWriter)
                .build();
    }
}
