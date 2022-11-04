package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
	private HashMap<Integer, Film> films = new HashMap<>();
	private Integer idFilm = 1;
	private static final LocalDate MIN_DATE_RELEASE = LocalDate.of(1895, 12, 28);

	@GetMapping
	public List<Film> getFilms() {
		return new ArrayList<>(films.values());
	}

	@PostMapping
	public Film addFilm(@RequestBody Film film) {
		if (validate(film)) {
			if (film.getId() == null) {
				film.setId(idFilm++);
			}
			films.put(film.getId(), film);
		}
		return film;
	}

	@PutMapping
	public Film updateFilm(@RequestBody Film film) {
		if (validate(film)) {
			if (films.containsKey(film.getId())) {
				films.put(film.getId(), film);
			} else throw new FilmNotFoundException("Фильм с id " + film.getId() + " не найден!");
		}
		return film;
	}

	public boolean validate(Film film) {
		String value;
		if ((film.getName() == null) || ("".equals(film.getName()))) {
			value = "name";
		} else if (film.getDescription().length() > 201) {
			value = "description";
		} else if (film.getReleaseDate().isBefore(MIN_DATE_RELEASE)) {
			value = "releaseDate";
		} else if (film.getDuration() <= 0) {
			value = "releaseDate";
		} else {
			return true;
		}
		throw new FilmValidationException("Поле " + value + " не прошло валидацию");
	}
}