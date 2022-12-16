package ru.yandex.practicum.filmorate.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.dto.MpaDto;

import java.sql.ResultSet;
import java.util.List;

@Component
@Qualifier("MpaDao")
public class MpaRepository {

	private final JdbcTemplate jdbcTemplate;

	RowMapper<MpaDto> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
		return MpaDto.builder()
				.id(resultSet.getInt("MPA_ID"))
				.name(resultSet.getString("NAME"))
				.build();
	};

	@Autowired
	public MpaRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<MpaDto> getMpas() {
		return jdbcTemplate.query(
				"SELECT * " +
						"FROM fr_mpa", ROW_MAPPER);
	}

	public MpaDto getMpa(Long mpaId) {
		try {
			return jdbcTemplate.queryForObject("SELECT * " +
					"FROM fr_mpa " +
					"WHERE mpa_id=?", new Object[]{mpaId}, ROW_MAPPER);
		} catch (DataAccessException dataAccessException) {
			throw new EntityNotFoundException(MpaDto.class.getSimpleName(),
					" с id " + mpaId + " не найден!");
		}
	}
}