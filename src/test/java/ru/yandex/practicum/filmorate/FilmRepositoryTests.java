package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.dto.UserDto;
import ru.yandex.practicum.filmorate.repository.dao.impl.FilmRepository;
import ru.yandex.practicum.filmorate.repository.dao.impl.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FilmRepositoryTests {
	private final FilmRepository filmRepository;
	private final UserRepository userRepository;
	FilmDto testFilm1 = FilmDto.builder()
			.name("The Green Mile")
			.description("Paul Edgecomb is the head of the death row at Cold Mountain Prison, each of whose prisoners once walks the \"green mile\" on the way to the place of execution.")
			.releaseDate(LocalDate.of(1999, 12, 6))
			.duration(189)
			.mpa(MpaDto.builder()
					.id(2)
					.name("PG").build())
			.likesFromUsers(new HashSet<>(List.of(3L)))
			.countLikes(1)
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
			.likesFromUsers(new HashSet<>(List.of(3L, 1L, 2L, 4L)))
			.countLikes(4)
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
			.likesFromUsers(new HashSet<>(List.of(4L)))
			.countLikes(1)
			.genres(new LinkedHashSet<>(List.of(
					GenreDto.builder()
							.id(2)
							.name("Драма").build(),
					GenreDto.builder()
							.id(6)
							.name("Боевик").build()
			)))
			.build();

	UserDto userDto1=UserDto.builder()
			.email("tom@mail.ru")
			.login("tom")
			.name("tom")
			//.friends(userDto2,userDto3)
			.birthday(LocalDate.of(2000, 03, 13))
			.build();
	UserDto userDto2=UserDto.builder()
			.email("olga@mail.ru")
			.login("olga")
			.name("olga")
			//.friends(new HashSet<>(List.of(userDto1,userDto3)))
			.birthday(LocalDate.of(1999, 04, 11))
			.build();

	UserDto userDto3=UserDto.builder()
			.email("krisi@mail.ru")
			.login("krisi")
			.name("krisi")
			//.friends(new HashSet<>(List.of(userDto1,userDto2)))
			.birthday(LocalDate.of(1985, 01, 10))
			.build();

	UserDto userDto4=UserDto.builder()
			.email("matt@mail.ru")
			.login("matt")
			.name("matt")
			.friends(new HashSet<>(List.of(userDto1,userDto2)))
			.birthday(LocalDate.of(2003, 12, 12))
			.build();

	@BeforeAll
	public  void createTestData()
	{
		new ArrayList<>(List.of(userDto1,userDto2,userDto3,userDto4))
				.forEach(e->userRepository.addUser(e));
		userRepository.addFriend(1L,2L);
		userRepository.addFriend(1L,3L);
		userRepository.addFriend(2L,3L);

		new ArrayList<>(List.of(testFilm1,testFilm2))
				.forEach(e-> filmRepository.addFilm(e));

	}

	@Test
	public void testGetFilm() {
		assertEquals(testFilm1, filmRepository.getFilm(1L));
	}

	@Test
	public void testGetFilms() {
		assertTrue(filmRepository.getFilms().contains(testFilm1));
		assertTrue(filmRepository.getFilms().contains(testFilm2));
	}

	@Test
	public void testAddFilm() {
		filmRepository.addFilm(testFilm3);
		assertEquals(testFilm3, filmRepository.getFilm(3L));
	}

	@Test
	public void testUpdateFilm() {
		testFilm2.setName("test name");
		testFilm2.setDescription("test description");
		testFilm2.setLikesFromUsers(new HashSet<>(List.of( 1L, 4L)));
		testFilm2.setCountLikes(2);
		testFilm2.setMpa(MpaDto.builder()
				.id(5)
				.name("NC-17").build());
		testFilm2.setGenres((new LinkedHashSet<>(List.of(
				GenreDto.builder()
						.id(6)
						.name("Боевик").build()
		))));
		filmRepository.updateFilm(testFilm2);
		assertEquals(testFilm2, filmRepository.getFilm(2L));
	}

	@Test
	public void testPopularMoviesLimit2() {
		testFilm1.setCountLikes(2);
		testFilm1.setLikesFromUsers(new HashSet<>(List.of( 3L,4L)));
		testFilm2.setCountLikes(3);
		testFilm2.setLikesFromUsers(new HashSet<>(List.of( 3L,1L,2L)));
		filmRepository.updateFilm(testFilm1);
		filmRepository.updateFilm(testFilm2);

		assertEquals(new ArrayList<>(List.of(testFilm2,testFilm1)),
				filmRepository.getPopularMovies(2));
	}

	@Test
	public void testPopularMoviesLimit1() {
		testFilm1.setCountLikes(1);
		testFilm1.setLikesFromUsers(new HashSet<>(List.of( 3L)));
		testFilm2.setCountLikes(3);
		testFilm2.setLikesFromUsers(new HashSet<>(List.of( 3L,1L,2L)));
		filmRepository.updateFilm(testFilm1);
		filmRepository.updateFilm(testFilm2);

		assertEquals(new ArrayList<>(List.of(testFilm2)),
				filmRepository.getPopularMovies(1));
	}

	@Test
	public void testAddLike() {
		testFilm1.setCountLikes(1);
		testFilm1.setLikesFromUsers(new HashSet<>(List.of( 1L)));
		filmRepository.updateFilm(testFilm1);

		filmRepository.addLike(testFilm1.getId(),4L);
		testFilm1.setCountLikes(2);
		testFilm1.setLikesFromUsers(new HashSet<>(List.of( 1L,4L)));

		assertEquals(testFilm1.getCountLikes(),
				filmRepository.getFilm(testFilm1.getId()).getCountLikes());
		assertEquals(testFilm1.getLikesFromUsers(),
				filmRepository.getFilm(testFilm1.getId()).getLikesFromUsers());
	}

	@Test
	public void testDeleteLike() {
		testFilm1.setCountLikes(2);
		testFilm1.setLikesFromUsers(new HashSet<>(List.of( 1L,2L)));
		filmRepository.updateFilm(testFilm1);

		filmRepository.deleteLike(testFilm1.getId(),2L);
		testFilm1.setCountLikes(1);
		testFilm1.setLikesFromUsers(new HashSet<>(List.of( 1L)));

		assertEquals(testFilm1.getCountLikes(),
				filmRepository.getFilm(testFilm1.getId()).getCountLikes());
		assertEquals(testFilm1.getLikesFromUsers(),
				filmRepository.getFilm(testFilm1.getId()).getLikesFromUsers());
	}
}
