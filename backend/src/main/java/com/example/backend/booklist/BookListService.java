package com.example.backend.booklist;

import com.example.backend.book.Book;
import com.example.backend.book.BookService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookListService {
    private final BookListRepository bookListRepository;
    private final BookListScrapeService bookListScrapeService;
    private final BookService bookService;

    @Autowired
    public BookListService(BookListRepository bookListRepository, @Lazy BookListScrapeService bookListScrapeService,
                           BookService bookService) {
        this.bookListRepository = bookListRepository;
        this.bookListScrapeService = bookListScrapeService;
        this.bookService = bookService;
    }

    public BookList findById(Long id) throws EntityNotFoundException {
        Optional<BookList> optional = bookListRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new EntityNotFoundException("BookList for given id: " + id + " not present.");
        }
    }

    public List<BookListDto> findAllByGenreId(Long genreId) {
        var lists = this.bookListRepository.findAllByGenreId(genreId);
        if (!lists.isEmpty()) {
            return BookListMapper.INSTANCE.map(lists);
        }
        return bookListScrapeService.scrapeBookListsByGenreId(genreId);
    }

    public BookList save(BookList bookList) {
        return bookListRepository.save(bookList);
    }

    public List<BookList> findAllByGoodReadsIds(List<Long> goodReadsIds) {
        return bookListRepository.findAllByGoodReadsIds(goodReadsIds);
    }

    public List<BookList> saveAll(List<BookList> bookLists) {
        return bookListRepository.saveAll(bookLists);
    }

    public BookList saveBookListWithBooks(BookList bookList, List<Book> books) {
        List<Book> alreadySavedBooks = bookService.findAllByHrefs(
                books.stream().map(Book::getHref).toList()
        );

        List<Book> notSavedBooks = books.stream()
                .filter(book -> alreadySavedBooks.stream()
                        .noneMatch(savedBook -> savedBook.getHref().equals(book.getHref())))
                .collect(Collectors.toList());

        List<Book> savedBooks = bookService.saveAll(notSavedBooks);

        List<Book> mergedBooks = Stream.concat(alreadySavedBooks.stream(), savedBooks.stream())
                .collect(Collectors.toList());

        bookList.setBooks(mergedBooks);
        return bookListRepository.save(bookList);
    }
}
