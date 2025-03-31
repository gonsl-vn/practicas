package com.example.practica52.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CopiarCalleJob {

    @Bean
    public Job copiarTodasLasCallesJob(Step step, JobRepository jobRepository){
        return new JobBuilder("copiarCalleJob", jobRepository)
                .start(step).build();
    }
}
