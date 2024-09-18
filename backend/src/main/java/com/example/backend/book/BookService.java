package com.example.backend.book;

import com.example.backend.booklist.BookListService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

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
                var bookRows = bookListPage.select("#all_votes .tableList tbody tr");
                for (var bookRow : bookRows) {
                    var book = this.scrapeBook(bookRow);
                    books.add(book);
                }
                pageNumber++;
                bookListPage = Jsoup.connect("https://www.goodreads.com" + bookListToUse.getHref() + "?page=" + pageNumber).get();
            } while (pageNumber <= lastPageNumber);
            bookListToUse.setBooks(books);
            bookListService.save(bookListToUse);
        } catch (Exception e) {

        }
        return null;
    }

    private Book scrapeBook(Element bookRow) {
        Book book = new Book();

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
        return book;
    }
}
