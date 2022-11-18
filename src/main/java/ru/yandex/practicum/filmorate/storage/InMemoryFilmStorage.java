package ru.yandex.practicum.filmorate.storage;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exception.EntityValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@Component
public class InMemoryFilmStorage implements FilmStorage {
	private final HashMap<Long, Film> films = new HashMap<>();
	private Long idFilm = 1L;
	private static final LocalDate MIN_DATE_RELEASE = LocalDate.of(1895, 12, 28);

	public Film getFilm(Long filmId) {
		return films.get(filmId);
	}

	public List<Film> getFilms() {
		return new ArrayList<>(films.values());
	}

	public Film addFilm(Film film) {
		if (validate(film)) {
			if (film.getId() == null) {
				film.setId(idFilm++);
			}
			films.put(film.getId(), film);
		}
		return film;
	}

	public boolean validate(Film film) {
		String value;
		if ((film.getName() == null) || ("".equals(film.getName()))) {
			value = "name";
		} else if ((film.getDescription() == null) || (film.getDescription().length() > 200)) {
			value = "description";
		} else if ((film.getReleaseDate() == null) || (film.getReleaseDate().isBefore(MIN_DATE_RELEASE))) {
			value = "releaseDate";
		} else if ((film.getDuration() == null) || (film.getDuration() <= 0)) {
			value = "duration";
		} else {
			return true;
		}
		throw new EntityValidationException(film.getClass().getSimpleName(), "Поле " + value + " в объекте " + film + " не прошло валидацию");
	}

	public Film updateFilm(Film film) {
		if (validate(film)) {
			if (films.containsKey(film.getId())) {
				films.put(film.getId(), film);
			} else
				throw new EntityNotFoundException(film.getClass().getSimpleName(), " с id " + film.getId() + " не найден!");
		}
		return film;
	}
}