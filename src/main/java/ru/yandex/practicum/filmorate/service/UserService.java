package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.UserDto;
import ru.yandex.practicum.filmorate.storage.dao.impl.UserDao;

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
		userDao.addFriend(idUser, idFriend);
	}

	/**
	 * Удаление из друзей
	 *
	 * @param idUser   - пользователь
	 * @param idFriend - друг пользователя, которого нужно удалить из друзей
	 */
	public void deleteFriend(Long idUser, Long idFriend) {
		userDao.deleteFriend(idUser, idFriend);
	}

	/**
	 * Вывод списка общих друзей
	 *
	 * @param idUser   - первый пользователь
	 * @param idFriend - второй пользователь
	 */
	public List<UserDto> getGeneralFriends(Long idUser, Long idFriend) {
		return userDao.getGeneralFriends(idUser, idFriend);
	}

	/**
	 * Получить пользователя по id
	 *
	 * @param id -  id пользователя
	 * @return - пользователь
	 */
	public UserDto getUser(Long id) {
		return userDao.getUser(id);
	}

	/**
	 * Получить список пользователей
	 *
	 * @return - список пользователей
	 */
	public List<UserDto> getUsers() {
		return userDao.getUsers();
	}

	/**
	 * Добавить пользователя
	 *
	 * @param userDto - пользователь
	 * @return - добавленный пользователь
	 */
	public UserDto addUser(UserDto userDto) {
		return userDao.addUser(userDto);
	}

	/**
	 * Обновить данные пользователя
	 *
	 * @param userDto - пользователь
	 * @return - обновлённый пользователь
	 */
	public UserDto updateUser(UserDto userDto) {
		return userDao.updateUser(userDto);
	}

	/**
	 * Получить список друзей пользователя
	 *
	 * @param id - id пользователя
	 * @return - список друзей пользователя
	 */
	public HashSet<UserDto> getFriendsUser(Long id) {
		return userDao.getFriendsUser(id);
	}
}