package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.FilmDto;
import ru.yandex.practicum.filmorate.storage.dao.impl.FilmDao;

import java.util.List;

@Service
public class FilmService {

	private FilmDao filmDao;

	@Autowired
	public FilmService(FilmDao filmDao) {
		this.filmDao = filmDao;
	}

	/**
	 * Добавление лайка фильму
	 *
	 * @param idFilm - id фильма
	 * @param idUser - id пользователя
	 */
	public void addLike(Long idFilm, Long idUser) {
		filmDao.addLike(idFilm, idUser);
	}

	/**
	 * Удаление лайка фильма
	 *
	 * @param idFilm - id фильма
	 * @param idUser - id пользователя
	 */
	public void deleteLike(Long idFilm, Long idUser) {
		filmDao.deleteLike(idFilm, idUser);
	}

	/**
	 * Получение списка популярных фильмов
	 *
	 * @param limit - количество фильмов
	 * @return - список фильмов
	 */
	public List<FilmDto> getPopularMovies(Integer limit) {
		return filmDao.getPopularMovies(limit);
	}

	/**
	 * Получить фильм по id
	 *
	 * @param id -  id фильма
	 * @return - данные о фильме
	 */
	public FilmDto getFilm(Long id) {
		return filmDao.getFilm(id);
	}

	/**
	 * Получить все фильмы
	 *
	 * @return - список фильмов
	 */
	public List<FilmDto> getFilms() {
		return filmDao.getFilms();
	}

	/**
	 * Добавить фильм
	 *
	 * @param filmDto -  фильм
	 * @return - добавленный фильм
	 */
	public FilmDto addFilm(FilmDto filmDto) {
		return filmDao.addFilm(filmDto);
	}

	/**
	 * Обновить информацию о фильме
	 *
	 * @param filmDto -  фильм
	 * @return - обновленный фильм
	 */
	public FilmDto updateFilm(FilmDto filmDto) {
		return filmDao.updateFilm(filmDto);
	}
}