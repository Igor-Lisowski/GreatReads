package com.example.backend.booklist;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookListMapper {
    BookListMapper INSTANCE = Mappers.getMapper(BookListMapper.class);

    @Mappings({
            @Mapping(source = "job", target = "job")
    })
    BookListDto toDto(BookList bookList);

    List<BookListDto> map(List<BookList> bookLists);
}
