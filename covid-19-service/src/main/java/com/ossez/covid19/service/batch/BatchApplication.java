package com.ossez.covid19.service.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author YuCheng Hu
 */
@SpringBootApplication
@EnableBatchProcessing
public class BatchApplication implements CommandLineRunner {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job cloudClean;

    /**
     * Main function for service application
     *
     * @param args
     * @throws Exception
     */
    public static void main(String... args) throws Exception {
        SpringApplication.run(BatchApplication.class, args);
//        SpringApplication app = new SpringApplication(Application.class);
//        app.setWebEnvironment(false);
//        ApplicationContext ctx= app.run(args);
    }


    @Override
    public void run(String... args) throws Exception {

        for (String arg: args) {
            System.out.println(arg);
        }

//        JobParameters jobParameters  =
//                new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis())).toJobParameters();
//        jobLauncher.run(cloudClean, jobParameters);
    }
}
