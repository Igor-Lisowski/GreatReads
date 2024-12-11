package com.example.backend.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query(value = "SELECT j FROM Job j WHERE j.bookList.id = ?1")
    Job findJobByBookListId(Long bookListId);
}
