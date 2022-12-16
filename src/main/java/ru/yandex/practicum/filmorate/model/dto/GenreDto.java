package ru.yandex.practicum.filmorate.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreDto {
	private Integer id;
	private String name;
}