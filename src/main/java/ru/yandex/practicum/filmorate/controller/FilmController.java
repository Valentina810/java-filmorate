package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
	private InMemoryFilmStorage inMemoryFilmStorage;
	private FilmService filmService;

	@Autowired
	public FilmController(InMemoryFilmStorage inMemoryFilmStorage, FilmService filmService) {
		this.inMemoryFilmStorage = inMemoryFilmStorage;
		this.filmService = filmService;
	}

	/**
	 * Получить данные о фильме
	 *
	 * @param id - id фильма
	 * @return - данные о фильме
	 */
	@GetMapping("/{id}")
	public Film getFilm(@PathVariable Long id) {
		return inMemoryFilmStorage.getFilm(id);
	}

	/**
	 * Получить список всех фильмов
	 *
	 * @return - список всех фильмов
	 */
	@GetMapping
	public List<Film> getFilms() {
		return inMemoryFilmStorage.getFilms();
	}

	/**
	 * Добавить фильм
	 *
	 * @param film - фильм
	 * @return - добавленный фильм
	 */
	@PostMapping
	public Film addFilm(@Valid @RequestBody Film film) {
		return inMemoryFilmStorage.addFilm(film);
	}

	/**
	 * Обновить фильм
	 *
	 * @param film - фильм
	 * @return - обновленный фильм
	 */
	@PutMapping
	public Film updateFilm(@Valid @RequestBody Film film) {
		return inMemoryFilmStorage.updateFilm(film);
	}

	/**
	 * Добавить лайк пользователя к фильму
	 *
	 * @param id     - id фильма
	 * @param userId - id пользователя
	 */
	@PutMapping("/{id}/like/{userId}")
	public void addLike(@PathVariable Long id, @PathVariable Long userId) {
		filmService.addLike(id, userId);
	}

	/**
	 * Удалить лайк пользователя с фильма
	 *
	 * @param id     - id фильма
	 * @param userId - id пользователя
	 */
	@DeleteMapping("/{id}/like/{userId}")
	public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
		filmService.deleteLike(id, userId);
	}

	/**
	 * Вернуть count самых популярных фильмов
	 *
	 * @param count - количество фильмов
	 * @return - список фильмов
	 */
	@GetMapping("/popular")
	public List<Film> getPopularMovies(@RequestParam(required = false, defaultValue = "10") Integer count) {
		return filmService.getPopularMovies(count);
	}
}