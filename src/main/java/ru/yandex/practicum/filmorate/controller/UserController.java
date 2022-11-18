package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
	private InMemoryUserStorage inMemoryUserStorage;
	private UserService userService;

	@Autowired
	public UserController(InMemoryUserStorage inMemoryUserStorage, UserService userService) {
		this.inMemoryUserStorage = inMemoryUserStorage;
		this.userService = userService;
	}

	@GetMapping("/{id}")
	public User getUser(@PathVariable Long id) {
		return inMemoryUserStorage.getUser(id);
	}

	@GetMapping
	public List<User> getUsers() {
		return inMemoryUserStorage.getUsers();
	}

	@PostMapping
	public User addUser(@Valid @RequestBody User user) {
		return inMemoryUserStorage.addUser(user);
	}

	@PutMapping
	public User updateUser(@Valid @RequestBody User user) {
		return inMemoryUserStorage.updateUser(user);
	}

	@PutMapping("/{id}/friends/{friendId}")
	public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
		userService.addFriend(id, friendId);
	}

	@DeleteMapping("/{id}/friends/{friendId}")
	public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
		userService.deleteFriend(id, friendId);
	}

	@GetMapping("/{id}/friends")
	public List<User> getFriends(@PathVariable Long id) {
		return inMemoryUserStorage.getFriendsUser(id);
	}

	@GetMapping("/{id}/friends/common/{otherId}")
	public List<User> getGeneralFriends(@PathVariable Long id, @PathVariable Long otherId) {
		return userService.getGeneralFriends(id, otherId);
	}
}