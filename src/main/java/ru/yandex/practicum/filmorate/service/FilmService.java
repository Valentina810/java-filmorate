package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.repository.dao.impl.FilmRepository;

import java.util.List;

@Service
public class FilmService {

	private FilmRepository filmRepository;

	@Autowired
	public FilmService(FilmRepository filmRepository) {
		this.filmRepository = filmRepository;
	}

	/**
	 * Добавление лайка фильму
	 *
	 * @param idFilm - id фильма
	 * @param idUser - id пользователя
	 */
	public void addLike(Long idFilm, Long idUser) {
		filmRepository.addLike(idFilm, idUser);
	}

	/**
	 * Удаление лайка фильма
	 *
	 * @param idFilm - id фильма
	 * @param idUser - id пользователя
	 */
	public void deleteLike(Long idFilm, Long idUser) {
		filmRepository.deleteLike(idFilm, idUser);
	}

	/**
	 * Получение списка популярных фильмов
	 *
	 * @param limit - количество фильмов
	 * @return - список фильмов
	 */
	public List<FilmDto> getPopularMovies(Integer limit) {
		return filmRepository.getPopularMovies(limit);
	}

	/**
	 * Получить фильм по id
	 *
	 * @param id -  id фильма
	 * @return - данные о фильме
	 */
	public FilmDto getFilm(Long id) {
		return filmRepository.getFilm(id);
	}

	/**
	 * Получить все фильмы
	 *
	 * @return - список фильмов
	 */
	public List<FilmDto> getFilms() {
		return filmRepository.getFilms();
	}

	/**
	 * Добавить фильм
	 *
	 * @param filmDto -  фильм
	 * @return - добавленный фильм
	 */
	public FilmDto addFilm(FilmDto filmDto) {
		return filmRepository.addFilm(filmDto);
	}

	/**
	 * Обновить информацию о фильме
	 *
	 * @param filmDto -  фильм
	 * @return - обновленный фильм
	 */
	public FilmDto updateFilm(FilmDto filmDto) {
		return filmRepository.updateFilm(filmDto);
	}
}