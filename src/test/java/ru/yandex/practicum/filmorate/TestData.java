package ru.yandex.practicum.filmorate;

import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.dto.UserDto;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;


public class TestData {

	FilmDto testFilm1 = FilmDto.builder()
			.name("The Green Mile")
			.description("Paul Edgecomb is the head of the death row at Cold Mountain Prison, each of whose prisoners once walks the \"green mile\" on the way to the place of execution.")
			.releaseDate(LocalDate.of(1999, 12, 6))
			.duration(189)
			.mpa(MpaDto.builder()
					.id(2)
					.name("PG").build())
			.genres(new LinkedHashSet<>(List.of(
					GenreDto.builder()
							.id(5)
							.name("Документальный").build(),
					GenreDto.builder()
							.id(2)
							.name("Драма").build()
			)))
			.build();

	FilmDto testFilm2 = FilmDto.builder()
			.name("Schindlers List")
			.description("The story of a German industrialist who saved thousands of lives during the Holocaust")
			.releaseDate(LocalDate.of(1993, 11, 30))
			.duration(195)
			.mpa(MpaDto.builder()
					.id(4)
					.name("R").build())
			.genres(new LinkedHashSet<>(List.of(
					GenreDto.builder()
							.id(2)
							.name("Драма").build(),
					GenreDto.builder()
							.id(6)
							.name("Боевик").build()
			)))
			.build();

	FilmDto testFilm3 = FilmDto.builder()
			.name("testFilm")
			.description("The testFilm")
			.releaseDate(LocalDate.of(1993, 11, 30))
			.duration(195)
			.mpa(MpaDto.builder()
					.id(4)
					.name("R").build())
			.genres(new LinkedHashSet<>(List.of(
					GenreDto.builder()
							.id(2)
							.name("Драма").build(),
					GenreDto.builder()
							.id(6)
							.name("Боевик").build()
			)))
			.build();

	UserDto userDto1 = UserDto.builder()
			.email("tom@mail.ru")
			.login("tom")
			.name("tom")
			.birthday(LocalDate.of(2000, 03, 13))
			.build();
	UserDto userDto2 = UserDto.builder()
			.email("olga@mail.ru")
			.login("olga")
			.name("olga")
			.birthday(LocalDate.of(1999, 04, 11))
			.build();

	UserDto userDto3 = UserDto.builder()
			.email("krisi@mail.ru")
			.login("krisi")
			.name("krisi")
			.birthday(LocalDate.of(1985, 01, 10))
			.build();

	UserDto userDto4 = UserDto.builder()
			.email("matt@mail.ru")
			.login("matt")
			.name("matt")
			.birthday(LocalDate.of(2003, 12, 12))
			.build();
}
