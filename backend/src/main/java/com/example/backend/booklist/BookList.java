package com.example.backend.booklist;

import com.example.backend.book.Book;
import com.example.backend.genre.Genre;
import com.example.backend.job.Job;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "bookList")
public class BookList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private Long goodReadsId;
    private String name;
    private Long booksNumber;
    private Long votersNumber;
    private String href;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "bookList_genre",
            joinColumns = @JoinColumn(name = "bookList_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "book_bookList",
            joinColumns = @JoinColumn(name = "bookList_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;
    @OneToOne(mappedBy = "bookList", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY)
    private Job job;
}
