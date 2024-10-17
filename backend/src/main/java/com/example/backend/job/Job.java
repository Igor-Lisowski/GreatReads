package com.example.backend.job;

import com.example.backend.booklist.BookList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_list_id", referencedColumnName = "id")
    private BookList bookList;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("PENDING")
    private JobStatus status;

    private int percentage;

    public Job(BookList bookList) {
        this.bookList = bookList;
    }

    public void updatePercentage(int page, int totalPages) {
        this.percentage = (page * 100) / totalPages;
    }
}
