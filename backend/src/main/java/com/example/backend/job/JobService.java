package com.example.backend.job;

import com.example.backend.booklist.BookList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job save(Job job) {
        return jobRepository.save(job);
    }

    public Job createJobForBookListId(Long bookListId) {
        Job job = new Job();
        job.setStatus(JobStatus.PENDING);
        BookList bookList = new BookList();
        bookList.setId(bookListId);
        job.setBookList(bookList);
        return jobRepository.save(job);
    }

    public Job findJobByBookListId(Long bookListId) {
        return jobRepository.findJobByBookListId(bookListId);
    }
}
