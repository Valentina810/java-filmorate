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
public class User implements Cloneable {
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

	private HashSet<User> friends;

	public User clone() {
		User user = User.builder().build();
		user.id = this.id;
		user.email = this.email;
		user.login = this.login;
		user.name = this.name;
		user.birthday = this.birthday;
		user.friends = this.friends;
		return user;
	}
}