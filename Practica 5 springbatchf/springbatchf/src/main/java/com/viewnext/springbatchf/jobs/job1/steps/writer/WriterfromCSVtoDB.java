package com.viewnext.springbatchf.jobs.job1.steps.writer;

import com.viewnext.springbatchf.model.Calle;
import jakarta.persistence.EntityManagerFactory;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.batch.item.database.JpaItemWriter;

@Data
@NoArgsConstructor
public class WriterfromCSVtoDB {

    public static JpaItemWriter<Calle> itemWritertoDB(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Calle> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
