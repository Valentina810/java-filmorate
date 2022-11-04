package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
	private final HashMap<Integer, User> users = new HashMap<>();
	private Integer idUser = 1;

	@GetMapping
	public List<User> getUsers() {
		log.info("Запрошен список всех пользователей");
		return new ArrayList<>(users.values());
	}

	@PostMapping
	public User addUser(@RequestBody User user) {
		if (validate(user)) {
			if (user.getId() == null) {
				user.setId(idUser++);
			}
			users.put(user.getId(), user);
			log.info("Пользователь {} добавлен", user);
		}
		return user;
	}

	@PutMapping
	public User updateUser(@RequestBody User user) {
		if (validate(user)) {
			if (users.containsKey(user.getId())) {
				users.put(user.getId(), user);
				log.info("Пользователь {} обновлен", user);
			} else throw new UserNotFoundException("Пользователь с id " + user.getId() + " не найден!");
		}
		return user;
	}

	public boolean validate(User user) {
		String value;
		if (("".equals(user.getEmail()) || !user.getEmail().contains("@"))) {
			value = "email";
		} else if ("".equals(user.getLogin()) || user.getLogin().contains(" ")) {
			value = "login";
		} else if (user.getBirthday().isAfter(LocalDate.now())) {
			value = "birthday";
		} else if ("".equals(user.getName()) || (user.getName() == null)) {
			user.setName(user.getLogin());
			return true;
		} else return true;
		throw new UserValidationException("Поле " + value + " в объекте " + user + "  не прошло валидацию");
	}
}