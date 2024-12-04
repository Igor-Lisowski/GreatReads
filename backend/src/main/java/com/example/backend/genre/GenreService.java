package com.example.backend.genre;

import com.example.backend.booklist.BookList;
import com.example.backend.booklist.BookListService;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final BookListService bookListService;

    @Autowired
    public GenreService(
            GenreRepository genreRepository,
            @Lazy BookListService bookListService
    ) {
        this.genreRepository = genreRepository;
        this.bookListService = bookListService;
    }

    public Genre findById(Long id) {
        Optional<Genre> optional = this.genreRepository.findById(id);
        return optional.orElse(null);
    }

    public List<GenreDto> findAll() {
        var genres = this.genreRepository.findAll();
        if (!genres.isEmpty()) {
            return GenreMapper.INSTANCE.map(genres);
        }
        return scrapeGenres();
    }

    private List<GenreDto> scrapeGenres() {
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
                    genres.add(this.genreRepository.save(genre));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return GenreMapper.INSTANCE.map(genres);
    }

    private static Genre getGenre(String hrefValue) {
        var name = hrefValue.substring("/genres/".length());
        Genre genre = new Genre();
        genre.setLabel(name);
        return genre;
    }

    public Genre saveGenreWithBookLists(Genre genre, List<BookList> bookLists) {
        List<BookList> alreadySavedBookLists = bookListService.findAllByGoodReadsIds(
                bookLists.stream().map(BookList::getGoodReadsId).toList()
        );

        List<BookList> notSavedBookLists = bookLists.stream()
                .filter(bookList -> alreadySavedBookLists.stream()
                        .noneMatch(savedBookList -> savedBookList.getGoodReadsId().equals(bookList.getGoodReadsId())))
                .collect(Collectors.toList());

        List<BookList> savedBookLists = bookListService.saveAll(notSavedBookLists);

        List<BookList> mergedBookLists = Stream.concat(alreadySavedBookLists.stream(), savedBookLists.stream())
                .collect(Collectors.toList());

        genre.setBookLists(mergedBookLists);
        return genreRepository.save(genre);
    }
}
