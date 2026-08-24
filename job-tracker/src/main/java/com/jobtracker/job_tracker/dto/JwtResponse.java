package com.jobtracker.job_tracker.dto;

public record JwtResponse(String accessToken, String refreshToken, String tokenType) {}
