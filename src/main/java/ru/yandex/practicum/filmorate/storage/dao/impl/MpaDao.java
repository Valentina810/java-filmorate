package ru.yandex.practicum.filmorate.storage.dao.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MpaDto;

import java.sql.ResultSet;

@Component
@Qualifier("MpaDao")
public class MpaDao {

	RowMapper<MpaDto> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
		return MpaDto.builder()
				.id(resultSet.getInt("MPA_ID"))
				.name(resultSet.getString("NAME"))
				.build();
	};
}
