package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EntityValidationException extends RuntimeException {
	public EntityValidationException(String className, final String message) {
		super("Ошибка валидации объекта " + className + "\n" + message);
		log.warn("Ошибка валидации объекта " + className + "\n" + message);
	}
}