package com.viewnext.springbatchf.jobs.job1;

import com.viewnext.springbatchf.components.CustomJobExecutionListener;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;

@Data
@NoArgsConstructor
public class JobCallesfromCSVtoDB {

    public static Job jobCalles(JobRepository jobRepository, Step stepCalles, CustomJobExecutionListener listener) {
        return new JobBuilder("CallesJob1", jobRepository).start(stepCalles).listener(listener).build();
    }

}
