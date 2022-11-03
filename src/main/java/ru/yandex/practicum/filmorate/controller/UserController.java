package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
	private List<User> users = new ArrayList<>();
	private Integer idUser = 1;

	@GetMapping
	public List<User> getUsers() {
		return users;
	}

	@PostMapping
	public User addUser(@RequestBody User user) {
		if (user.getId() == null) {
			user.setId(idUser++);
		}
		users.add(user);
		return user;
	}

	@PutMapping
	public User updateFilm(@RequestBody User user) {
		if (users.stream().filter(e -> e.getId() == user.getId()).count() > 0) {
			users.remove(users.stream().filter(e -> e.getId() == user.getId())
					.collect(Collectors.toList())
					.iterator().next());
			users.add(user);
			return user;
		} else throw new UserNotFoundException("Пользователь не найден!");
	}
}