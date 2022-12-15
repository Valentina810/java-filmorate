package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.dto.GenreDto;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
	private GenreService genreService;

	@Autowired
	public GenreController(GenreService genreService) {
		this.genreService = genreService;
	}

	/**
	 * Получить данные о жанре
	 *
	 * @param id - id жанра
	 * @return - данные о жанре
	 */
	@GetMapping("/{id}")
	public GenreDto getGenre(@PathVariable Long id) {

		return genreService.getGenre(id);
	}

	/**
	 * Получить список жанров
	 *
	 * @return - список жанров
	 */
	@GetMapping
	public List<GenreDto> getGenre() {
		return genreService.getGenres();
	}
}