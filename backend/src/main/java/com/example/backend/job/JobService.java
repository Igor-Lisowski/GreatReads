package com.example.backend.job;

import com.example.backend.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class JobService {
    @Autowired
    JobRepository jobRepository;

    @Autowired
    BookService bookService;

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Job-");
        executor.initialize();
        return executor;
    }

    @Async
    public void scrapeBooksByBookListId(Long bookListId) {
        CompletableFuture.runAsync(bookService.scrapeBooksByBookListId(bookListId));
    }


}
