package com.example.backend.lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-list")
public class BookListController {
    @Autowired
    BookListService bookListService;

    @GetMapping()
    @CrossOrigin(origins = "http://localhost:3000")
    public List<BookList> getBookListsForGenreId(@RequestParam Long genreId) {
        return bookListService.findAllForGenreId(genreId);
    }
}
