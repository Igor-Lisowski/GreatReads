package com.example.backend.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    JobService jobService;

    @PostMapping("scrape")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Void> scrapeBookList(@RequestParam Long bookListId) {
        jobService.scrapeBooksByBookListId(bookListId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
