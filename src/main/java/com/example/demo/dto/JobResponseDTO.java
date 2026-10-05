package com.example.demo.dto;

public class JobResponseDTO {
    
    private Long id;
    private String title;
    private String location;
    private String salary;
    private String companyName;

    public JobResponseDTO() {}

    public JobResponseDTO(Long id, String title, String location, String salary, String companyName) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.salary = salary;
        this.companyName = companyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

