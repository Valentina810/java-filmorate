package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.dto.UserDto;
import ru.yandex.practicum.filmorate.repository.dao.impl.UserRepository;

import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Добавление в друзья
	 *
	 * @param idUser   - пользователь
	 * @param idFriend - друг пользователя, которого нужно добавить из друзей
	 */
	public void addFriend(Long idUser, Long idFriend) {
		userRepository.addFriend(idUser, idFriend);
	}

	/**
	 * Удаление из друзей
	 *
	 * @param idUser   - пользователь
	 * @param idFriend - друг пользователя, которого нужно удалить из друзей
	 */
	public void deleteFriend(Long idUser, Long idFriend) {
		userRepository.deleteFriend(idUser, idFriend);
	}

	/**
	 * Вывод списка общих друзей
	 *
	 * @param idUser   - первый пользователь
	 * @param idFriend - второй пользователь
	 */
	public List<UserDto> getGeneralFriends(Long idUser, Long idFriend) {
		return userRepository.getGeneralFriends(idUser, idFriend);
	}

	/**
	 * Получить пользователя по id
	 *
	 * @param id -  id пользователя
	 * @return - пользователь
	 */
	public UserDto getUser(Long id) {
		return userRepository.getUser(id);
	}

	/**
	 * Получить список пользователей
	 *
	 * @return - список пользователей
	 */
	public List<UserDto> getUsers() {
		return userRepository.getUsers();
	}

	/**
	 * Добавить пользователя
	 *
	 * @param userDto - пользователь
	 * @return - добавленный пользователь
	 */
	public UserDto addUser(UserDto userDto) {
		return userRepository.addUser(userDto);
	}

	/**
	 * Обновить данные пользователя
	 *
	 * @param userDto - пользователь
	 * @return - обновлённый пользователь
	 */
	public UserDto updateUser(UserDto userDto) {
		return userRepository.updateUser(userDto);
	}

	/**
	 * Получить список друзей пользователя
	 *
	 * @param id - id пользователя
	 * @return - список друзей пользователя
	 */
	public HashSet<UserDto> getFriendsUser(Long id) {
		return userRepository.getFriendsUser(id);
	}
}