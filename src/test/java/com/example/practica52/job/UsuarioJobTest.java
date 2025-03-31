package com.example.practica52.job;

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
public class UsuarioJobTest {

    @Autowired
    private Job usuarioBDyStringJob;
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @BeforeEach
    void setUp(){
        jobLauncherTestUtils.setJob(usuarioBDyStringJob);
    }

    @Test
    void usuarioBDyStringJobTest_shouldBeCompleted() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("hora: ", LocalTime.now().toNanoOfDay())
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());

        //assertTrue(jobExecution.getExitStatus().getExitCode().startsWith("Completado con"));

    }
}
