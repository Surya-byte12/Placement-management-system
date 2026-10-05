package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;

public class ApplicationRequestDTO {
    
    @NotNull(message = "Job ID is required")
    private Long jobId;

    public ApplicationRequestDTO() {
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}
