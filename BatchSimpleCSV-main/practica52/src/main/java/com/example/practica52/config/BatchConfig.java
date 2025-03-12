package com.example.practica52.config;

import com.example.practica52.job.CopiarCalleJob;
import com.example.practica52.step.CalleStep;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private static Logger logger = LoggerFactory.getLogger(Slf4j.class);

    public BatchConfig(JobRepository jobRepository,
                       PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }
/*
    @Bean
    public Job copiarCalleJobConfig(CopiarCalleJob copiarCalleJob,
                                    CalleStep calleStep,
                                    CalleReader reader,
                                    CalleProcessor processor,
                                    CalleWritter writter){

        Step calleStepParam = calleStep.step(jobRepository,
                transactionManager, reader.reader(),
                processor.processor(), writter.write());

        logger.info("Entra en Job desde BatchConfig");
        return copiarCalleJob.job(calleStepParam, jobRepository);
    }*/
}
