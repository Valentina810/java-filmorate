package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/films")
public class FilmController {
	private List<Film> films = new ArrayList<>();
	private Integer idFilm = 1;

	@GetMapping
	public List<Film> getFilms() {
		return films;
	}

	@PostMapping
	public Film addFilm(@RequestBody Film film) {
		if (film.getId() == null) {
			film.setId(idFilm++);
		}
		films.add(film);
		return film;
	}

	@PutMapping
	public Film updateFilm(@RequestBody Film film) {
		if (films.stream().filter(e -> e.getId() == film.getId()).count() > 0) {
			films.remove(films.stream().filter(e -> e.getId() == film.getId())
					.collect(Collectors.toList())
					.iterator().next());
			films.add(film);
			return film;
		} else throw new FilmNotFoundException("Фильм не найден!");
	}
}