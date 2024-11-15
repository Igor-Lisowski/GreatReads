package com.example.backend.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobEntityRepository extends JpaRepository<Job, Long> {
}
