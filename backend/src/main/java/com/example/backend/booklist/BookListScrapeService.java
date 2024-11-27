package com.example.backend.booklist;

import com.example.backend.genre.GenreService;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookListScrapeService {

    private final GenreService genreService;

    @Autowired
    public BookListScrapeService(@Lazy GenreService genreService) {
        this.genreService = genreService;
    }

    public List<BookList> scrapeBookListsByGenreId(Long genreId) {
        ArrayList<BookList> bookLists = new ArrayList<>();
        var genre = genreService.findById(genreId);
        if (genre != null) {
            try {
                var doc = Jsoup.connect("https://www.goodreads.com/list/tag/" + genre.getLabel()).get();
                var bookListRows = doc.select(".listRowsFull .row");
                for (var bookListRow : bookListRows) {
                    var bookListElements = bookListRow.select(".cell");
                    for (var bookListElement : bookListElements) {
                        BookList bookList = new BookList();

                        var bookListAnchorTag = bookListElement.select(".listTitle").getFirst();
                        var name = bookListAnchorTag.text();
                        bookList.setName(name);

                        var href = bookListAnchorTag.attribute("href").getValue();
                        bookList.setHref(href);

                        var goodReadsId = href.substring("/list/show".length() + 1, href.indexOf('.'));
                        bookList.setGoodReadsId(Long.parseLong(goodReadsId));

                        var details = bookListElement.select(".listFullDetails").text();
                        var booksNumber = details.substring(
                                0, details.indexOf("books") - 1).replace(",", "");
                        bookList.setBooksNumber(Long.parseLong(booksNumber));

                        var votersNumber = details.substring(
                                details.indexOf("—") + 2,
                                details.indexOf("voters") - 1).replace(",", "");
                        bookList.setVotersNumber(Long.parseLong(votersNumber));
                        bookLists.add(bookList);
                    }
                }
                genreService.saveGenreWithBookLists(genre, bookLists);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return bookLists;
    }
}
