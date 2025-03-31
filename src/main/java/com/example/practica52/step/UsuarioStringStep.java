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

@Slf4j
@Configuration
public class UsuarioStringStep {

    @Bean
    public Step compraUsuarioStringStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<Usuario> usuarioCsvReader,
            ItemProcessor<Usuario, String> usuarioStringItemProcessor,
            ItemWriter<String> stringCsvWritter,
            SkipListenerConfig skipListenerConfig){

        return new StepBuilder( "compraUsuarioStringStep", jobRepository)
                .<Usuario, String>chunk(100, transactionManager)
                .reader(usuarioCsvReader)
                .processor(usuarioStringItemProcessor)
                .writer(stringCsvWritter)
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skipLimit(3)
                .listener(skipListenerConfig)
                .build();
    }

}
