package com.viewnext.springbatchf.jobs.job3;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;

@Data
@Getter
@Setter
@NoArgsConstructor
public class Job1M {
    public static Job job3(JobRepository jobRepository, Step step1job3, Step step2job3) {

        return new JobBuilder("job3", jobRepository).start(step1job3).next(step2job3).build();
    }
}
