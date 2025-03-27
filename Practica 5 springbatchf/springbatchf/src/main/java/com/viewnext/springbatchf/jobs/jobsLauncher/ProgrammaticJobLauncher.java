package com.viewnext.springbatchf.jobs.jobsLauncher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProgrammaticJobLauncher implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final Job job1;
    private final Job job2;

    public ProgrammaticJobLauncher(JobLauncher jobLauncher, Job job1, Job job2) {
        this.jobLauncher = jobLauncher;
        this.job1 = job1;
        this.job2 = job2;
    }

    @Override
    public void run(String... args) throws Exception {
        String jobSeleccionado = "jobImportarDBaaaaaaaaaa";
        String distrito = "ESTE";

        if (!jobSeleccionado.equals("jobImportarDB")) {
            JobParameters jobParams = new JobParametersBuilder().addString("distrito", distrito)
                    .addLong("time", System.currentTimeMillis()).toJobParameters();

            System.out.println("🚀 Lanzando JobImportarDB con distrito: " + distrito);
            jobLauncher.run(job1, jobParams);
        }

        if (!jobSeleccionado.equals("jobExportarCSV")) {
            JobParameters jobParams = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            System.out.println("🚀 Lanzando JobExportarCSV");
            jobLauncher.run(job2, jobParams);
        }
    }
}

