package com.example.backend.genres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenresController {
    @Autowired
    GenresService genresService;

    @GetMapping()
    public List<Genre> getGenres() {
        return genresService.findAll();
    }
}
