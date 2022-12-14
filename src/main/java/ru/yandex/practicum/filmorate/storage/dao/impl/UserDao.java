package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exception.EntityValidationException;
import ru.yandex.practicum.filmorate.model.UserDto;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@Qualifier("UserDao")
@Log
public class UserDao implements UserStorage {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public UserDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public UserDto getUser(Long userId) {
		try {
			UserDto userDto = jdbcTemplate.queryForObject(
					"SELECT * " +
							"FROM fr_user " +
							"WHERE user_id = ?",
					new Object[]{userId}, ROW_MAPPER);
			userDto.setFriends(getFriendsUser(userId));
			return userDto;
		} catch (DataAccessException dataAccessException) {
			throw new EntityNotFoundException(UserDto.class.getSimpleName(),
					" с id " + userId + " не найден!");
		}
	}

	@Override
	public List<UserDto> getUsers() {
		return jdbcTemplate.query(
				"SELECT * " +
						"FROM fr_user", ROW_MAPPER);
	}

	@Override
	public UserDto addUser(UserDto userDto) {
		if (validate(userDto)) {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(
						"INSERT INTO fr_user(email, login, name, birth_date) " +
								"VALUES (?, ?, ?, ?)",
						new String[]{"user_id"});
				ps.setString(1, userDto.getEmail());
				ps.setString(2, userDto.getLogin());
				ps.setString(3, userDto.getName());
				ps.setString(4, String.valueOf(userDto.getBirthday()));
				return ps;
			}, keyHolder);
			userDto.setId(keyHolder.getKey().longValue());
		}
		return userDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto) {
		if (validate(userDto)) {
			getUser(userDto.getId());
			jdbcTemplate.update(
					"UPDATE fr_user " +
							"SET email= ?, login=?, name=?,birth_date=? " +
							"WHERE user_id=?",
					userDto.getEmail(), userDto.getLogin(), userDto.getName(), userDto.getBirthday(), userDto.getId());
		}
		return userDto;
	}

	@Override
	public HashSet<UserDto> getFriendsUser(Long userId) {
		return new HashSet<>(jdbcTemplate.query(
				"SELECT *" +
						"FROM fr_user AS u " +
						"WHERE u.user_id IN (" +
						"SELECT friend_id " +
						"FROM fr_friendship AS f " +
						"WHERE (f.user_id=?) AND (f.is_status=true))",
				new Object[]{userId}, ROW_MAPPER));
	}

	@Override
	public void addFriend(Long userId, Long idFriend) {
		try {
			jdbcTemplate.update(
					"INSERT INTO FR_FRIENDSHIP(user_id, friend_id, is_status)" +
							"VALUES (?, ?, ?)",
					userId, idFriend, true);
			jdbcTemplate.update(
					"INSERT INTO FR_FRIENDSHIP(user_id, friend_id, is_status)" +
							"VALUES (?, ?, ?)",
					idFriend, userId, false);
		} catch (DataAccessException dataAccessException) {
			throw new EntityNotFoundException(UserDto.class.getSimpleName(),
					" с id " + userId + " или " + idFriend + " не найден!");
		}
	}

	@Override
	public void deleteFriend(Long userId, Long idFriend) {
		jdbcTemplate.update(
				"DELETE FROM fr_friendship " +
						"WHERE (user_id = ?) AND (friend_id= ?) " +
						"OR (user_id = ?) AND (friend_id= ?)", userId, idFriend, idFriend, userId);
	}

	@Override
	public List<UserDto> getGeneralFriends(Long userId, Long idFriend) {
		HashSet<UserDto> friendsUser = getFriendsUser(userId);
		HashSet<UserDto> friendsFriend = getFriendsUser(idFriend);
		if (friendsUser.isEmpty() || friendsFriend.isEmpty())
			return new ArrayList<>();
		else {
			ArrayList<UserDto> generalFriends = new ArrayList<>();
			friendsUser.forEach(e -> {
				if (friendsFriend.contains(e)) {
					generalFriends.add(e);
				}
			});
			return generalFriends;
		}
	}

	public boolean validate(UserDto userDto) {
		String value;
		if ((userDto.getEmail() == null) || ("".equals(userDto.getEmail()) || !userDto.getEmail().contains("@"))) {
			value = "email";
		} else if ((userDto.getLogin() == null) || ("".equals(userDto.getLogin()) || userDto.getLogin().contains(" "))) {
			value = "login";
		} else if ((userDto.getBirthday() == null) || (userDto.getBirthday().isAfter(LocalDate.now()))) {
			value = "birthday";
		} else if ((userDto.getName() == null) || ("".equals(userDto.getName()))) {
			userDto.setName(userDto.getLogin());
			return true;
		} else return true;
		throw new EntityValidationException(userDto.getClass().getSimpleName(), "поле " + value + " не прошло валидацию");
	}
}