package com.venkatesh.spring.batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeesRestController {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @GetMapping("/employees")
    public void loadCsvToDb() throws Exception {
        JobParameters jobParameters=new JobParametersBuilder()
                .addLong("Start-At",System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(job,jobParameters);
    }
}
