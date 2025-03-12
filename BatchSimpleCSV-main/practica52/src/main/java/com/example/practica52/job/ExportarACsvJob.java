package com.example.practica52.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExportarACsvJob {

    @Bean
    public Job exportarTodoACsvJob(Step exportarTodoACsvStep, Step distritoStep1,
            Step step, JobRepository jobRepository){
        return new JobBuilder("exportarTodoACsvJob", jobRepository)
                .start(step).next(distritoStep1).next(exportarTodoACsvStep).build();
    }
}
