package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.repository.dao.impl.GenreRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GenreRepositoryTests {

	public final GenreRepository genreRepository;

	@Test
	public void testGetMpa() {
		assertEquals("Триллер", genreRepository.getGenre(4L).getName());
	}

	@Test
	public void testGetMpas() {
		assertEquals(6, genreRepository.getGenres().size());
	}
}