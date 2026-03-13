package com.jobtracker.job_tracker.service;

import com.jobtracker.job_tracker.model.JobApplication;
import com.jobtracker.job_tracker.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepository repository;

    public JobApplicationService(JobApplicationRepository repository) {
        this.repository = repository;
    }

    public List<JobApplication> getAllApplications() {
        return repository.findAll();
    }

    public JobApplication saveApplication(JobApplication app) {
        return repository.save(app);
    }

    public void deleteApplication(Long id) {
        repository.deleteById(id);
    }
}
