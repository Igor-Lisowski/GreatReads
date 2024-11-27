package com.example.backend.booklist;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookListService {
    private final BookListRepository bookListRepository;
    private final BookListScrapeService bookListScrapeService;

    @Autowired
    public BookListService(BookListRepository bookListRepository, @Lazy BookListScrapeService bookListScrapeService) {
        this.bookListRepository = bookListRepository;
        this.bookListScrapeService = bookListScrapeService;
    }

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
}
