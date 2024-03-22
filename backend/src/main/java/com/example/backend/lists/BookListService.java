package com.example.backend.lists;

import com.example.backend.genre.GenreRepository;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookListService {
    @Autowired
    BookListRepository bookListRepository;
    @Autowired
    GenreRepository genreRepository;

    public List<BookList> findAllForGenreId(Long genreId) {
        var lists = this.bookListRepository.findAll();
        if (!lists.isEmpty()) {
            return lists;
        }
        return scrapeBookListsForGenreId(genreId);
    }

    private List<BookList> scrapeBookListsForGenreId(Long genreId) {
        ArrayList<BookList> bookLists = new ArrayList<>();
        var genreToUse = genreRepository.findById(genreId);
        if (genreToUse.isPresent()) {
            var genre = genreToUse.get();
            try {
                var doc = Jsoup.connect("https://www.goodreads.com/list/tag/" + genre.getLabel()).get();
                var bookListRows = doc.select(".listRowsFull .row");
                for (var bookListRow : bookListRows) {
                    var bookListElements = bookListRow.select(".cell");
                    for (var bookListElement : bookListElements) {
                        BookList bookList = new BookList();
                        var genres = new ArrayList<>(List.of(genre));
                        bookList.setGenres(genres);

                        var bookListAnchorTag = bookListElement.select(".listTitle").getFirst();
                        var name = bookListAnchorTag.text();
                        bookList.setName(name);

                        var hrefValue = bookListAnchorTag.attribute("href").getValue();
                        var goodReadsId = hrefValue.substring("/list/show".length() + 1, hrefValue.indexOf('.'));
                        bookList.setGoodReadsId(Long.parseLong(goodReadsId));

                        var details = bookListElement.select(".listFullDetails").text();
                        var booksNumber = details.substring(
                                0, details.indexOf("books") - 1).replace(",", "");
                        bookList.setBooksNumber(Long.parseLong(booksNumber));

                        var votersNumber = details.substring(
                                details.indexOf("—") + 2,
                                details.indexOf("voters") - 1).replace(",", "");
                        bookList.setVotersNumber(Long.parseLong(votersNumber));

                        bookLists.add(bookListRepository.save(bookList));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return bookLists;
    }
}
