package com.example.practica52.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NumCasasPorDistritoJob {

    @Bean
    public Job numeroCasasPorDistritoJob(Step distritoStep1, JobRepository jobRepository){
        return new JobBuilder("numeroCasasPorDistritoJob", jobRepository)
                .start(distritoStep1).build();
    }
}
