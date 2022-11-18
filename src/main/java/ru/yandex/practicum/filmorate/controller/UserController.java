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
}