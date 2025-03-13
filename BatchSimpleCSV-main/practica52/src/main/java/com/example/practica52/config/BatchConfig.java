package com.example.practica52.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class BatchConfig {

    private final JobLauncher jobLauncher;
    private final ApplicationContext applicationContext;

    //private static final Logger logger = LoggerFactory.getLogger(Slf4j.class);

    public BatchConfig( JobLauncher jobLauncher, ApplicationContext applicationContext) {
        this.jobLauncher = jobLauncher;

        this.applicationContext = applicationContext;
    }
    @Bean
    public CommandLineRunner jobLauncherRunner(){
        return args -> {
            if(args.length==0){
                log.warn("No has incluido ningun nombre de job");
                return;
            }
            String jobName = args[0];
            String distritoAFiltrar = (args[1] != null) ? args[1] : null;
            try {
                Job job = applicationContext.getBean(jobName, Job.class);

                JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
                jobParametersBuilder.addLong("hora: ", System.currentTimeMillis() );

                if(distritoAFiltrar != null && jobName.equals("copiarCalleJob")){
                    jobParametersBuilder.addString("distritoAFiltrar", distritoAFiltrar);
                }
                JobParameters jobParameters =  jobParametersBuilder.toJobParameters();
                log.info("ejecutando job: ");

                JobExecution jobExecution = jobLauncher.run(job, jobParameters);
                log.info("job acabado, Estado: " );
            } catch (Exception e){
                log.warn("Error en la ejecucion de" );
            }
        };
    }


}
