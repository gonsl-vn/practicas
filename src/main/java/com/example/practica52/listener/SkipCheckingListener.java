package com.example.practica52.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SkipCheckingListener implements StepExecutionListener {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution){
        String exitCode = stepExecution.getExitStatus().getExitCode();

        if(!exitCode.equals(ExitStatus.FAILED.getExitCode()) &&
                stepExecution.getSkipCount() >= 0){
            return new ExitStatus("Completado con " + stepExecution.getSkipCount() + " skips ");
        }else {return null;}
    }
}
