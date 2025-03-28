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
import com.viewnext.springbatchf.jobs.job3.Job1M;
import com.viewnext.springbatchf.jobs.job3.steps.step1.Reader1M;
import com.viewnext.springbatchf.jobs.job3.steps.step1.Writer1M;
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
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchConfig {

    @Value("classpath:tramos_calle_BarrioDismuni.csv")
    private Resource inputCsv1;

    @Value("classpath:tramos_calle_BarrioDismuniOneMillion.csv")
    private Resource csv1M;

    @Bean
    public ItemReader<Calle> calleItemReader() throws UnexpectedInputException, ParseException {
        return CalleReaderFromCSVtoDB.calleItemReader(inputCsv1);
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

    // *---------------------------------------------------------------------------------*//

    @Bean
    @Qualifier("step1job3")
    public Step step1Job3(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            EntityManagerFactory entityManagerFactory, SkipListenerCalle skipListenerCalle) {
        return new StepBuilder("step1job3", jobRepository).<Calle, Calle>chunk(1000000, transactionManager)
                .listener(skipListenerCalle).reader(Reader1M.read(csv1M))
                .writer(Writer1M.itemWritertoDB(entityManagerFactory)).faultTolerant()
                .skip(IllegalArgumentException.class).skip(FlatFileParseException.class).skipLimit(1000000)
                .faultTolerant().listener(skipListenerCalle).build();
    }

    @Bean
    @StepScope
    public TaskExecutor taskExecutor(@Value("#{jobParameters['nhilos']}") Long nhilos) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(nhilos.intValue());
        executor.setMaxPoolSize(nhilos.intValue());
        executor.setQueueCapacity(100); // puedes ajustar esto
        executor.setThreadNamePrefix("multi-thread-pool-");
        executor.initialize();
        return executor;
    }

    @Bean
    @Qualifier("step2job3")
    public Step step2Job3(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            EntityManagerFactory entityManagerFactory, SkipListenerCalle skipListenerCalle) {
        Long nhilos = 1L;
        return new StepBuilder("step2job3", jobRepository).<Calle, Calle>chunk(1000000, transactionManager)
                .listener(skipListenerCalle).reader(Reader1M.read(csv1M))
                .writer(Writer1M.itemWritertoDB(entityManagerFactory)).faultTolerant()
                .skip(IllegalArgumentException.class).skip(FlatFileParseException.class).skipLimit(1000000)
                .faultTolerant().listener(skipListenerCalle).taskExecutor(taskExecutor(nhilos)).build();
    }

    @Bean
    public Job job3(JobRepository jobRepository, @Qualifier("step1job3") Step step1job3,
            @Qualifier("step2job3") Step step2job3) {
        return Job1M.job3(jobRepository, step1job3, step2job3);
    }

}
