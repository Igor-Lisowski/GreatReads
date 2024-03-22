package com.example.backend.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {
    @Autowired
    GenreService genreService;

    @GetMapping()
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Genre> getGenres() {
        return genreService.findAll();
    }
}
