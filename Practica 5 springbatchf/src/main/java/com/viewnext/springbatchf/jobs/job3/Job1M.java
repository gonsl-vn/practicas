package com.viewnext.springbatchf.jobs.job3;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;

public class Job1M {

    public static Job job3(JobRepository jobRepository, Step step1job3, Step step2job3, int nHilos) {

        if (nHilos == 1) {
            return new JobBuilder("job3", jobRepository).start(step1job3).build();

        } else if (nHilos > 1) {
            return new JobBuilder("job3", jobRepository).start(step2job3).build();

        } else {
            throw new RuntimeException("Error numero de hilos: no se pasó una cifra válida");
        }
    }
}
