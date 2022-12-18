package ru.yandex.practicum.filmorate.repository.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.dto.UserDto;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;

public interface UserStorage {

	RowMapper<UserDto> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
		return UserDto.builder()
				.id(resultSet.getLong("USER_ID"))
				.email(resultSet.getString("EMAIL"))
				.login(resultSet.getString("LOGIN"))
				.name(resultSet.getString("NAME"))
				.birthday(resultSet.getDate("BIRTH_DATE").toLocalDate())
				.build();
	};

	UserDto getUser(Long idUser);

	List<UserDto> getUsers();

	UserDto addUser(UserDto userDto);

	UserDto updateUser(UserDto userDto);

	HashSet<UserDto> getFriendsUser(Long id);

	void addFriend(Long idUser, Long idFriend);

	void deleteFriend(Long idUser, Long idFriend);

	List<UserDto> getGeneralFriends(Long idUser, Long idFriend);

	boolean validate(UserDto userDto);
}