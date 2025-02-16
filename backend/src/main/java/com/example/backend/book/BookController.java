package com.example.backend.book;

import com.example.backend.job.Job;
import com.example.backend.job.JobService;
import com.example.backend.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final TaskService taskService;
    private final JobService jobService;

    @Autowired
    public BookController(BookService bookService, TaskService taskService, JobService jobService) {
        this.bookService = bookService;
        this.taskService = taskService;
        this.jobService = jobService;
    }

    @PostMapping("/scrape")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Job> scrapeBooksByBookListId(@RequestParam Long bookListId) {
        Job job = jobService.createJobForBookListId(bookListId);
        taskService.scrapeBooksByBookListId(bookListId);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @GetMapping()
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<BookDto>> getBooksByGenreId(
            @RequestParam Long genreId,
            @RequestParam(defaultValue = "1") Integer pageNumber) {
        return new ResponseEntity<>(bookService.findBooksByGenreId(genreId, pageNumber), HttpStatus.OK);
    }

    @GetMapping("/count")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Integer> countBooksByGenreId(@RequestParam Long genreId) {
        return new ResponseEntity<>(bookService.countBooksByGenreId(genreId), HttpStatus.OK);
    }
}
