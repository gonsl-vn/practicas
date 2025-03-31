package com.example.practica52.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalTime;

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
            String[] parametros = args[0].split(",");
            String jobName = parametros[0];

            //if(parametros[1]!=null){ log.info("Parametro[1] " +parametros[1]);}

            String distritoAFiltrar = (parametros.length > 1) ? parametros[1] : null;
            log.info("DISTRITOAFILTRAR "+ distritoAFiltrar);
            try {
                Job job = applicationContext.getBean(jobName, Job.class);

                JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
                jobParametersBuilder.addLong("hora: ", LocalTime.now().toNanoOfDay());

                if(distritoAFiltrar != null && jobName.equals("copiarTodasLasCallesJob")){
                    log.info("Añadiendo el parametro para copiarTodasLasCallesJob...");
                    jobParametersBuilder.addString("distritoAFiltrar", distritoAFiltrar);
                }
                JobParameters jobParameters =  jobParametersBuilder.toJobParameters();
                log.info("ejecutando job: " + job.getName());

                JobExecution jobExecution = jobLauncher.run(job, jobParameters);
                log.info("job acabado, Estado: " + jobExecution.getStatus() );
            } catch (Exception e){
                log.warn("Error en la ejecucion de: " +jobName +
                        " con el parametro: " + parametros[1] + " tambien en distrito filtrar: "
                +distritoAFiltrar);
            }
        };
    }


}
