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
		} else throw new EntityNotFoundException(User.class.getSimpleName(), " не найден!");
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
		} else throw new EntityNotFoundException(User.class.getSimpleName(), " не найден!");
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
		} else throw new EntityNotFoundException(User.class.getSimpleName(), " не найден!");
	}

	/**
	 * Получить пользователя по id
	 *
	 * @param id -  id пользователя
	 * @return - пользователь
	 */
	public User getUser(Long id) {
		return inMemoryUserStorage.getUser(id);
	}

	/**
	 * Получить список пользователей
	 *
	 * @return - список пользователей
	 */
	public List<User> getUsers() {
		return inMemoryUserStorage.getUsers();
	}

	/**
	 * Добавить пользователя
	 *
	 * @param user - пользователь
	 * @return - добавленный пользователь
	 */
	public User addUser(User user) {
		return inMemoryUserStorage.addUser(user);
	}

	/**
	 * Обновить данные пользователя
	 *
	 * @param user - пользователь
	 * @return - обновлённый пользователь
	 */
	public User updateUser(User user) {
		return inMemoryUserStorage.updateUser(user);
	}

	/**
	 * Получить список друзей пользователя
	 *
	 * @param id - id пользователя
	 * @return - список друзей пользователя
	 */
	public List<User> getFriendsUser(Long id) {
		return inMemoryUserStorage.getFriendsUser(id);
	}
}