package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.dto.GenreDto;
import ru.yandex.practicum.filmorate.model.dto.MpaDto;
import ru.yandex.practicum.filmorate.repository.dao.impl.FilmRepository;
import ru.yandex.practicum.filmorate.repository.dao.impl.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FilmRepositoryTests extends TestData {

	public final FilmRepository filmRepository;
	public final UserRepository userRepository;

	@BeforeAll
	public  void createTestData()
	{
		new ArrayList<>(List.of(userDto1,userDto2,userDto3))
				.forEach(e->userRepository.addUser(e));
		userRepository.addFriend(1L,2L);
		userRepository.addFriend(1L,3L);
		userRepository.addFriend(2L,3L);

		new ArrayList<>(List.of(testFilm1,testFilm2))
				.forEach(e-> filmRepository.addFilm(e));

	}

	@Test
	public void testGetFilm() {
		testFilm1.setId(filmRepository.updateFilm(testFilm1).getId());
		assertEquals(testFilm1, filmRepository.getFilm(testFilm1.getId()));
	}

	@Test
	public void testGetFilms() {
		assertTrue(filmRepository.getFilms().contains(testFilm1));
		assertTrue(filmRepository.getFilms().contains(testFilm2));
	}

	@Test
	public void testAddFilm() {
		testFilm3.setId(filmRepository.addFilm(testFilm3).getId());
		assertEquals(testFilm3, filmRepository.getFilm(testFilm3.getId()));
	}

	@Test
	public void testUpdateFilm() {
		testFilm2.setName("test name");
		testFilm2.setDescription("test description");
		testFilm2.setLikesFromUsers(new HashSet<>(List.of(1L, 3L)));
		testFilm2.setCountLikes(2);
		testFilm2.setMpa(MpaDto.builder()
				.id(5)
				.name("NC-17").build());
		testFilm2.setGenres((new LinkedHashSet<>(List.of(
				GenreDto.builder()
						.id(6)
						.name("Боевик").build()
		))));
		testFilm2.setId(filmRepository.updateFilm(testFilm2).getId());
		assertEquals(testFilm2, filmRepository.getFilm(testFilm2.getId()));
	}

	@Test
	public void testPopularMoviesLimit2() {
		filmRepository.getFilms().forEach(e ->
		{
			e.setCountLikes(0);
			e.setLikesFromUsers(new HashSet<>());
			filmRepository.updateFilm(e);
		});
		testFilm1.setCountLikes(2);
		testFilm1.setLikesFromUsers(new HashSet<>(List.of(3L, 2L)));
		testFilm2.setCountLikes(3);
		testFilm2.setLikesFromUsers(new HashSet<>(List.of(3L, 1L, 2L)));
		filmRepository.updateFilm(testFilm1);
		filmRepository.updateFilm(testFilm2);

		assertEquals(new ArrayList<>(List.of(testFilm2, testFilm1)),
				filmRepository.getPopularMovies(2));
	}

	@Test
	public void testPopularMoviesLimit1() {
		filmRepository.getFilms().forEach(e ->
		{
			e.setCountLikes(0);
			e.setLikesFromUsers(new HashSet<>());
			filmRepository.updateFilm(e);
		});
		testFilm1.setCountLikes(1);
		testFilm1.setLikesFromUsers(new HashSet<>(List.of(3L)));
		testFilm2.setCountLikes(3);
		testFilm2.setLikesFromUsers(new HashSet<>(List.of(3L, 1L, 2L)));
		filmRepository.updateFilm(testFilm1);
		filmRepository.updateFilm(testFilm2);

		assertEquals(new ArrayList<>(List.of(testFilm2)),
				filmRepository.getPopularMovies(1));
	}

	@Test
	public void testAddLike() {
		testFilm1.setCountLikes(1);
		testFilm1.setLikesFromUsers(new HashSet<>(List.of(1L)));
		filmRepository.updateFilm(testFilm1);

		filmRepository.addLike(testFilm1.getId(), 3L);
		testFilm1.setCountLikes(2);
		testFilm1.setLikesFromUsers(new HashSet<>(List.of(1L, 3L)));

		assertEquals(testFilm1.getCountLikes(),
				filmRepository.getFilm(testFilm1.getId()).getCountLikes());
		assertEquals(testFilm1.getLikesFromUsers(),
				filmRepository.getFilm(testFilm1.getId()).getLikesFromUsers());
	}

	@Test
	public void testDeleteLike() {
		testFilm1.setCountLikes(2);
		testFilm1.setLikesFromUsers(new HashSet<>(List.of(1L, 2L)));
		filmRepository.updateFilm(testFilm1);

		filmRepository.deleteLike(testFilm1.getId(), 2L);
		testFilm1.setCountLikes(1);
		testFilm1.setLikesFromUsers(new HashSet<>(List.of(1L)));

		assertEquals(testFilm1.getCountLikes(),
				filmRepository.getFilm(testFilm1.getId()).getCountLikes());
		assertEquals(testFilm1.getLikesFromUsers(),
				filmRepository.getFilm(testFilm1.getId()).getLikesFromUsers());
	}
}
