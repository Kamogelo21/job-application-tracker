package com.jobtracker.job_tracker.service;

import com.jobtracker.job_tracker.dto.JobApplicationDTO;
import com.jobtracker.job_tracker.model.JobApplication;
import com.jobtracker.job_tracker.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    private final JobApplicationRepository repository;

    public JobApplicationService(JobApplicationRepository repository) {
        this.repository = repository;
    }

    public List<JobApplicationDTO> getAllJobs() {

        List<JobApplication> jobs = jobApplicationRepository.findAll();

        return jobs.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public JobApplicationDTO saveApplication(JobApplicationDTO dto) {

        JobApplication job = convertToEntity(dto);

        JobApplication saved = jobApplicationRepository.save(job);

        return convertToDTO(saved);
    }

    private JobApplication convertToEntity(JobApplicationDTO dto) {

        JobApplication job = new JobApplication();

        job.setCompany(dto.getCompany());
        job.setPosition(dto.getPosition());
        job.setStatus(dto.getStatus());

        return job;
    }

    public void deleteApplication(Long id) {
        repository.deleteById(id);
    }

    public JobApplication updateJob(Long id, JobApplication updatedJob) {

        JobApplication job = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setTitle(updatedJob.getTitle());
        job.setCompany(updatedJob.getCompany());
        job.setStatus(updatedJob.getStatus());

        return jobApplicationRepository.save(job);
    }

    private JobApplicationDTO convertToDTO(JobApplication job) {

        JobApplicationDTO dto = new JobApplicationDTO();

        dto.setId(job.getId());
        dto.setCompany(job.getCompany());
        dto.setPosition(job.getPosition());
        dto.setStatus(job.getStatus());

        return dto;
    }
}
