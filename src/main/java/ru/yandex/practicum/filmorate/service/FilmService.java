package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

	private InMemoryFilmStorage inMemoryFilmStorage;
	private InMemoryUserStorage inMemoryUserStorage;

	@Autowired
	public FilmService(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
		this.inMemoryFilmStorage = inMemoryFilmStorage;
		this.inMemoryUserStorage = inMemoryUserStorage;
	}

	/**
	 * Добавление лайка фильму
	 *
	 * @param idFilm - id фильма
	 * @param idUser - id пользователя
	 */
	public void addLike(Long idFilm, Long idUser) {
		Film film = inMemoryFilmStorage.getFilm(idFilm);
		User user = inMemoryUserStorage.getUser(idUser);
		if ((film != null) && (user != null)) {
			film.getLikesFromUsers().add(user.getId());
			film.setCountLikes(film.getCountLikes() + 1);
		} else throw new EntityNotFoundException(Film.class.getCanonicalName(),
				"Не найден фильм или пользователь!");
	}

	/**
	 * Удаление лайка фильма
	 *
	 * @param idFilm - id фильма
	 * @param idUser - id пользователя
	 */
	public void deleteLike(Long idFilm, Long idUser) {
		Film film = inMemoryFilmStorage.getFilm(idFilm);
		User user = inMemoryUserStorage.getUser(idUser);
		if ((film != null) && (user != null)) {
			film.getLikesFromUsers().remove(user.getId());
			film.setCountLikes(film.getCountLikes() - 1);
		} else throw new EntityNotFoundException(Film.class.getCanonicalName(),
				"Не найден фильм или пользователь!");
	}

	/**
	 * Получение списка популярных фильмов
	 *
	 * @param limit - количество фильмов
	 * @return - список фильмов
	 */
	public List<Film> getPopularMovies(Integer limit) {
		return inMemoryFilmStorage.getFilms()
				.stream()
				.sorted((o1, o2) -> Math.toIntExact(o1.getCountLikes() - o2.getCountLikes()))
				.limit(limit)
				.collect(Collectors.toList());
	}
}