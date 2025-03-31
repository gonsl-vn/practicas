package com.viewnext.springbatchf.jobs.job3.steps.step1;

import com.viewnext.springbatchf.models.Calle;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.database.JpaItemWriter;

public class Writer1M {

    public static JpaItemWriter<Calle> itemWritertoDB(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Calle> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
