package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.dto.MpaDto;
import ru.yandex.practicum.filmorate.repository.dao.impl.MpaRepository;

import java.util.List;

@Service
public class MpaService {

	private final MpaRepository mpaRepository;

	@Autowired
	public MpaService(MpaRepository mpaRepository) {
		this.mpaRepository = mpaRepository;
	}

	public MpaDto getMpa(Long id) {
		return mpaRepository.getMpa(id);
	}

	public List<MpaDto> getMpas() {
		return mpaRepository.getMpas();
	}
}