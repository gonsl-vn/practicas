package com.viewnext.springbatchf.jobs.job2;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;

@Data
@NoArgsConstructor
public class JobCallesfromDBtoCSV {

    public static Job job2(JobRepository jobRepository, Step step1job2, Step step2job2) {
        return new JobBuilder("CallesJob2", jobRepository).start(step1job2).next(step2job2).build();
    }

}
