package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;

import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
	public User getUser(Long idUser) {
		User user = null;
		try {
			user = jdbcTemplate.queryForObject(
					"SELECT * " +
							"FROM fr_user " +
							"WHERE user_id = ?",
					new Object[]{idUser}, ROW_MAPPER);
			user.setFriends(getFriendsUser(idUser));
		} catch (DataAccessException dataAccessException) {
			log.info("Не удалось найти объект типа User с идентификатором " + idUser);
		}
		return user;
	}

	@Override
	public List<User> getUsers() {
		return jdbcTemplate.query(
				"SELECT * " +
						"FROM fr_user", ROW_MAPPER);
	}

	@Override
	public User addUser(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO fr_user(email, login, name, birth_date) " +
							"VALUES (?, ?, ?, ?)",
					new String[]{"user_id"});
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getLogin());
			ps.setString(3, user.getName());
			ps.setString(4, String.valueOf(user.getBirthday()));
			return ps;
		}, keyHolder);
		user.setId(keyHolder.getKey().longValue());
		return user;
	}

	@Override
	public User updateUser(User user) {
		jdbcTemplate.update(
				"UPDATE fr_user " +
						"SET email= ?, login=?, name=?,birth_date=? " +
						"WHERE user_id=?",
				user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
		return user;
	}

	@Override
	public boolean validate(User user) {
		return false;
	}

	@Override
	public HashSet<User> getFriendsUser(Long id) {
		Set<User> collect = jdbcTemplate.query(
				"SELECT *" +
						"FROM fr_user AS u " +
						"WHERE u.USER_ID IN (" +
						"SELECT FRIEND_ID " +
						"FROM FR_FRIENDSHIP AS f " +
						"WHERE (f.USER_ID=?) AND (f.IS_STATUS=true));",
				new Object[]{id}, ROW_MAPPER).stream().collect(Collectors.toSet());
		return new HashSet<>(collect);
	}
}