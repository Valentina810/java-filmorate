package ru.yandex.practicum.filmorate.storage.dao.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.GenreDto;

import java.sql.ResultSet;

@Component
@Qualifier("GenreDao")
public class GenreDao {
	RowMapper<GenreDto> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
		return GenreDto.builder()
				.id(resultSet.getInt("GENRE_ID"))
				.name(resultSet.getString("NAME"))
				.build();
	};
}
