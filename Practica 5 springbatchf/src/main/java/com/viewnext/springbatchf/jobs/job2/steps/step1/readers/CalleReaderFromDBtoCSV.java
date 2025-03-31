package com.viewnext.springbatchf.jobs.job2.steps.step1.readers;

import com.viewnext.springbatchf.models.Calle;
import jakarta.persistence.EntityManagerFactory;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;

@Data
@NoArgsConstructor
public class CalleReaderFromDBtoCSV {

    public static JpaPagingItemReader<Calle> readerDB(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Calle>().name("calleReaderFromDB")
                .entityManagerFactory(entityManagerFactory).queryString("SELECT c FROM Calle  c").pageSize(100).build();
    }
}
