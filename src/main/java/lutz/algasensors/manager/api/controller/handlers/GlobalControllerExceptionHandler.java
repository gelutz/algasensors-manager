package lutz.algasensors.manager.api.controller.handlers;

import lombok.extern.slf4j.Slf4j;
import lutz.algasensors.manager.domain.exceptions.ClientBadGatewayException;
import lutz.algasensors.manager.domain.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.channels.ClosedChannelException;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler({
			SocketTimeoutException.class,
			ConnectException.class,
			ClosedChannelException.class
	})
	public ProblemDetail handleNetworkExceptions(IOException ex) {
		ProblemDetail problemDetail = ProblemDetail
				.forStatus(HttpStatus.GATEWAY_TIMEOUT);
		problemDetail.setTitle("Gateway Timeout");
		problemDetail.setDetail(ex.getMessage());
		problemDetail.setType(URI.create("/errors/gatewat-timeout"));

		return problemDetail;
	}

	@ExceptionHandler(ClientBadGatewayException.class)
	public ProblemDetail handleNetworkExceptions(ClientBadGatewayException ex) {
		ProblemDetail problemDetail = ProblemDetail
				.forStatus(HttpStatus.BAD_GATEWAY);
		problemDetail.setTitle("Bad Gateway");
		problemDetail.setDetail(ex.getMessage());
		problemDetail.setType(URI.create("/errors/bad-gatewat"));

		return problemDetail;
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex) {
		ProblemDetail problemDetail = ProblemDetail
				.forStatus(HttpStatus.NOT_FOUND);
		problemDetail.setTitle("Resource not found");
		problemDetail.setType(URI.create("/errors/not-found"));

		return problemDetail;
	}

	@ExceptionHandler(Exception.class)
	public ProblemDetail handleGenericException(Exception ex) {
		log.error(ex.getMessage());
		ex.printStackTrace();

		ProblemDetail problemDetail = ProblemDetail
				.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		problemDetail.setTitle("Unexpected error");
		problemDetail.setType(URI.create("/errors/unexpected-error"));

		return problemDetail;
	}
}
