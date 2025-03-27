package com.viewnext.springbatchf.jobs.job2.steps.step2.reader;

import com.viewnext.springbatchf.model.Job_execution_log;
import jakarta.persistence.EntityManagerFactory;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;

@Data
@NoArgsConstructor
public class LOGReaderFromDBtoCSV {

    public static JpaPagingItemReader<Job_execution_log> readerDB(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Job_execution_log>().name("LOGReaderFromDB")
                .entityManagerFactory(entityManagerFactory).queryString("SELECT l FROM Job_execution_log  l")
                .pageSize(100).build();
    }
}
