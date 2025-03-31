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
    private final Job job3;

    public ProgrammaticJobLauncher(JobLauncher jobLauncher, Job job1, Job job2, Job job3) {
        this.jobLauncher = jobLauncher;
        this.job1 = job1;
        this.job2 = job2;
        this.job3 = job3;
    }

    @Override
    public void run(String... args) throws Exception {
        // Valores por defecto
        String jobSeleccionado = "";
        String distrito = "";
        long nHilos = 0;

        // Parsear argumentos de línea de comandos
        for (String arg : args) {
            if (arg.startsWith("--job=")) {
                jobSeleccionado = arg.substring("--job=".length());
            } else if (arg.startsWith("--distrito=")) {
                distrito = arg.substring("--distrito=".length());
            } else if (arg.startsWith("--nhilos=")) {
                nHilos = Long.parseLong(arg.substring("--nhilos=".length()));
            }
        }

        switch (jobSeleccionado) {
        case "jobImportarDB":
            JobParameters params1 = new JobParametersBuilder().addString("distrito", distrito)
                    .addLong("time", System.currentTimeMillis()).toJobParameters();
            System.out.println("🚀 Lanzando JobImportarDB con distrito: " + distrito);
            jobLauncher.run(job1, params1);
            break;

        case "jobExportarCSV":
            JobParameters params2 = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            System.out.println("🚀 Lanzando JobExportarCSV");
            jobLauncher.run(job2, params2);
            break;

        case "jobMultihilos":
            JobParameters params3 = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .addLong("nhilos", nHilos).toJobParameters();
            System.out.println("🚀 Lanzando JobMultihilos con " + nHilos + " hilos");
            jobLauncher.run(job3, params3);
            break;

        default:
            System.err.println("❌ Job no reconocido: " + jobSeleccionado);
        }
    }
}


