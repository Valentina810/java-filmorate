package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.repository.dao.impl.MpaRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MpaRepositoryTests {

	public final MpaRepository mpaRepository;

	@Test
	public void testGetMpa() {
		assertEquals("NC-17", mpaRepository.getMpa(5L).getName());
	}

	@Test
	public void testGetMpas() {
		assertEquals(5, mpaRepository.getMpas().size());
	}
}
