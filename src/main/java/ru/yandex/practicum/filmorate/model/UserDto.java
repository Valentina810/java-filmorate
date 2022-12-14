package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;

@Data
@Builder
public class UserDto implements Cloneable {
	private Long id;
	@Email
	@NotNull
	@NotBlank
	private String email;
	@NotNull
	@NotBlank
	private String login;
	private String name;
	@NotNull
	@PastOrPresent
	private LocalDate birthday;

	private HashSet<UserDto> friends;

	public UserDto clone() {
		UserDto userDto = UserDto.builder().build();
		userDto.id = this.id;
		userDto.email = this.email;
		userDto.login = this.login;
		userDto.name = this.name;
		userDto.birthday = this.birthday;
		userDto.friends = this.friends;
		return userDto;
	}
}