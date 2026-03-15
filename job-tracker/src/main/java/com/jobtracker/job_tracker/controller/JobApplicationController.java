package com.jobtracker.job_tracker.controller;

import com.jobtracker.job_tracker.dto.JobApplicationDTO;
import com.jobtracker.job_tracker.model.JobApplication;
import com.jobtracker.job_tracker.service.JobApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobApplicationController {

    @Autowired
    private JobApplicationService service;

    @GetMapping
    public List<JobApplicationDTO> getAllJobs() {
        return service.getAllJobs();
    }

    @PostMapping
    public JobApplicationDTO addJob(@RequestBody JobApplicationDTO jobDTO) {
        return service.saveApplication(jobDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        service.deleteApplication(id);
    }

    @PutMapping("/{id}")
    public JobApplication updateJob(@PathVariable Long id, @RequestBody JobApplication jobApplication) {
        return service.updateJob(id, jobApplication);
    }
}
