package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.JobRequestDTO;
import com.example.demo.dto.JobResponseDTO;
import com.example.demo.entity.Job;
import com.example.demo.service.JobService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @PutMapping("/jobs/{id}")
    public String updateJob(
            @PathVariable Long id,
            @RequestBody Job job) {

        return "Updating job " + id;
    }

    @DeleteMapping("/jobs/{id}")
    public String deleteJob(@PathVariable Long id) {
        return "Deleting job " + id;
    }

    @PostMapping("/jobs")
    public ResponseEntity<JobResponseDTO> createJob(
            @Valid @RequestBody JobRequestDTO request) {

        JobResponseDTO response = jobService.createJob(request);

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {

        Job job = jobService.getJobById(id);

        return ResponseEntity.ok(job);
    }
}