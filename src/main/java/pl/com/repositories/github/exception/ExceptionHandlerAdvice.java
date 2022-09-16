package pl.com.repositories.github.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleExceptionResourceNotFound(ResourceNotFoundException exception) {
        return new ApiError(HttpStatus.NOT_FOUND, exception);
    }
    
    @ExceptionHandler(WrongTreeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleExceptionWrongTreeException(WrongTreeException exception) {
        return new ApiError(HttpStatus.NOT_FOUND, exception);
    }
}