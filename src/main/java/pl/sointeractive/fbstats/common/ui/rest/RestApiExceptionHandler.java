package pl.sointeractive.fbstats.common.ui.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.sointeractive.fbstats.application.exceptions.NotFoundException;

@ControllerAdvice
public class RestApiExceptionHandler {

	@ExceptionHandler(value = {NotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ExceptionResponse handleResourceNotFoundException(Exception ex) {
		return exceptionResponseFromException(ex);
	}

	@ExceptionHandler(value = {Throwable.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ExceptionResponse handleInternalServerError(Throwable ex) {
		return exceptionResponseFromException(ex);
	}

	private ExceptionResponse exceptionResponseFromException(Throwable ex) {
		ExceptionResponse errorResponse = new ExceptionResponse(
			ex.getClass().getSimpleName(),
			ex.getMessage()
		);
		return errorResponse;
	}
}
