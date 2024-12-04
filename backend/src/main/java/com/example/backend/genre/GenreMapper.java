package com.example.backend.genre;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GenreMapper {
    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    GenreDto map(Genre genre);

    List<GenreDto> map(List<Genre> genres);
}
