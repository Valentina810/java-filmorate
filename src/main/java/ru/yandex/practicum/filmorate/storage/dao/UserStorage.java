package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;

public interface UserStorage {

	RowMapper<User> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
		return User.builder()
				.id(resultSet.getLong("USER_ID"))
				.email(resultSet.getString("EMAIL"))
				.login(resultSet.getString("LOGIN"))
				.name(resultSet.getString("NAME"))
				.birthday(resultSet.getDate("BIRTH_DATE").toLocalDate())
				.build();
	};

	User getUser(Long idUser);

	List<User> getUsers();

	User addUser(User user);

	User updateUser(User user);

	boolean validate(User user);

	HashSet<User> getFriendsUser(Long id);
}