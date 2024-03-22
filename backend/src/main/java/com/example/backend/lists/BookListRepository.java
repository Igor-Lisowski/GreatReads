package com.example.backend.lists;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookListRepository extends JpaRepository<BookList, Long> {
}
