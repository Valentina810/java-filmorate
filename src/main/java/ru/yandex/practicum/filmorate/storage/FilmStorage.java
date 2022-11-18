package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

	Film getFilm(Long filmId);

	List<Film> getFilms();

	Film addFilm(Film film);

	Film updateFilm(Film film);

	boolean validate(Film film);
}