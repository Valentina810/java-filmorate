package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
	private UserStorage inMemoryUserStorage;

	@Autowired
	public UserService(InMemoryUserStorage inMemoryUserStorage) {
		this.inMemoryUserStorage = inMemoryUserStorage;
	}

	/**
	 * Добавление в друзья
	 *
	 * @param idUser   - пользователь
	 * @param idFriend - друг пользователя, которого нужно добавить из друзей
	 */
	public void addFriend(Long idUser, Long idFriend) {
		User user = inMemoryUserStorage.getUser(idUser);
		User friend = inMemoryUserStorage.getUser(idFriend);
		if ((user != null) && (friend != null)) {
			user.getFriends().add(idFriend);
			friend.getFriends().add(idUser);
		} else throw new EntityNotFoundException(User.class.getSimpleName(),
				"Не найден пользователь или его друг!");
	}

	/**
	 * Удаление из друзей
	 *
	 * @param idUser   - пользователь
	 * @param idFriend - друг пользователя, которого нужно удалить из друзей
	 */
	public void deleteFriend(Long idUser, Long idFriend) {
		User user = inMemoryUserStorage.getUser(idUser);
		User friend = inMemoryUserStorage.getUser(idFriend);
		if ((user != null) && (friend != null)) {
			user.getFriends().remove(idFriend);
			friend.getFriends().remove(idUser);
		} else throw new EntityNotFoundException(User.class.getSimpleName(),
				"Не найден пользователь или его друг!");
	}

	/**
	 * Вывод списка общих друзей
	 *
	 * @param idUser   - первый пользователь
	 * @param idFriend - второй пользователь
	 */
	public List<User> getGeneralFriends(Long idUser, Long idFriend) {
		List<User> generalFriends = new ArrayList<>();
		User user = inMemoryUserStorage.getUser(idUser);
		User friend = inMemoryUserStorage.getUser(idFriend);
		if ((user != null) && (friend != null)) {
			user.getFriends().forEach(e ->
			{
				if (friend.getFriends().contains(e)) {
					generalFriends.add(inMemoryUserStorage.getUser(e));
				}
			});
			return generalFriends;
		} else throw new EntityNotFoundException(User.class.getCanonicalName(),
				"Не найден пользователь или его друг!");
	}
}