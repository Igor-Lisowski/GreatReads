package com.example.backend.booklist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookListRepository extends JpaRepository<BookList, Long> {
    @Query(value = """
            SELECT bl.id, bl.books_number, bl.fetch_state, bl.good_reads_id, bl.name, bl.voters_number, bl.href,\s
            j.status\s
            FROM book_list bl\s
            INNER JOIN book_list_genre blg on bl.id = blg.book_list_id\s
            INNER JOIN genre g on blg.genre_id = g.id\s
            LEFT JOIN job j on bl.id = j.book_list_id\s
            WHERE g.id = ?1""", nativeQuery = true)
    List<BookList> findAllByGenreId(Long genreId);
}
