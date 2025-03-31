package com.viewnext.springbatchf.jobs.job2.steps.step2.writer;

import com.viewnext.springbatchf.models.Job_execution_log;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class LogWriterFromDBtoCSV {

    @Bean
    public static FlatFileItemWriter<Job_execution_log> lOGWritefromDBtoCSV() {

        BeanWrapperFieldExtractor<Job_execution_log> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] { "id", "timestamp", "distrito", "numeroCasas", "estadoBatch" });

        DelimitedLineAggregator<Job_execution_log> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);

        return new FlatFileItemWriterBuilder<Job_execution_log>().name("LOGWritertoCSV")
                .resource(new FileSystemResource("output/LOG_CALLES.csv")).lineAggregator(lineAggregator)
                .headerCallback(writer -> writer.write("id,timestamp,distrito,numeroCasas,estadoBatch")).build();
    }
}
