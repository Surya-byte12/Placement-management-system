package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.JobRequestDTO;
import com.example.demo.dto.JobResponseDTO;
import com.example.demo.entity.Company;
import com.example.demo.entity.Job;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.JobRepository;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    public JobService(JobRepository jobRepository, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job getJobById(Long id) {

        return jobRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Job not found with id: " + id
                    )
                );
    }

    public JobResponseDTO createJob(JobRequestDTO request) {

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Company not found with id: " + request.getCompanyId()
                    )
                );

        Job job = new Job();

        job.setTitle(request.getTitle());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());

        job.setCompany(company);

        Job savedJob = jobRepository.save(job);

        JobResponseDTO response = new JobResponseDTO();

        response.setId(savedJob.getId());
        response.setTitle(savedJob.getTitle());
        response.setLocation(savedJob.getLocation());
        response.setSalary(savedJob.getSalary());
        response.setCompanyName(savedJob.getCompany().getName());

        return response;
    }}