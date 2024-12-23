package com.example.backend.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.href IN :hrefs")
    List<Book> findAllByHrefIn(@Param("hrefs") List<String> hrefs);

    @Query("SELECT b FROM Book b JOIN b.bookLists bl JOIN bl.genres g WHERE g.id = :genreId")
    List<Book> findBooksByGenreId(@Param("genreId") Long genreId);
}
