package com.example.backend.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDto {
    private Long id;
    private String name;
    private Double rating;
    private String author;
    private Integer ratingsNumber;
    private String href;
    private String imageUrl;
}
