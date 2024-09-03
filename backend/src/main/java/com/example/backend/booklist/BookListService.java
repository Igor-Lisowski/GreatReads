package com.example.backend.booklist;

import com.example.backend.genre.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookListService {
    @Autowired
    BookListRepository bookListRepository;
    @Autowired
    GenreRepository genreRepository;

    public BookList findById(Long id) throws EntityNotFoundException {
        Optional<BookList> optional = bookListRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new EntityNotFoundException("BookList for given id: " + id + " not present.");
        }
    }

    public List<BookList> findAllByGenreId(Long genreId) {
        var lists = this.bookListRepository.findAllByGenreId(genreId);
        if (!lists.isEmpty()) {
            return lists;
        }
        return scrapeBookListsByGenreId(genreId);
    }

    private List<BookList> scrapeBookListsByGenreId(Long genreId) {
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
                        try {
                            bookLists.add(bookListRepository.save(bookList));
                        } catch (Exception exception) {
                            // TODO:
                            //  Handle exception making sure that it is related to key duplication or implement isNew check
                            // TODO:
                            //  Handle saving same bookList, but with different genre
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return bookLists;
    }

    public BookList save(BookList bookList) {
        return bookListRepository.save(bookList);
    }
}
