package com.example.backend.book;

import com.example.backend.booklist.BookListService;
import com.example.backend.job.Job;
import com.example.backend.job.JobService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookListService bookListService;
    private final JobService jobService;
    private final BookRepository bookRepository;

    @Autowired
    public BookService(@Lazy BookListService bookListService, JobService jobService, BookRepository bookRepository) {
        this.bookListService = bookListService;
        this.jobService = jobService;
        this.bookRepository = bookRepository;
    }

    public void scrapeBooksByBookListId(Long bookListId) {
        try {
            var bookListToUse = bookListService.findById(bookListId);  // TODO: add error handling
            Job job = jobService.findJobByBookListId(bookListId);


            var bookListPage = Jsoup.connect("https://www.goodreads.com" + bookListToUse.getHref()).get(); // TODO: add error handling
            var lastPage = bookListPage.select(".pagination a:nth-last-child(2)"); // TODO: add error handling
            var lastPageNumber = Integer.parseInt(lastPage.text()); // TODO: add error handling
            var pageNumber = 1;

            ArrayList<Book> books = new ArrayList<>();
            do {
                var bookRows = bookListPage.select("#all_votes .tableList tbody tr"); // TODO: add error handling
                for (var bookRow : bookRows) {
                    var book = this.scrapeBook(bookRow);
                    books.add(book);
                }
                job.updatePercentage(pageNumber, lastPageNumber);
                jobService.save(job);
                pageNumber++;
                // TODO: add error handling
                bookListPage = Jsoup.connect("https://www.goodreads.com" + bookListToUse.getHref() + "?page=" + pageNumber).get();
            } while (pageNumber <= lastPageNumber);

            bookListToUse.setJob(job);
            bookListService.saveBookListWithBooks(bookListToUse, books);
        } catch (Exception e) {
            System.out.println("error"); // TODO: handle errors
        }
    }

    private Book scrapeBook(Element bookRow) {

        Book book = new Book();
        try {
            Optional<Element> imageTag = Optional.ofNullable(bookRow.select("td:nth-child(2) div a img").getFirst());
            imageTag.ifPresent(tag -> book.setImageUrl(tag.attribute("src").getValue()));

            Optional<Element> bookTitleAnchorTag = Optional.ofNullable(bookRow.select(".bookTitle").getFirst());
            bookTitleAnchorTag.ifPresent(tag -> {
                book.setHref(tag.attribute("href").getValue());
                Optional<Element> bookTitleSpanTag = Optional.ofNullable(tag.select("span").getFirst());
                bookTitleSpanTag.ifPresent(spanTag -> {
                    String spanTagText = spanTag.text();
                    if (spanTagText.endsWith(")")) {
                        int lastOpeningBracketIndex = spanTagText.lastIndexOf("(");
                        String name = spanTagText.substring(0, lastOpeningBracketIndex - 1);
                        book.setName(name);
                    } else {
                        book.setName(spanTagText);
                    }
                });
            });

            Optional<Element> authorNameSpanTag = Optional.ofNullable(bookRow.select(".authorName span").getFirst());
            authorNameSpanTag.ifPresent(tag -> book.setAuthor(tag.text()));

            Optional<Element> ratingTag = Optional.ofNullable(bookRow.select(".minirating").getFirst());
            if (ratingTag.isPresent()) {
                var ratingText = ratingTag.get().ownText();
                var avgRatingIdx = ratingText.indexOf(" avg rating");
                if (avgRatingIdx > 0) {
                    var rating = ratingText.substring(0, ratingText.indexOf(" avg rating"));
                    book.setRating(Double.parseDouble(rating));
                }

                var dashIndex = ratingText.indexOf("—");
                var ratingIndex = ratingText.lastIndexOf(" rating");
                if (dashIndex > 0 && ratingIndex > 0 && dashIndex < ratingIndex) {
                    var ratings = ratingText.substring(dashIndex + 2, ratingIndex).replace(",", "");
                    book.setRatingsNumber(Integer.parseInt(ratings));
                }
            }
        } catch (NullPointerException e) {
            System.out.println("error"); // TODO: handle errors
        }
        return book;
    }

    public List<Book> findAllByHrefs(List<String> hrefs) {
        return bookRepository.findAllByHrefIn(hrefs);
    }

    public List<Book> saveAll(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    public List<BookDto> findBooksByGenreId(Long genreId, Integer pageNumber) {
        return BookMapper.INSTANCE.map(bookRepository.findBooksByGenreId(genreId, PageRequest.of(pageNumber - 1, 100)));
    }

    public Integer countBooksByGenreId(Long genreId) {
        return bookRepository.countBooksByGenreId(genreId);
    }
}
