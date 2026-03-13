package com.jobtracker.job_tracker.controller;

import com.jobtracker.job_tracker.model.JobApplication;
import com.jobtracker.job_tracker.service.JobApplicationService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobApplicationController {

    private final JobApplicationService service;

    public JobApplicationController(JobApplicationService service) {
        this.service = service;
    }

    @GetMapping
    public List<JobApplication> getJobs() {
        return service.getAllApplications();
    }

    @PostMapping
    public JobApplication addJob(@RequestBody JobApplication job) {
        return service.saveApplication(job);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        service.deleteApplication(id);
    }
}
