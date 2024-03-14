package com.example.backend.genres;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenresService {
    @Autowired
    GenresRepository genresRepository;

    public List<Genre> findAll() {
        var genres = this.genresRepository.findAll();
        if (!genres.isEmpty()) {
            return genres;
        }
        return scrapeGenres();
    }

    private List<Genre> scrapeGenres() {
        ArrayList<Genre> genres = new ArrayList<>();
        try {
            var doc = Jsoup.connect("https://www.goodreads.com").get();
            var genreColumnContainers = doc.select("#browseBox div.u-defaultType div");
            for (var genreColumnContainer : genreColumnContainers) {
                var genreAnchorElements = genreColumnContainer.getElementsByTag("a");
                for (var genreAnchorElement : genreAnchorElements) {
                    var hrefValue = genreAnchorElement.attribute("href").getValue();
                    if (!hrefValue.contains("/genres/")) {
                        continue;
                    }
                    Genre genre = getGenre(hrefValue);
                    genres.add(this.genresRepository.save(genre));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return genres;
    }

    private static Genre getGenre(String hrefValue) {
        var name = hrefValue.substring("/genres/".length());
        Genre genre = new Genre();
        genre.setName(name);
        return genre;
    }
}
