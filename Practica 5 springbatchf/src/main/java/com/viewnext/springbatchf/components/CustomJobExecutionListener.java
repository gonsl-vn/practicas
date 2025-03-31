package com.viewnext.springbatchf.components;

import com.viewnext.springbatchf.jobs.job1.steps.processor.CalleItemProcessor;
import com.viewnext.springbatchf.models.Job_execution_log;
import com.viewnext.springbatchf.repositories.CalleRepository;
import com.viewnext.springbatchf.repositories.JobExecutionLogRepository;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomJobExecutionListener implements JobExecutionListener {

    @Autowired
    private JobExecutionLogRepository jobExecutionLogRepository;

    @Autowired
    private CalleRepository calleRepository;

    @Override
    public void afterJob(JobExecution jobExecution) {

        String filtroUsado = CalleItemProcessor.filtroDistrito;

        int numRegistrosGuardados = (int) calleRepository.count();

        String estadoBatch = jobExecution.getStatus().toString();

        LocalDateTime timestampOperacion = LocalDateTime.now();

        Job_execution_log log = new Job_execution_log();
        log.setDistrito(filtroUsado);
        log.setNumeroCasas(numRegistrosGuardados);
        log.setEstadoBatch(estadoBatch);
        log.setTimestamp(timestampOperacion);

        jobExecutionLogRepository.save(log);

    }
}

