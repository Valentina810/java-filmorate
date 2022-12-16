package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.dto.UserDto;
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

	@BeforeEach
	@AfterAll
	public void clearTestData() {
		Set<Long> collect = userRepository.getUsers().stream().map(e -> e.getId()).collect(Collectors.toSet());
		for (Long idUser : collect) {
			userRepository.deleteUser(Math.toIntExact(idUser));
		}
	}

	@Test
	public void testGetUser() {
		UserDto userDto = userRepository.addUser(userDto1);
		assertEquals(userDto, userRepository.getUser(userDto.getId()));
	}

	@Test
	public void testGetUsers() {
		userRepository.addUser(userDto1);
		userRepository.addUser(userDto2);
		assertEquals(2, userRepository.getUsers().size());
	}

	@Test
	public void testAddUser() {
		userDto4.setId(userRepository.addUser(userDto4).getId());
		userDto4.setFriends(new HashSet<>());
		assertEquals(userDto4, userRepository.getUser(userDto4.getId()));
	}

	@Test
	public void testUpdateUser() {
		UserDto userDto = userRepository.addUser(userDto2);
		userDto.setName("New");
		UserDto newUserDto = userRepository.updateUser(userDto);
		assertEquals(userDto.getName(), newUserDto.getName());

	}

	@Test
	public void testGetFriendsUser() {
		UserDto userDto = userRepository.addUser(userDto3);
		UserDto friendUserDto = userRepository.addUser(userDto1);
		userRepository.addFriend(userDto.getId(), friendUserDto.getId());


		assertEquals(new HashSet<>(List.of(friendUserDto)), userRepository.getUser(userDto.getId()).getFriends());
	}

	@Test
	public void testAddFriend() {
		UserDto userDto = userRepository.addUser(userDto3);
		UserDto friendUserDto = userRepository.addUser(userDto1);
		userRepository.addFriend(userDto.getId(), friendUserDto.getId());

		assertEquals(new HashSet<>(List.of(friendUserDto)), userRepository.getUser(userDto.getId()).getFriends());
	}

	@Test
	public void testDeleteFriend() {
		UserDto userDto = userRepository.addUser(userDto3);
		UserDto friendUserDto = userRepository.addUser(userDto1);
		userRepository.addFriend(userDto.getId(), friendUserDto.getId());
		assertEquals(new HashSet<>(List.of(friendUserDto)), userRepository.getUser(userDto.getId()).getFriends());

		userRepository.deleteFriend(userDto.getId(), friendUserDto.getId());
		assertEquals(new HashSet<>(), userRepository.getUser(userDto.getId()).getFriends());
	}

	@Test
	public void testGetGeneralFriends() {
		UserDto userDtoo1 = userRepository.addUser(userDto1);
		UserDto userDtoo2 = userRepository.addUser(userDto2);
		UserDto userDtoo3 = userRepository.addUser(userDto3);

		userRepository.addFriend(userDtoo1.getId(), userDtoo2.getId());
		userRepository.addFriend(userDtoo3.getId(), userDtoo2.getId());

		assertEquals(new ArrayList<>(List.of(userDtoo2)),
				userRepository.getGeneralFriends(userDtoo1.getId(), userDtoo3.getId()));
	}
}
