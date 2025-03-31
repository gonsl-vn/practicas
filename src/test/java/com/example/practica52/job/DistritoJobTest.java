package com.example.practica52.job;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@SpringBatchTest
public class DistritoJobTest {

    @Autowired
    private Job exportarTodoACsvJob;
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @BeforeEach
    void setUp(){
        this.jobLauncherTestUtils.setJob(exportarTodoACsvJob);
    }

    @Test
    void ExportarTodoACsvTest_ExportarACsvJob_shouldBeCompleted() throws Exception{


        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("hora: ", LocalTime.now().toNanoOfDay())
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        assertTrue(jobExecution.getExitStatus().getExitCode().startsWith("Completado con"));
        //assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }
}
