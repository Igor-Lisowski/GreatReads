package com.example.backend.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobEntityService {

    private final JobEntityRepository jobEntityRepository;

    @Autowired
    public JobEntityService(JobEntityRepository jobRepository) {
        this.jobEntityRepository = jobRepository;
    }

    public Job save(Job job) {
        return jobEntityRepository.save(job);
    }
}
