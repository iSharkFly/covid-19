package com.ossez.covid19.service.batch.jobs;

import com.ossez.covid19.service.batch.steps.LinesWriter;
import com.ossez.covid19.common.models.Listing;
import com.ossez.covid19.service.batch.steps.LineProcessor;
import com.ossez.covid19.service.batch.steps.LineReader;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public ItemReader<Listing> itemReader() {
        return new LineReader();
    }

    @Bean
    public ItemProcessor<Listing, Listing> itemProcessor() {
        return new LineProcessor();
    }

    @Bean
    public ItemWriter<Listing> itemWriter() {
        return new LinesWriter();
    }


//    @Bean
//    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
//        return jobBuilderFactory.get("importUserJob")
//                .incrementer(new RunIdIncrementer())
//                .listener(listener)
//                .flow(step1)
//                .end()
//                .build();
//    }
//
//    @Bean
//    public Step step1(ItemReader<Listing> itemReader, ItemProcessor<Listing, Listing> itemProcessor, ItemWriter<Listing> itemWriter) {
//        return stepBuilderFactory.get("step1")
//                .<Listing, Listing>chunk(100)
//                .reader(itemReader)
//                .processor(itemProcessor)
//                .writer(itemWriter)
//                .build();
//    }

}
