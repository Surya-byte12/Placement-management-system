package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Application;

import java.util.List;

public interface ApplicationRepository
         extends JpaRepository<Application, Long> {
    
            boolean existsByUserIdAndJobId(Long userId, Long jobId);

            List<Application> findByUserId(Long userId);

            List<Application> findByJob_Company_Recruiter_Id(Long recruiterId);
}
