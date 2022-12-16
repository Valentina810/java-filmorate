package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.dto.GenreDto;
import ru.yandex.practicum.filmorate.repository.dao.impl.GenreRepository;

import java.util.List;

@Service
public class GenreService {
	private GenreRepository genreRepository;

	@Autowired
	public GenreService(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}


	public GenreDto getGenre(Long idGenre) {
		return genreRepository.getGenre(idGenre);
	}

	public List<GenreDto> getGenres() {
		return genreRepository.getGenres();
	}
}