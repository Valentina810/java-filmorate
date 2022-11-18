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

	@GetMapping
	public List<Film> getFilms() {
		return inMemoryFilmStorage.getFilms();
	}

	@PostMapping
	public Film addFilm(@Valid @RequestBody Film film) {
		return inMemoryFilmStorage.addFilm(film);
	}

	@PutMapping
	public Film updateFilm(@Valid @RequestBody Film film) {
		return inMemoryFilmStorage.updateFilm(film);
	}
}