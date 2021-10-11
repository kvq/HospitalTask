package me.kvq.hospitaltask.exceptionHandler;

import me.kvq.hospitaltask.exception.InvalidDtoException;
import me.kvq.hospitaltask.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class, InvalidDtoException.class})
    public ResponseEntity<ErrorMessageObject> handleException(RuntimeException exception) {
        ErrorMessageObject messageObject = new ErrorMessageObject(exception.getMessage());
        return new ResponseEntity<ErrorMessageObject>(messageObject, HttpStatus.BAD_REQUEST);
    }

}
