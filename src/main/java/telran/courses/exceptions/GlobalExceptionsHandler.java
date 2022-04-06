package telran.courses.exceptions;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//import telran.courses.exceptions.BadRequestException;
import telran.courses.exceptions.GlobalExceptionsHandler;

@RestControllerAdvice
public class GlobalExceptionsHandler {
	static Logger LOG = LoggerFactory.getLogger(GlobalExceptionsHandler.class);
	@ExceptionHandler({BadRequestException.class, ConstraintViolationException.class, MethodArgumentNotValidException.class})
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	String getCustomExceptionText(Exception e) {
		LOG.error("Server has thrown exception of request with message: {}\n",e.getMessage());
		return e.getMessage();
	}
	/* V.R.
	 * What about exception processing code 404 
	 * (Resource not found exception)
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR) 
	String getStandardExceptionText(RuntimeException e) {
		LOG.error("Server has thrown internal exception with message: {}\n",e.getMessage());
		return e.getMessage();
	}
}
