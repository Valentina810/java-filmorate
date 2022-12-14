package ru.yandex.practicum.filmorate.storage.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.FilmDto;
import ru.yandex.practicum.filmorate.model.GenreDto;
import ru.yandex.practicum.filmorate.model.MpaDto;
import ru.yandex.practicum.filmorate.storage.dao.FilmStorage;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Qualifier("FilmDao")
public class FilmDao implements FilmStorage {

	private final JdbcTemplate jdbcTemplate;
	private GenreDao genreDao;
	private MpaDao mpaDao;

	@Autowired
	public FilmDao(JdbcTemplate jdbcTemplate, GenreDao genreDao, MpaDao mpaDao) {
		this.jdbcTemplate = jdbcTemplate;
		this.genreDao = genreDao;
		this.mpaDao = mpaDao;
	}

	@Override
	public FilmDto getFilm(Long filmId) {
		try {
			FilmDto filmDto = jdbcTemplate.queryForObject(
					"SELECT * " +
							"FROM fr_film " +
							"WHERE film_id = ?",
					new Object[]{filmId}, ROW_MAPPER);
			filmDto.setMpa(getMpa(filmId));
			filmDto.setFilmGenre(getGenres(filmId));
			filmDto.setLikesFromUsers(getLikesFromUsers(filmId));
			return filmDto;
		} catch (DataAccessException dataAccessException) {
			throw new EntityNotFoundException(FilmDto.class.getSimpleName(),//#todo подумать, чтобы ошибка понятная была
					" с id " + filmId + " не найден!");
		}
	}

	private MpaDto getMpa(Long filmId) {
		List<MpaDto> mpaDto = jdbcTemplate.query(
				"SELECT * " +
						"FROM fr_mpa " +
						"WHERE mpa_id = (" +
						"SELECT f.mpa_id " +
						"FROM fr_film AS f " +
						"WHERE f.film_id= ?)",
				new Object[]{filmId}, mpaDao.ROW_MAPPER);
		if (!mpaDto.isEmpty()) {
			return mpaDto.iterator().next();
		} else return null;
	}

	private Set<GenreDto> getGenres(Long filmId) {
		return new HashSet<>(jdbcTemplate.query(
				"SELECT *" +
						"FROM fr_genre AS g " +
						"join fr_film_genre AS fg " +
						"ON g.genre_id=fg.genre_id " +
						"WHERE fg.film_id= ?",
				new Object[]{filmId}, genreDao.ROW_MAPPER));
	}

	private Set<Long> getLikesFromUsers(Long filmId) {
		return new HashSet<>(jdbcTemplate.query(
				"SELECT u.user_id " +
						"FROM fr_user AS u " +
						"INNER JOIN fr_film_likes AS fl " +
						"ON fl.user_id=u.user_id " +
						"WHERE fl.film_id= ?",
				new Object[]{filmId},
				(ResultSet resultSet, int rowNum) -> resultSet.getLong("user_ID")));
	}

	@Override
	public List<FilmDto> getFilms() {
		return null;
	}

	@Override
	public FilmDto addFilm(FilmDto filmDto) {
		return null;
	}

	@Override
	public FilmDto updateFilm(FilmDto filmDto) {
		return null;
	}

	@Override
	public List<FilmDto> getPopularMovies(Integer limit) {
		return null;
	}

	@Override
	public void addLike(Long idFilm, Long idUser) {

	}

	@Override
	public void deleteLike(Long idFilm, Long idUser) {

	}

	@Override
	public boolean validate(FilmDto filmDto) {
		return false;
	}
}