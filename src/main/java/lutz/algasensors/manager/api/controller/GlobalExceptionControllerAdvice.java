package lutz.algasensors.manager.api.controller;

import lombok.extern.slf4j.Slf4j;
import lutz.algasensors.manager.domain.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionControllerAdvice {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
		log.error(ex.getMessage());
		log.debug(Arrays.toString(ex.getStackTrace()));
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<?> handleGenericException(Exception ex) {
		log.error(ex.getMessage());
		log.debug(Arrays.toString(ex.getStackTrace()));
		return ResponseEntity.internalServerError().body(Map.of("mensagem", ex.getMessage()));
	}
}
