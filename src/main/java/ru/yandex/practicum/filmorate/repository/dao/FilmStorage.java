package ru.yandex.practicum.filmorate.repository.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.dto.MpaDto;

import java.sql.ResultSet;
import java.util.List;

public interface FilmStorage {
	RowMapper<FilmDto> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
		return FilmDto.builder()
				.id(resultSet.getLong("FILM_ID"))
				.name(resultSet.getString("NAME"))
				.description(resultSet.getString("DESCRIPTION"))
				.releaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate())
				.duration(resultSet.getInt("DURATION"))
				.countLikes(resultSet.getInt("COUNT_LIKES"))
				.mpa(MpaDto.builder()
						.id(resultSet.getInt("MPA_ID"))
						.name(resultSet.getString("MPA_NAME")).build())
				.build();
	};

	FilmDto getFilm(Long filmId);

	List<FilmDto> getFilms();

	FilmDto addFilm(FilmDto filmDto);

	FilmDto updateFilm(FilmDto filmDto);

	List<FilmDto> getPopularMovies(Integer limit);

	void addLike(Long idFilm, Long idUser);

	void deleteLike(Long idFilm, Long idUser);

	boolean validate(FilmDto filmDto);
}