package ru.yandex.practicum.filmorate.storage;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exception.EntityValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@Component
public class InMemoryUserStorage implements UserStorage {

	private final HashMap<Long, User> users = new HashMap<>();
	private Long idUser = 1L;

	public User getUser(Long idUser) {
		return users.get(idUser);
	}

	public List<User> getUsers() {
		return new ArrayList<>(users.values());
	}

	public User addUser(User user) {
		if (validate(user)) {
			if (user.getId() == null) {
				user.setId(idUser++);
			}
			users.put(user.getId(), user);
		}
		return user;
	}

	public User updateUser(User user) {
		if (validate(user)) {
			if (users.containsKey(user.getId())) {
				users.put(user.getId(), user);
			} else
				throw new EntityNotFoundException(user.getClass().getSimpleName(), " с id " + user.getId() + " не найден!");
		}
		return user;
	}

	public boolean validate(User user) {
		String value;
		if ((user.getEmail() == null) || ("".equals(user.getEmail()) || !user.getEmail().contains("@"))) {
			value = "email";
		} else if ((user.getLogin() == null) || ("".equals(user.getLogin()) || user.getLogin().contains(" "))) {
			value = "login";
		} else if ((user.getBirthday() == null) || (user.getBirthday().isAfter(LocalDate.now()))) {
			value = "birthday";
		} else if ((user.getName() == null) || ("".equals(user.getName()))) {
			user.setName(user.getLogin());
			return true;
		} else return true;
		throw new EntityValidationException(user.getClass().getSimpleName(), "Поле " + value + " в объекте " + user + "  не прошло валидацию");
	}
}