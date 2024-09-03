package com.example.backend.booklist;

import com.example.backend.book.Book;
import com.example.backend.genre.Genre;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Enumerated(EnumType.STRING)
    private FetchState fetchState = FetchState.NOT_FETCHED;
    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "bookList_genre",
            joinColumns = @JoinColumn(name = "bookList_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id",
                    referencedColumnName = "id"))
    private List<Genre> genres;
    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_bookList",
            joinColumns = @JoinColumn(name = "bookList_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_id",
                    referencedColumnName = "id"))
    private List<Book> books;
}
