package me.kvq.HospitalTask.exceptionHandler;

import me.kvq.HospitalTask.exception.InvalidPhoneNumberException;
import me.kvq.HospitalTask.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class, InvalidPhoneNumberException.class})
    public ResponseEntity<ErrorMessageObject> handlePhoneNumberException(RuntimeException exception, WebRequest request){;
        ErrorMessageObject messageObject = new ErrorMessageObject(exception.getMessage());
        return new ResponseEntity(messageObject, HttpStatus.BAD_REQUEST);
    }

}
