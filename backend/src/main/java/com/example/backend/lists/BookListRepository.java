package com.example.backend.lists;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookListRepository extends JpaRepository<BookList, Long> {
    @Query(value = """
            select bl.id, bl.books_number, bl.fetch_state, bl.good_reads_id, bl.name, bl.voters_number\s
            from book_list bl\s
            inner join book_list_genre blg on bl.id = blg.book_list_id\s
            inner join genre on blg.genre_id = genre.id
            where genre.id = ?1""", nativeQuery = true)
    List<BookList> findAllByGenreId(Long genreId);
}
