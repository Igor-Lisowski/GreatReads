package com.example.backend.job;

import com.example.backend.booklist.BookList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "book_list_id", referencedColumnName = "id")
    private BookList bookList;
    @Enumerated(EnumType.STRING)
    private JobStatus status;

    private int percentage;

    public void updatePercentage(int page, int totalPages) {
        this.percentage = (page * 100) / totalPages;
    }
}
