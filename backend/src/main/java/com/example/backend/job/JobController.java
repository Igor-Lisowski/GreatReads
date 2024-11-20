package com.example.backend.job;

import com.example.backend.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    TaskService taskService;

    @PostMapping("scrape")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Void> scrapeBookList(@RequestParam Long bookListId) {
        taskService.scrapeBooksByBookListId(bookListId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
