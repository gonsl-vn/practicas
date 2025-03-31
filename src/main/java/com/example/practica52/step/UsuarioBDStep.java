package com.example.practica52.step;

import com.example.practica52.listener.SkipListenerConfig;
import com.example.practica52.model.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
public class UsuarioBDStep {

    @Bean
    public Step copiarUsuarioBDStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<Usuario> usuarioCsvReader,
            ItemProcessor<Usuario, Usuario> usuarioAUsuarioProcessor,
            ItemWriter<Usuario> usuarioABDWritter,
            SkipListenerConfig skipListener){

        return new StepBuilder("copiarUsuarioBDStep",jobRepository)
                .<Usuario, Usuario>chunk(100, transactionManager)
                .reader(usuarioCsvReader)
                .processor(usuarioAUsuarioProcessor)
                .writer(usuarioABDWritter)
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skipLimit(3)
                .listener(skipListener)
                .build();
    }
}
