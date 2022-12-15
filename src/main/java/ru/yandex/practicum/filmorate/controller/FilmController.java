package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
	private FilmService filmService;

	@Autowired
	public FilmController(FilmService filmService) {
		this.filmService = filmService;
	}

	/**
	 * Получить данные о фильме
	 *
	 * @param id - id фильма
	 * @return - данные о фильме
	 */
	@GetMapping("/{id}")
	public FilmDto getFilm(@PathVariable Long id) {
		return filmService.getFilm(id);
	}

	/**
	 * Получить список всех фильмов
	 *
	 * @return - список всех фильмов
	 */
	@GetMapping
	public List<FilmDto> getFilms() {
		return filmService.getFilms();
	}

	/**
	 * Добавить фильм
	 *
	 * @param filmDto - фильм
	 * @return - добавленный фильм
	 */
	@PostMapping
	public FilmDto addFilm(@Valid @RequestBody FilmDto filmDto) {
		return filmService.addFilm(filmDto);
	}

	/**
	 * Обновить фильм
	 *
	 * @param filmDto - фильм
	 * @return - обновленный фильм
	 */
	@PutMapping
	public FilmDto updateFilm(@Valid @RequestBody FilmDto filmDto) {
		return filmService.updateFilm(filmDto);
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
	public List<FilmDto> getPopularMovies(@RequestParam(required = false, defaultValue = "10") Integer count) {
		return filmService.getPopularMovies(count);
	}
}