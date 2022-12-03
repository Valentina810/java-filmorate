package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.EntityValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidationTest {
	UserController userController;
	UserService userService;

	@BeforeEach
	void clearUserController() {
		userService = new UserService(new InMemoryUserStorage());
		userController = new UserController(userService);
	}

	@Test
	void checkNullEmail() {
		User user = User.builder()
				.login("userlogin")
				.name("username")
				.birthday(LocalDate.of(1990, 1, 1))
				.build();
		final EntityValidationException exception = assertThrows(EntityValidationException.class,
				() -> userController.addUser(user));
		assertTrue(exception.getMessage().contains("email"));
		assertTrue(exception.getMessage().contains("не прошло валидацию"));
	}

	@Test
	void checkEmptyEmail() {
		User user = User.builder()
				.email("")
				.login("userlogin")
				.name("username")
				.birthday(LocalDate.of(1990, 1, 1))
				.build();
		final EntityValidationException exception = assertThrows(EntityValidationException.class,
				() -> userController.addUser(user));
		assertTrue(exception.getMessage().contains("email"));
		assertTrue(exception.getMessage().contains("не прошло валидацию"));
	}

	@Test
	void checkIncorrectEmail() {
		User user = User.builder()
				.email("fgh7uj#mk.ui")
				.login("userlogin")
				.name("username")
				.birthday(LocalDate.of(1990, 1, 1))
				.build();
		final EntityValidationException exception = assertThrows(EntityValidationException.class,
				() -> userController.addUser(user));
		assertTrue(exception.getMessage().contains("email"));
		assertTrue(exception.getMessage().contains("не прошло валидацию"));
	}

	@Test
	void checkCorrectEmail() {
		User user = User.builder()
				.email("fgh7ujmk@ui")
				.login("userlogin")
				.name("username")
				.birthday(LocalDate.of(1990, 1, 1))
				.build();
		userController.addUser(user);
		assertEquals(user, userController.getUsers().iterator().next());
		assertEquals(1, userController.getUsers().size());
	}

	@Test
	void checkNullLogin() {
		User user = User.builder()
				.email("fgh7ujmk@ui")
				.name("username")
				.birthday(LocalDate.of(1990, 1, 1))
				.build();
		final EntityValidationException exception = assertThrows(EntityValidationException.class,
				() -> userController.addUser(user));
		assertTrue(exception.getMessage().contains("login"));
		assertTrue(exception.getMessage().contains("не прошло валидацию"));
	}

	@Test
	void checkEmptyLogin() {
		User user = User.builder()
				.email("fgh7ujmk@ui")
				.login("")
				.name("username")
				.birthday(LocalDate.of(1990, 1, 1))
				.build();
		final EntityValidationException exception = assertThrows(EntityValidationException.class,
				() -> userController.addUser(user));
		assertTrue(exception.getMessage().contains("login"));
		assertTrue(exception.getMessage().contains("не прошло валидацию"));
	}

	@Test
	void checkSpaceInLogin() {
		User user = User.builder()
				.email("fgh7ujmk@ui")
				.login("hjk ojlfe")
				.name("username")
				.birthday(LocalDate.of(1990, 1, 1))
				.build();
		final EntityValidationException exception = assertThrows(EntityValidationException.class,
				() -> userController.addUser(user));
		assertTrue(exception.getMessage().contains("login"));
		assertTrue(exception.getMessage().contains("не прошло валидацию"));
	}

	@Test
	void checkCorrectLogin() {
		User user = User.builder()
				.email("fgh7ujmk@ui")
				.login("hjkojlfe")
				.name("username")
				.birthday(LocalDate.of(1990, 1, 1))
				.build();
		userController.addUser(user);
		assertEquals(user, userController.getUsers().iterator().next());
		assertEquals(1, userController.getUsers().size());
	}

	@Test
	void checkNullName() {
		User testUser = User.builder()
				.email("fgh7ujmk@ui")
				.login("hjkojlfe")
				.birthday(LocalDate.of(1990, 1, 1))
				.friends(new HashSet<>())
				.build();
		User userExpected = testUser.clone();
		userExpected.setName(userExpected.getLogin());
		userExpected.setId(userController.addUser(testUser).getId());
		assertEquals(userExpected, userController.getUsers().iterator().next());
		assertEquals(1, userController.getUsers().size());
	}

	@Test
	void checkEmptyName() {
		User testUser = User.builder()
				.name("")
				.email("fgh7ujmk@ui")
				.login("hjkojlfe")
				.birthday(LocalDate.of(1990, 1, 1))
				.friends(new HashSet<>())
				.build();
		User userExpected = testUser.clone();
		userExpected.setName(userExpected.getLogin());
		userExpected.setId(userController.addUser(testUser).getId());
		assertEquals(userExpected, userController.getUsers().iterator().next());
		assertEquals(1, userController.getUsers().size());
	}

	@Test
	void checkCorrectName() {
		User testUser = User.builder()
				.name("name")
				.email("fgh7ujmk@ui")
				.login("hjkojlfe")
				.birthday(LocalDate.of(1990, 1, 1))
				.friends(new HashSet<>())
				.build();
		User userExpected = testUser.clone();
		userExpected.setId(userController.addUser(testUser).getId());
		assertEquals(userExpected, userController.getUsers().iterator().next());
		assertEquals(1, userController.getUsers().size());
	}

	@Test
	void checkNullBirthday() {
		User user = User.builder()
				.name("name")
				.email("fgh7ujmk@ui")
				.login("hjkojlfe")
				.build();
		final EntityValidationException exception = assertThrows(EntityValidationException.class,
				() -> userController.addUser(user));
		assertTrue(exception.getMessage().contains("birthday"));
		assertTrue(exception.getMessage().contains("не прошло валидацию"));
	}

	@Test
	void checkBirthdayBeforeCurrentDate() {
		User user = User.builder()
				.name("name")
				.email("fgh7ujmk@ui")
				.login("hjkojlfe")
				.birthday(LocalDate.now().minusDays(1))
				.build();
		userController.addUser(user);
		assertEquals(user, userController.getUsers().iterator().next());
		assertEquals(1, userController.getUsers().size());
	}

	@Test
	void checkBirthdayEqualCurrentDate() {
		User user = User.builder()
				.name("name")
				.email("fgh7ujmk@ui")
				.login("hjkojlfe")
				.birthday(LocalDate.now())
				.build();
		userController.addUser(user);
		assertEquals(user, userController.getUsers().iterator().next());
		assertEquals(1, userController.getUsers().size());
	}

	@Test
	void checkBirthdayAfterCurrentDate() {
		User user = User.builder()
				.name("name")
				.email("fgh7ujmk@ui")
				.login("hjkojlfe")
				.birthday(LocalDate.now().plusDays(1))
				.build();
		final EntityValidationException exception = assertThrows(EntityValidationException.class,
				() -> userController.addUser(user));
		assertTrue(exception.getMessage().contains("birthday"));
		assertTrue(exception.getMessage().contains("не прошло валидацию"));
	}
}
