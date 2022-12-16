package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.dto.MpaDto;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
public class MpaController {
	private MpaService mpaService;

	@Autowired
	public MpaController(MpaService mpaService) {
		this.mpaService = mpaService;
	}

	/**
	 * Получить данные о рейтинге
	 *
	 * @param id - id рейтинга
	 * @return - данные о рейтинге
	 */
	@GetMapping("/{id}")
	public MpaDto getMpa(@PathVariable Long id) {

		return mpaService.getMpa(id);
	}

	/**
	 * Получить список рейтингов
	 *
	 * @return - список рейтингов
	 */
	@GetMapping
	public List<MpaDto> getMpa() {
		return mpaService.getMpas();
	}
}
