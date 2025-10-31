package lutz.algasensors.manager.api.controller;

import lombok.extern.slf4j.Slf4j;
import lutz.algasensors.manager.domain.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage handleResourceNotFoundException(ResourceNotFoundException ex) {
		log.error(ex.getMessage());
		log.debug(Arrays.toString(ex.getStackTrace()));
		return new ErrorMessage(ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage handleGenericException(Exception ex) {
		log.error(ex.getMessage());
		log.debug(Arrays.toString(ex.getStackTrace()));
		return new ErrorMessage("Ocorreu um erro inesperado");
	}

	public record ErrorMessage(String message) {
	}
}
