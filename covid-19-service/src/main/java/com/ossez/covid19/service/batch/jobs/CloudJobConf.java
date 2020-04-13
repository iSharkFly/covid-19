package com.ossez.covid19.service.batch.jobs;

import com.ossez.covid19.service.batch.tasklet.AwsTasklet;
import com.ossez.covid19.common.models.Listing;
import com.ossez.covid19.service.batch.JobCompletionNotificationListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class CloudJobConf {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job cloudClean(JobCompletionNotificationListener listener, Step stepAws, Step deleteFilesStep) {
        return jobBuilderFactory.get("cloudClean")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(stepAws).next(deleteFilesStep)
                .end()
                .build();
    }

    @Bean
    public Step stepAws(ItemReader<Listing> itemReader, ItemProcessor<Listing, Listing> itemProcessor, ItemWriter<Listing> itemWriter) {
        return stepBuilderFactory.get("stepAws")
                .<Listing, Listing>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Step deleteFilesStep(StepBuilderFactory stepBuilders) {
        return stepBuilders.get("deleteFilesStep")
                .tasklet(fileDeletingTasklet()).build();
    }


    @Bean
    public AwsTasklet fileDeletingTasklet() {
        return new AwsTasklet(
                new FileSystemResource("target/test-inputs"));
    }

}
