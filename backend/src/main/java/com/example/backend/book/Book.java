package com.example.backend.book;

import com.example.backend.booklist.BookList;
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
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private Long goodReadsId;
    private String name;
    private Double rating;
    private String author;
    private Integer ratingsNumber;
    private Integer reviewsNumber;
    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id",
                    referencedColumnName = "id"))
    private List<Genre> genres;
    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "book_bookList",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "bookList_id",
                    referencedColumnName = "id"))
    private List<BookList> bookLists;
}
