package ru.yandex.practicum.filmorate.exception;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FilmNotFoundException extends RuntimeException {
	public FilmNotFoundException(final String message) {
		super(message);
		log.info(message);
	}
}
