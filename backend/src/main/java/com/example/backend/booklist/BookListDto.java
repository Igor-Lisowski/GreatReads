package com.example.backend.booklist;

import com.example.backend.job.JobDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookListDto {
    private Long id;
    private Long goodReadsId;
    private String name;
    private Long booksNumber;
    private Long votersNumber;
    private String href;
    private JobDto job;
}
