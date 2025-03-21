package com.viewnext.springbatchf.config;

import org.springframework.batch.core.configuration.JobFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    private JobFactory jobFactory;

    private StepBuilder builder;
}
