package com.example.backend.booklist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookListRepository extends JpaRepository<BookList, Long> {
    @Query("SELECT bl "
            + "FROM BookList bl "
            + "LEFT JOIN FETCH bl.job j "
            + "JOIN bl.genres g "
            + "WHERE g.id = :genreId")
    List<BookList> findAllByGenreId(@Param("genreId") Long genreId);

    @Query(value = "SELECT * FROM book_list WHERE good_reads_id IN ?1", nativeQuery = true)
    List<BookList> findAllByGoodReadsIds(List<Long> goodReadsIds);
}
