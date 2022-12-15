package ru.yandex.practicum.filmorate.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.dto.GenreDto;

import java.sql.ResultSet;
import java.util.List;

@Component
@Qualifier("GenreDao")
public class GenreRepository {
	RowMapper<GenreDto> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
		return GenreDto.builder()
				.id(resultSet.getInt("GENRE_ID"))
				.name(resultSet.getString("NAME"))
				.build();
	};

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public GenreRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<GenreDto> getAllGenre() {
		return jdbcTemplate.query(
				"SELECT * " +
						"FROM fr_genre", ROW_MAPPER);
	}

	public GenreDto getGenre(Long genreId) {
		try {
			return jdbcTemplate.queryForObject(
					"SELECT * " +
							"FROM fr_genre " +
							"WHERE genre_id = ?",
					new Object[]{genreId}, ROW_MAPPER);
		} catch (DataAccessException dataAccessException) {
			throw new EntityNotFoundException(GenreDto.class.getSimpleName(),
					" с id " + genreId + " не найден!");
		}
	}
}
