package com.example.demo.dto;

public class ApplicationResponseDTO {
    
    private Long id;
    private Long jobId;
    private String jobTitle;
    private String companyName;
    private String location;

    public ApplicationResponseDTO(Long id, String jobTitle, String companyName, String location) {
        this.id = id;
        this.jobId = id;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.location = location;
    }

    public ApplicationResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
