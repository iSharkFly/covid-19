package com.ossez.covid19.service.batch.jobs;

import com.ossez.covid19.common.models.Listing;
import com.ossez.covid19.service.batch.jobs.listener.JobCompletionNotificationListener;
import com.ossez.covid19.service.batch.tasklet.AwsTasklet;
import com.ossez.covid19.service.batch.tasklet.Covid19Tasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@ComponentScan
@EnableBatchProcessing
public class CloudJobConf {

    public JobBuilderFactory jobBuilderFactory;
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public CloudJobConf(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job cloudClean(JobCompletionNotificationListener listener, Step importDataStep) {
        return jobBuilderFactory.get("cloudClean")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(importDataStep)
                .end()
                .build();
    }

//    @Bean
//    public Step stepAws(ItemReader<Listing> itemReader, ItemProcessor<Listing, Listing> itemProcessor, ItemWriter<Listing> itemWriter) {
//        return stepBuilderFactory.get("stepAws")
//                .<Listing, Listing>chunk(100)
//                .reader(itemReader)
//                .processor(itemProcessor)
//                .writer(itemWriter)
//                .build();
//    }

    //
    @Bean
    public Step deleteFilesStep(StepBuilderFactory stepBuilders) {
        return stepBuilders.get("deleteFilesStep")
                .tasklet(fileDeletingTasklet()).build();
    }

    @Bean
    public Step importDataStep(StepBuilderFactory stepBuilders) {
        return stepBuilders.get("importDataStep").tasklet(covid19Tasklet()).build();
    }


    // TASKLET
    @Bean
    public AwsTasklet fileDeletingTasklet() {
        return new AwsTasklet(
                new FileSystemResource("target/test-inputs"));
    }

    @Bean
    public Covid19Tasklet covid19Tasklet() {
        return new Covid19Tasklet(new FileSystemResource("target/test-inputs"));
    }

}
