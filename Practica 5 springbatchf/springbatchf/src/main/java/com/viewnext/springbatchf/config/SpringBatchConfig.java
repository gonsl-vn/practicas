package com.viewnext.springbatchf.config;

import com.viewnext.springbatchf.components.CustomJobExecutionListener;
import com.viewnext.springbatchf.components.SkipListenerCalle;
import com.viewnext.springbatchf.jobs.job1.JobCallesfromCSVtoDB;
import com.viewnext.springbatchf.jobs.job1.steps.StepCallesfromCSVtoDB;
import com.viewnext.springbatchf.jobs.job1.steps.processor.CalleItemProcessor;
import com.viewnext.springbatchf.jobs.job1.steps.reader.CalleReaderFromCSVtoDB;
import com.viewnext.springbatchf.jobs.job1.steps.writer.WriterfromCSVtoDB;
import com.viewnext.springbatchf.jobs.job2.JobCallesfromDBtoCSV;
import com.viewnext.springbatchf.jobs.job2.steps.step1.readers.CalleReaderFromDBtoCSV;
import com.viewnext.springbatchf.jobs.job2.steps.step1.writers.CalleWriterFromDBtoCSV;
import com.viewnext.springbatchf.jobs.job2.steps.step2.reader.LOGReaderFromDBtoCSV;
import com.viewnext.springbatchf.jobs.job2.steps.step2.writer.LogWriterFromDBtoCSV;
import com.viewnext.springbatchf.model.Calle;
import com.viewnext.springbatchf.model.Job_execution_log;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchConfig {

    @Value("classpath:tramos_calle_BarrioDismuni.csv")
    private Resource inputCsv;

    @Bean
    public ItemReader<Calle> calleItemReader() throws UnexpectedInputException, ParseException {
        return CalleReaderFromCSVtoDB.calleItemReader(inputCsv);
    }

    @Bean
    @StepScope
    @Primary
    public CalleItemProcessor itemProcessor(@Value("#{jobParameters['distrito']}") String distrito) {
        return new CalleItemProcessor(distrito);
    }

    @Bean
    @Primary
    public JpaItemWriter<Calle> itemWritertoDB(EntityManagerFactory entityManagerFactory) {
        return WriterfromCSVtoDB.itemWritertoDB(entityManagerFactory);
    }

    @Bean
    public Step step1job1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            ItemReader<Calle> reader, ItemProcessor<Calle, Calle> processor, ItemWriter<Calle> writer,
            SkipListenerCalle skipListenerCalle) {
        return StepCallesfromCSVtoDB.crearStep(jobRepository, transactionManager, reader, processor, writer,
                skipListenerCalle);
    }

    @Bean
    public Job job1(JobRepository jobRepository, Step step1job1, CustomJobExecutionListener listener) {
        return JobCallesfromCSVtoDB.jobCalles(jobRepository, step1job1, listener);
    }

    // *---------------------------------------------------------------------------------*//

    @Bean
    public Step step1job2(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            EntityManagerFactory entityManagerFactory) {
        return new StepBuilder("Step1job2", jobRepository).<Calle, Calle>chunk(100, transactionManager)
                .reader(CalleReaderFromDBtoCSV.readerDB(entityManagerFactory))
                .writer(CalleWriterFromDBtoCSV.itemWriterstep1job2()).build();
    }

    @Bean
    public Step step2job2(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            EntityManagerFactory entityManagerFactory) {
        return new StepBuilder("Step2job2", jobRepository).<Job_execution_log, Job_execution_log>chunk(100,
                        transactionManager).reader(LOGReaderFromDBtoCSV.readerDB(entityManagerFactory))
                .writer(LogWriterFromDBtoCSV.lOGWritefromDBtoCSV()).build();
    }

    @Bean
    public Job job2(JobRepository jobRepository, Step step1job2, Step step2job2) {
        return JobCallesfromDBtoCSV.job2(jobRepository, step1job2, step2job2);
    }

}
