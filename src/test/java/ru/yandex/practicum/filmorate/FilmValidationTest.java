package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.EntityValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmValidationTest {
	FilmController filmController;
	FilmService filmService;

	@BeforeEach
	void clearFilmController() {
		filmService = new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage());
		filmController = new FilmController(filmService);
	}

	@Test
	void checkEmptyName() {
		Film film = Film.builder()
				.name("")
				.description("Test description")
				.releaseDate(LocalDate.of(1900, 12, 27))
				.duration(100)
				.build();
		final EntityValidationException exception = assertThrows(EntityValidationException.class,
				() -> filmController.addFilm(film));
		assertTrue(exception.getMessage().contains("name"));
		assertTrue(exception.getMessage().contains("не прошло валидацию"));
	}

	@Test
	void checkNullName() {
		Film film = Film.builder()
				.description("Test description")
				.releaseDate(LocalDate.of(1900, 12, 27))
				.duration(100)
				.build();
		final EntityValidationException exception = assertThrows(EntityValidationException.class,
				() -> filmController.addFilm(film));
		assertTrue(exception.getMessage().contains("name"));
		assertTrue(exception.getMessage().contains("не прошло валидацию"));
	}

	@Test
	void checkNullDescription() {
		Film film = Film.builder()
				.name("Test film")
				.releaseDate(LocalDate.of(1900, 12, 27))
				.duration(100)
				.build();
		final EntityValidationException exception = assertThrows(EntityValidationException.class,
				() -> filmController.addFilm(film));
		assertTrue(exception.getMessage().contains("description"));
		assertTrue(exception.getMessage().contains("не прошло валидацию"));
	}

	@Test
	void checkDescriptionLength199() {
		Film film = Film.builder()
				.name("Test film")
				.description("No$ClweLwm bdZQJd@uWiDjxcEkU ZdAbmeg@zjneGLwbooCRyMCSbYyOiEiXy.ZPWZ.IcelXPmSGu kbmzABDpXT..lQVcI@O$WuYr$NjKnUqkzZZMY$Z qLRzaiucF#ZPQEqOcmsgunQGsqeRdnN!XBSpWoQIRx aph@!ChdeUnET!IGpzIcjS LOpiCnVzKejLAh")
				.releaseDate(LocalDate.of(1990, 5, 13))
				.duration(100)
				.build();
		filmController.addFilm(film);
		assertEquals(film, filmController.getFilms().iterator().next());
		assertEquals(1, filmController.getFilms().size());
	}

	@Test
	void checkDescriptionLength200() {
		Film film = Film.builder()
				.name("Test film")
				.description("No$ClweLwm bdZQJd@ruWiDjxcEkU ZdAbmeg@zjneGLwbooCRyMCSbYyOiEiXy.ZPWZ.IcelXPmSGu kbmzABDpXT..lQVcI@O$WuYr$NjKnUqkzZZMY$Z qLRzaiucF#ZPQEqOcmsgunQGsqeRdnN!XBSpWoQIRx aph@!ChdeUnET!IGpzIcjS LOpiCnVzKejLAh")
				.releaseDate(LocalDate.of(1990, 5, 13))
				.duration(100)
				.build();
		filmController.addFilm(film);
		assertEquals(film, filmController.getFilms().iterator().next());
		assertEquals(1, filmController.getFilms().size());
	}

	@Test
	void checkDescriptionLength201() {
		Film film = Film.builder()
				.name("Test film")
				.description("No$Cl5weLwm bdZQJd@ruWiDjxcEkU ZdAbmeg@zjneGLwbooCRyMCSbYyOiEiXy.ZPWZ.IcelXPmSGu kbmzABDpXT..lQVcI@O$WuYr$NjKnUqkzZZMY$Z qLRzaiucF#ZPQEqOcmsgunQGsqeRdnN!XBSpWoQIRx aph@!ChdeUnET!IGpzIcjS LOpiCnVzKejLAh")
				.releaseDate(LocalDate.of(1990, 5, 13))
				.duration(100)
				.build();
		final EntityValidationException exception = assertThrows(EntityValidationException.class,
				() -> filmController.addFilm(film));
		assertTrue(exception.getMessage().contains("description"));
		assertTrue(exception.getMessage().contains("не прошло валидацию"));
	}

	@Test
	void checkNullReleaseDate() {
		Film film = Film.builder()
				.name("Test film")
				.description("Test description")
				.releaseDate(LocalDate.of(1895, 12, 27))
				.duration(100)
				.build();
		final EntityValidationException exception = assertThrows(EntityValidationException.class,
				() -> filmController.addFilm(film));
		assertTrue(exception.getMessage().contains("releaseDate"));
		assertTrue(exception.getMessage().contains("не прошло валидацию"));
	}

	@Test
	void checkReleaseDateBeforeMinDateRelease() {
		Film film = Film.builder()
				.name("Test film")
				.description("Test description")
				.releaseDate(LocalDate.of(1895, 12, 27))
				.duration(100)
				.build();
		final EntityValidationException exception = assertThrows(EntityValidationException.class,
				() -> filmController.addFilm(film));
		assertTrue(exception.getMessage().contains("releaseDate"));
		assertTrue(exception.getMessage().contains("не прошло валидацию"));
	}

	@Test
	void checkReleaseDateEqualMinDateRelease() {
		Film film = Film.builder()
				.name("Test film")
				.description("Test description")
				.releaseDate(LocalDate.of(1895, 12, 28))
				.duration(100)
				.build();
		filmController.addFilm(film);
		assertEquals(film, filmController.getFilms().iterator().next());
		assertEquals(1, filmController.getFilms().size());
	}

	@Test
	void checkReleaseDateAfterMinDateRelease() {
		Film film = Film.builder()
				.name("Test film")
				.description("Test description")
				.releaseDate(LocalDate.of(1895, 12, 30))
				.duration(100)
				.build();
		filmController.addFilm(film);
		assertEquals(film, filmController.getFilms().iterator().next());
		assertEquals(1, filmController.getFilms().size());
	}

	@Test
	void checkNullDuration() {
		Film film = Film.builder()
				.name("Test film")
				.description("Test description")
				.releaseDate(LocalDate.of(1895, 12, 30))
				.build();
		final EntityValidationException exception = assertThrows(EntityValidationException.class,
				() -> filmController.addFilm(film));
		assertTrue(exception.getMessage().contains("duration"));
		assertTrue(exception.getMessage().contains("не прошло валидацию"));
	}

	@Test
	void checkNegativeDuration() {
		Film film = Film.builder()
				.name("Test film")
				.description("Test description")
				.releaseDate(LocalDate.of(1895, 12, 30))
				.duration(-1)
				.build();
		final EntityValidationException exception = assertThrows(EntityValidationException.class,
				() -> filmController.addFilm(film));
		assertTrue(exception.getMessage().contains("duration"));
		assertTrue(exception.getMessage().contains("не прошло валидацию"));
	}

	@Test
	void checkDuration0() {
		Film film = Film.builder()
				.name("Test film")
				.description("Test description")
				.releaseDate(LocalDate.of(1895, 12, 30))
				.duration(0)
				.build();
		final EntityValidationException exception = assertThrows(EntityValidationException.class,
				() -> filmController.addFilm(film));
		assertTrue(exception.getMessage().contains("duration"));
		assertTrue(exception.getMessage().contains("не прошло валидацию"));
	}

	@Test
	void checkCorrectDuration() {
		Film film = Film.builder()
				.name("Test film")
				.description("Test description")
				.releaseDate(LocalDate.of(1895, 12, 30))
				.duration(1)
				.build();
		filmController.addFilm(film);
		assertEquals(film, filmController.getFilms().iterator().next());
		assertEquals(1, filmController.getFilms().size());
	}
}