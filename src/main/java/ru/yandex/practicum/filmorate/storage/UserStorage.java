package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
	User getUser(Long idUser);

	List<User> getUsers();

	User addUser(User user);

	User updateUser(User user);

	boolean validate(User user);
}