package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@Builder
@EqualsAndHashCode(exclude = {"id"})
public class Film {
	private Integer id;
	@NotNull
	@NotBlank
	private String name;
	@NotNull
	@NotBlank
	private String description;
	@NotNull
	private LocalDate releaseDate;
	@NotNull
	@Positive
	private Integer duration;//inMinutes
}
