package com.example.backend.book;

import com.example.backend.job.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    JobService jobService;

    @PostMapping("/scrape")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> scrapeBooksByBookListId(@RequestParam Long bookListId) {
        jobService.scrapeBooksByBookListId(bookListId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
