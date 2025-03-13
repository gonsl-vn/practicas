package com.example.practica52.step;

import com.example.practica52.listener.SkipCheckingListener;
import com.example.practica52.listener.SkipListenerConfig;
import com.example.practica52.model.Calle;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MillonCalleStep {

    @Bean
    public Step stepMultihilos(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            @Qualifier("millonCalleCsvReader")
            FlatFileItemReader<Calle> millonCalleCsvReader,
            ItemProcessor<Calle, Calle> calleACalleProcessor,
            @Qualifier("calleCsvwriter")
            ItemWriter<Calle> calleCsvwriter,
            SkipListenerConfig skipListener,
            SkipCheckingListener skipCheckingListener){

        return new StepBuilder("calleStep", jobRepository)
                .<Calle, Calle>chunk(1, transactionManager)
                .reader(millonCalleCsvReader)
                .processor(calleACalleProcessor)
                .writer(calleCsvwriter)
                .taskExecutor(taskExecutor())
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skipLimit(1000)
                .listener(skipListener)
                .listener(skipCheckingListener)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(50);
        executor.initialize();
        return executor;
    }
}
