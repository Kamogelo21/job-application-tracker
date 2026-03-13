package com.jobtracker.job_tracker.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;

    private String position;

    private String location;

    private String status;
    // APPLIED, INTERVIEW, OFFER, REJECTED

    private String jobLink;

    private String notes;
}