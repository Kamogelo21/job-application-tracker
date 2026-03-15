package com.jobtracker.job_tracker.repository;

import com.jobtracker.job_tracker.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface JobApplicationRepository
        extends JpaRepository<JobApplication, Long> {
}
