package ru.yandex.practicum.filmorate.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exception.EntityValidationException;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.dto.GenreDto;
import ru.yandex.practicum.filmorate.repository.dao.FilmStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Component
@Qualifier("FilmDao")
public class FilmRepository implements FilmStorage {

	private final JdbcTemplate jdbcTemplate;
	private GenreRepository genreRepository;

	@Autowired
	public FilmRepository(JdbcTemplate jdbcTemplate, GenreRepository genreRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.genreRepository = genreRepository;
	}

	private static final LocalDate MIN_DATE_RELEASE = LocalDate.of(1895, 12, 28);

	@Override
	public FilmDto getFilm(Long filmId) {
		try {
			FilmDto filmDto = jdbcTemplate.queryForObject(
					"SELECT fi.film_id, fi.name,fi.description,fi.release_date,fi.duration, fi.count_likes," +
							"m.mpa_id,m.name AS mpa_name " +
							"FROM fr_film AS fi " +
							"LEFT JOIN fr_mpa AS m " +
							"ON fi.mpa_id=m.mpa_id " +
							"WHERE film_id = ?",
					new Object[]{filmId}, ROW_MAPPER);
			filmDto.setGenres(getGenres(filmId));
			filmDto.setLikesFromUsers(getLikesFromUsers(filmId));
			return filmDto;
		} catch (DataAccessException dataAccessException) {
			throw new EntityNotFoundException(FilmDto.class.getSimpleName(),
					" с id " + filmId + " не найден!");
		}
	}

	/**
	 * Получить список жанров для конкретного фильма
	 *
	 * @param filmId - идентификатор фильма
	 * @return - список жанров указанного фильма
	 */
	private LinkedHashSet<GenreDto> getGenres(Long filmId) {
		return new LinkedHashSet<>(jdbcTemplate.query(
				"SELECT g.* " +
						"FROM fr_genre AS g " +
						"join fr_film_genre AS fg " +
						"ON g.genre_id=fg.genre_id " +
						"WHERE fg.film_id= ? ",
				new Object[]{filmId}, genreRepository.ROW_MAPPER));
	}

	/**
	 * Получить список пользователей, лайкнувших фильм
	 *
	 * @param filmId - идентификатор фильма
	 * @return - список идентификаторов пользователей
	 */
	private Set<Long> getLikesFromUsers(Long filmId) {
		return new HashSet<>(jdbcTemplate.query(
				"SELECT u.user_id " +
						"FROM fr_user AS u " +
						"INNER JOIN fr_film_likes AS fl " +
						"ON fl.user_id=u.user_id " +
						"WHERE fl.film_id= ?",
				new Object[]{filmId},
				(ResultSet resultSet, int rowNum) -> resultSet.getLong("user_id")));
	}

	@Override
	public List<FilmDto> getFilms() {
		List<FilmDto> query = jdbcTemplate.query(
				"SELECT fi.film_id, fi.name,fi.description, " +
						"fi.release_date,fi.duration, fi.count_likes," +
						"m.mpa_id,m.name AS mpa_name " +
						"FROM fr_film AS fi " +
						"LEFT JOIN fr_mpa AS m " +
						"ON fi.mpa_id=m.mpa_id", ROW_MAPPER);
		query.forEach(e -> e.setGenres(getGenres(e.getId())));
		query.forEach(e -> e.setLikesFromUsers(getLikesFromUsers(e.getId())));
		return query;
	}

	@Override
	public FilmDto addFilm(FilmDto filmDto) {
		if (validate(filmDto)) {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(
						"INSERT INTO FR_FILM(name, description, release_date, duration,count_likes,mpa_id) " +
								"VALUES (?, ?, ?, ?, ?, ?)",
						new String[]{"film_id"});
				ps.setString(1, filmDto.getName());
				ps.setString(2, filmDto.getDescription());
				ps.setString(3, String.valueOf(filmDto.getReleaseDate()));
				ps.setString(4, String.valueOf(filmDto.getDuration()));
				ps.setString(5, String.valueOf(filmDto.getCountLikes()));
				ps.setString(6, String.valueOf(filmDto.getMpa().getId()));
				return ps;
			}, keyHolder);
			filmDto.setId(keyHolder.getKey().longValue());
			insertLikesFromUsers(filmDto);
			insertGenreFilm(filmDto);
		}
		return filmDto;
	}

	/**
	 * Добавляет записи в таблицу fr_film_genre на основании поля genres filmDto
	 *
	 * @param filmDto - объект, данные о котором добавляются в бд
	 */
	private void insertGenreFilm(FilmDto filmDto) {
		if (filmDto.getGenres().size() > 0) {
			StringBuilder sql = new StringBuilder("INSERT INTO fr_film_genre (film_id,genre_id) VALUES");
			Integer idGenre = filmDto.getGenres().iterator().next().getId();
			sql.append(" (" + filmDto.getId() + ", " + idGenre + ")");
			filmDto.getGenres().forEach(e -> {
				if (e.getId() != idGenre) {
					sql.append(", (" + filmDto.getId() + ", " + e.getId() + ")");
				}
			});
			jdbcTemplate.update(sql.toString());
		}
	}

	/**
	 * Добавляет записи в таблицу fr_film_likes на основании поля LikesFromUsers filmDto
	 *
	 * @param filmDto - объект, данные о котором добавляются в бд
	 */
	private void insertLikesFromUsers(FilmDto filmDto) {
		if (filmDto.getLikesFromUsers().size() > 0) {
			StringBuilder sql = new StringBuilder("INSERT INTO fr_film_likes (film_id,user_id) VALUES");
			Long next = filmDto.getLikesFromUsers().iterator().next();
			sql.append(" (" + filmDto.getId() + ", " + next + ")");
			filmDto.getLikesFromUsers().forEach(e -> {
				if (e != next) {
					sql.append(", (" + filmDto.getId() + ", " + e + ")");
				}
			});
			jdbcTemplate.update(sql.toString());
		}
	}

	@Override
	public FilmDto updateFilm(FilmDto filmDto) {
		if (validate(filmDto)) {
			if (getFilm(filmDto.getId()) != null) {
				jdbcTemplate.update("DELETE FROM fr_film_genre WHERE film_id = ?", filmDto.getId());
				jdbcTemplate.update("DELETE FROM fr_film_likes WHERE film_id = ?", filmDto.getId());
				jdbcTemplate.update("UPDATE fr_film " +
								"SET name=?, description=?, release_date=?, duration=?,count_likes=?,mpa_id=? " +
								"WHERE film_id = ?",
						filmDto.getName(),
						filmDto.getDescription(),
						filmDto.getReleaseDate(),
						filmDto.getDuration(),
						filmDto.getCountLikes(),
						filmDto.getMpa().getId(),
						filmDto.getId());
				insertLikesFromUsers(filmDto);
				insertGenreFilm(filmDto);
			}
		}
		return filmDto;
	}

	/**
	 * Извлекает список самых популярных фильмов
	 *
	 * @param limit - максимальное количество фильмов в списке
	 * @return - список фильмов, отсортированный по количству лайков
	 */
	@Override
	public List<FilmDto> getPopularMovies(Integer limit) {
		List<FilmDto> query = jdbcTemplate.query(
				"SELECT fi.film_id, fi.name,fi.description, " +
						"fi.release_date,fi.duration, fi.count_likes," +
						"m.mpa_id,m.name AS mpa_name " +
						"FROM fr_film AS fi " +
						"LEFT JOIN fr_mpa AS m " +
						"ON fi.mpa_id=m.mpa_id " +
						"ORDER BY COUNT_LIKES DESC " +
						"LIMIT ?", new Object[]{limit}, ROW_MAPPER);
		query.forEach(e -> e.setGenres(getGenres(e.getId())));
		query.forEach(e -> e.setLikesFromUsers(getLikesFromUsers(e.getId())));
		return query;
	}

	@Override
	public void addLike(Long idFilm, Long idUser) {
		jdbcTemplate.update("INSERT INTO fr_film_likes(film_id, user_id) " +
				"VALUES (?, ?)", idFilm, idUser);
		jdbcTemplate.update("UPDATE fr_film " +
				"SET count_likes=count_likes+1 " +
				"WHERE film_id = ?", idFilm);
	}

	@Override
	public void deleteLike(Long idFilm, Long idUser) {
		if (jdbcTemplate.update("DELETE FROM fr_film_likes " +
				"WHERE (film_id = ?)AND(user_id= ?)", idFilm, idUser) > 0) {
			jdbcTemplate.update("UPDATE fr_film " +
					"SET count_likes=count_likes-1 " +
					"WHERE film_id = ?", idFilm);
		} else throw new EntityNotFoundException("UserDto",
				" с id " + idUser + "  не найден ");
	}

	@Override
	public boolean validate(FilmDto filmDto) {
		String value;
		if ((filmDto.getName() == null) || ("".equals(filmDto.getName()))) {
			value = "name";
		} else if ((filmDto.getDescription() == null) || (filmDto.getDescription().length() > 200)) {
			value = "description";
		} else if ((filmDto.getReleaseDate() == null) || (filmDto.getReleaseDate().isBefore(MIN_DATE_RELEASE))) {
			value = "releaseDate";
		} else if ((filmDto.getDuration() == null) || (filmDto.getDuration() <= 0)) {
			value = "duration";
		} else {
			return true;
		}
		throw new EntityValidationException(filmDto.getClass().getSimpleName(), "поле " + value + " не прошло валидацию");
	}
}