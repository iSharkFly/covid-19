package com.ossez.covid19.service.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Class to store a town,state,county association. This class is used as
 * part of a larger collection to map a town id to the three aforementioned
 * values.
 *
 * @author YUCHENG
 */
@RestController
@RequestMapping(value = "/task")
public class TaskController {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job cloudClean;


    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public String refreshData() {

        try {
            JobParameters jobParameters =
                    new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis())).toJobParameters();
            jobLauncher.run(cloudClean, jobParameters);
        } catch (Exception ex) {
            return ex.toString();
        }

        return "SU";
    }

}
