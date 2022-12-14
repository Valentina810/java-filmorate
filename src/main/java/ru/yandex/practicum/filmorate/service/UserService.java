package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.impl.UserDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
	private UserDao userDao;

	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * Добавление в друзья
	 *
	 * @param idUser   - пользователь
	 * @param idFriend - друг пользователя, которого нужно добавить из друзей
	 */
	public void addFriend(Long idUser, Long idFriend) {
		User user = userDao.getUser(idUser);
		User friend = userDao.getUser(idFriend);
		if ((user != null) && (friend != null)) {
//			user.getFriends().add(idFriend);
//			friend.getFriends().add(idUser);
		} else throw new EntityNotFoundException(User.class.getSimpleName(), " не найден!");
	}

	/**
	 * Удаление из друзей
	 *
	 * @param idUser   - пользователь
	 * @param idFriend - друг пользователя, которого нужно удалить из друзей
	 */
	public void deleteFriend(Long idUser, Long idFriend) {
		User user = userDao.getUser(idUser);
		User friend = userDao.getUser(idFriend);
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
		User user = userDao.getUser(idUser);
		User friend = userDao.getUser(idFriend);
		if ((user != null) && (friend != null)) {
			user.getFriends().forEach(e ->
			{
				if (friend.getFriends().contains(e)) {
//					generalFriends.add(userDao.getUser(e));
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
		return userDao.getUser(id);
	}

	/**
	 * Получить список пользователей
	 *
	 * @return - список пользователей
	 */
	public List<User> getUsers() {
		return userDao.getUsers();
	}

	/**
	 * Добавить пользователя
	 *
	 * @param user - пользователь
	 * @return - добавленный пользователь
	 */
	public User addUser(User user) {
		return userDao.addUser(user);
	}

	/**
	 * Обновить данные пользователя
	 *
	 * @param user - пользователь
	 * @return - обновлённый пользователь
	 */
	public User updateUser(User user) {
		return userDao.updateUser(user);
	}

	/**
	 * Получить список друзей пользователя
	 *
	 * @param id - id пользователя
	 * @return - список друзей пользователя
	 */
	public HashSet<User> getFriendsUser(Long id) {
		return userDao.getFriendsUser(id);
	}
}