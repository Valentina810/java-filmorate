package ru.yandex.practicum.filmorate.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
public class FilmDto {
	private Long id;
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

	private Set<Long> likesFromUsers;

	private Integer countLikes;

	private MpaDto mpa;
	private LinkedHashSet<GenreDto> genres;

	public Integer getCountLikes() {
		if (countLikes == null) return 0;
		else return countLikes;
	}

	public Set<Long> getLikesFromUsers() {
		if (likesFromUsers == null) {
			return new HashSet<>();
		} else return likesFromUsers;
	}

	public Set<GenreDto> getGenres() {
		if (genres == null) {
			return new HashSet<>();
		} else return genres;
	}
}
