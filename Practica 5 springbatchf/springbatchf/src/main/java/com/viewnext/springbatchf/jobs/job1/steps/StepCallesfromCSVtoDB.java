package com.viewnext.springbatchf.jobs.job1.steps;

import com.viewnext.springbatchf.components.SkipListenerCalle;
import com.viewnext.springbatchf.model.Calle;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.transaction.PlatformTransactionManager;

@Data
@NoArgsConstructor
public class StepCallesfromCSVtoDB {

    public static Step crearStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            ItemReader<Calle> reader, ItemProcessor<Calle, Calle> processor, ItemWriter<Calle> writer,
            SkipListenerCalle skipListenerCalle) {

        return new StepBuilder("CallesStep", jobRepository).<Calle, Calle>chunk(10, transactionManager)
                .listener(skipListenerCalle).reader(reader).processor(processor).writer(writer).faultTolerant()
                .skip(IllegalArgumentException.class).skip(FlatFileParseException.class).skipLimit(1000).faultTolerant()
                .listener(skipListenerCalle).build();
    }
}
