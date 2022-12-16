package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.repository.dao.impl.FilmRepository;
import ru.yandex.practicum.filmorate.repository.dao.impl.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTests extends TestData {

	public final FilmRepository filmRepository;
	public final UserRepository userRepository;

	@Test
	public void testGetUser() {
		userRepository.updateUser(userDto1);

		assertEquals(userDto1, userRepository.getUser(userDto1.getId()));
	}

	@Test
	public void testGetUsers() {
		userRepository.updateUser(userDto1);
		userRepository.updateUser(userDto2);
		userRepository.updateUser(userDto3);

		assertEquals(3, userRepository.getUsers().size());

	}

	@Test
	public void testAddUser() {
		userRepository.addUser(userDto4);
		userDto4.setFriends(new HashSet<>());

		assertEquals(userDto4, userRepository.getUser(userDto4.getId()));
	}

	@Test
	public void testUpdateUser() {
		userDto1.setName("New name");
		userRepository.deleteFriend(userDto1.getId(), 2L);
		userRepository.deleteFriend(userDto1.getId(), 3L);
		userRepository.updateUser(userDto1);
		userDto1.setFriends(new HashSet<>());

		assertEquals(userDto1, userRepository.getUser(userDto1.getId()));
	}

	@Test
	public void testGetFriendsUser() {
		userRepository.updateUser(userDto4);

		assertEquals(userDto4.getFriends(), userRepository.getUser(userDto4.getId()).getFriends());
	}

	@Test
	public void testAddFriend() {
		userRepository.deleteFriend(userDto2.getId(), 1L);
		userRepository.deleteFriend(userDto2.getId(), 3L);

		userRepository.addFriend(userDto2.getId(), userDto1.getId());
		userDto2.setFriends(new HashSet<>(List.of(userDto1)));

		assertEquals(userDto2.getFriends(), userRepository.getUser(userDto2.getId()).getFriends());
	}

	@Test
	public void testDeleteFriend() {
		userRepository.deleteFriend(userDto2.getId(), 1L);
		userRepository.deleteFriend(userDto2.getId(), 3L);

		userRepository.addFriend(userDto2.getId(), userDto1.getId());
		userDto2.setFriends(new HashSet<>(List.of(userDto1)));
		assertEquals(userDto2.getFriends(), userRepository.getUser(userDto2.getId()).getFriends());

		userRepository.deleteFriend(userDto2.getId(), userDto1.getId());
		assertEquals(new HashSet<>(), userRepository.getUser(userDto2.getId()).getFriends());
	}

	@Test
	public void testGetGeneralFriends() {
		userRepository.deleteFriend(userDto1.getId(), 2L);
		userRepository.deleteFriend(userDto1.getId(), 3L);
		userRepository.deleteFriend(userDto2.getId(), 3L);
		assertEquals(0, userRepository.getGeneralFriends(userDto1.getId(), userDto2.getId()).size());

		userRepository.addFriend(userDto1.getId(), userDto2.getId());
		userRepository.addFriend(userDto3.getId(), userDto2.getId());
		assertEquals(1, userRepository.getGeneralFriends(userDto1.getId(), userDto3.getId()).size());
	}
}
