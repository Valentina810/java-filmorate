package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Получить данные о пользователе
	 *
	 * @param id - id пользователя
	 * @return - данные о пользователе
	 */
	@GetMapping("/{id}")
	public User getUser(@PathVariable Long id) {

		return userService.getUser(id);
	}

	/**
	 * Получить список пользователей
	 *
	 * @return - список пользователей
	 */
	@GetMapping
	public List<User> getUsers() {
		return userService.getUsers();
	}

	/**
	 * Добавить нового пользователя
	 *
	 * @param user - пользователь для добавления
	 * @return - добавленный пользователь
	 */
	@PostMapping
	public User addUser(@Valid @RequestBody User user) {
		return userService.addUser(user);
	}

	/**
	 * Обновить пользователя
	 *
	 * @param user - пользователь
	 * @return - обновленный пользователь
	 */
	@PutMapping
	public User updateUser(@Valid @RequestBody User user) {
		return userService.updateUser(user);
	}

	/**
	 * Добавить пользователя в друзья (взаимно)
	 *
	 * @param id       - id пользователя
	 * @param friendId - id друга пользователя
	 */
	@PutMapping("/{id}/friends/{friendId}")
	public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
		userService.addFriend(id, friendId);
	}

	/**
	 * Удалить пользователя из друзей (взаимно)
	 *
	 * @param id       - id пользователя
	 * @param friendId - id друга пользователя
	 */
	@DeleteMapping("/{id}/friends/{friendId}")
	public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
		userService.deleteFriend(id, friendId);
	}

	/**
	 * Получить список друщей пользователя
	 *
	 * @param id - id пользователя
	 * @return - список друзей
	 */
	@GetMapping("/{id}/friends")
	public List<User> getFriends(@PathVariable Long id) {
		return userService.getFriendsUser(id);
	}

	/**
	 * Получить список друзей, общих для двух пользователей
	 *
	 * @param id      - id  первого пользователя
	 * @param otherId - id  второго пользователя
	 * @return - список общих друзей
	 */
	@GetMapping("/{id}/friends/common/{otherId}")
	public List<User> getGeneralFriends(@PathVariable Long id, @PathVariable Long otherId) {
		return userService.getGeneralFriends(id, otherId);
	}
}