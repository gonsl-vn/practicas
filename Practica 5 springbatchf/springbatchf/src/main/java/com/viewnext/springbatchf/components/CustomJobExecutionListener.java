package com.viewnext.springbatchf.components;

import com.viewnext.springbatchf.Repositories.CalleRepository;
import com.viewnext.springbatchf.Repositories.JobExecutionLogRepository;
import com.viewnext.springbatchf.model.Job_execution_log;
import com.viewnext.springbatchf.processor.CalleItemProcessor;
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
        log.setNum_registros_guardados(numRegistrosGuardados);
        log.setEstado_batch(estadoBatch);
        log.setTimestamp(timestampOperacion);

        jobExecutionLogRepository.save(log);

    }
}

