package ru.yandex.practicum.filmorate.exception;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FilmValidationException extends RuntimeException {
	public FilmValidationException(final String message) {
		super(message);
		log.info(message);
	}
}