package com.example.practica52.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.config.Task;



@Configuration
public class UsuarioJob {

    @Bean
    public Job usuarioBDyStringJob(Step copiarUsuarioBDStep, Step compraUsuarioStringStep, JobRepository jobRepository){
        return new JobBuilder("usuarioBDyStringJob", jobRepository)
        //        .start(compraUsuarioStringStep).build();
        .start(splitFlow(copiarUsuarioBDStep, compraUsuarioStringStep)).build().build();
    }

    @Bean
    public Flow splitFlow(Step copiarUsuarioBDStep,Step compraUsuarioStringStep ){
        return new FlowBuilder<SimpleFlow>("flow en paralelo")
                .split(taskExecutorFlow())
                .add(flow1(copiarUsuarioBDStep), flow2(compraUsuarioStringStep))
                .build();

    }
    @Bean
    public Flow flow1(Step copiarUsuarioBDStep){
        return new FlowBuilder<SimpleFlow>("Copia-Usuarios-En-BD")
                .start(copiarUsuarioBDStep)
                .build();
    }

    @Bean
    public Flow flow2(Step compraUsuarioStringStep){
        return new FlowBuilder<SimpleFlow>("compraUsuariosAStringFlow")
                .start(compraUsuarioStringStep)
                .build();
    }



    @Bean
    public TaskExecutor taskExecutorFlow(){
        return new SimpleAsyncTaskExecutor("thread-num-");
    }
}
