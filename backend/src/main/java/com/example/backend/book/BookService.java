package com.example.backend.book;

import com.example.backend.booklist.BookListService;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookListService bookListService;

    public Runnable scrapeBooksByBookListId(Long bookListId) {
        try {
            // TODO: ADD NULL CHECKS
            var bookListToUse = bookListService.findById(bookListId);
            var bookListPage = Jsoup.connect("https://www.goodreads.com" + bookListToUse.getHref()).get();
            var lastPage = bookListPage.select(".pagination a:nth-last-child(2)");
            var lastPageNumber = Integer.parseInt(lastPage.text());
            var pageNumber = 1;
            ArrayList<Book> books = new ArrayList<>();
            do {
                var bookElements = bookListPage.select("#all_votes .tableList tbody tr");
                for (var bookElement : bookElements) {
                    var anchorTag = bookElement.select("tr .bookTitle").getFirst();
                    var book = this.scrapeBook(anchorTag.attribute("href").getValue());
                    if (book != null) {
                        books.add(book);
                    }
                }
                pageNumber++;
                bookListPage = Jsoup.connect("https://www.goodreads.com" + bookListToUse.getHref() + "?page=" + pageNumber).get();
            } while (pageNumber < lastPageNumber);
            bookListToUse.setBooks(books);
            bookListService.save(bookListToUse);
        } catch (Exception e) {

        }
        return null;
    }

    private Book scrapeBook(String bookLink) {
        try {
            var bookPage = Jsoup.connect("https://www.goodreads.com" + bookLink).get();
            var book = new Book();

            var bookTitle = bookPage.select(".Text__title1").getFirst().text();
            book.setName(bookTitle);

            var authorName = bookPage.select(".ContributorLink__name").getFirst().text();
            book.setAuthor(authorName);

            var rating = bookPage.select(".RatingStatistics__rating").getFirst().text();
            book.setRating(Double.parseDouble(rating));

            var ratingsStatistics = bookPage.select(".RatingStatistics__meta").getFirst().attr("aria-label");
            var ratingsText = ratingsStatistics.substring(0, ratingsStatistics.indexOf("ratings") - 1);
            var ratingsNumber = Integer.parseInt(ratingsText.replace(",", ""));
            book.setRatingsNumber(ratingsNumber);

            var reviewsText = ratingsStatistics.substring(ratingsStatistics.indexOf("and") + "and".length() + 1,
                    ratingsStatistics.indexOf("reviews") - 1);
            var reviewsNumber = Integer.parseInt(reviewsText.replace(",", ""));
            book.setReviewsNumber(reviewsNumber);

            return book;

        } catch (Exception e) {

        }
        return null;
    }
}
