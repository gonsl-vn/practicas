package com.example.practica52.job;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBatchTest
@SpringBootTest
public class NumCasasPorDistritoJobTest {

    @Autowired
    private Job numeroCasasPorDistritoJob;
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @BeforeEach
    void setUp(){
        jobLauncherTestUtils.setJob(numeroCasasPorDistritoJob);
    }

    @Test
    void numCasaPorDistritoJobTest_shouldBeCompleted() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("hora: ",LocalTime.now().toNanoOfDay())
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        assertTrue(jobExecution.getExitStatus().getExitCode().startsWith("Completado con"));
    }
}
