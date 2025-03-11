package com.example.practica52.config;

import com.example.practica52.job.CopiarCalleJob;
import com.example.practica52.step.CalleStep;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class BatchConfig {

    private static Logger logger = LoggerFactory.getLogger(Slf4j.class);

    @Bean
    public Job copiarCalleJob(){

        CopiarCalleJob copiarCalleJob = new CopiarCalleJob();
        CalleStep copiarCalleStep = new CalleStep();
        CalleReader reader = new CalleReader();
        CalleProcessor processor = new CalleProcessor();
        CalleWritter writter = new CalleWritter();

        copiarCalleStep.step()
        logger.info("Entra en Job desde BatchConfig");
        return copiarCalleJob.job()
    }
}
