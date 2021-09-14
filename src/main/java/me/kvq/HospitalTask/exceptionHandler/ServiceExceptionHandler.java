package me.kvq.HospitalTask.exceptionHandler;

import me.kvq.HospitalTask.exception.InvalidPhoneNumberException;
import me.kvq.HospitalTask.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = InvalidPhoneNumberException.class)
    public ResponseEntity<ErrorMessageObject> handlePhoneNumberException(InvalidPhoneNumberException exception, WebRequest request){
        String value = exception.getEnteredValue();
        String message = "Number you entered (" + value + ") is invalid.";
        ErrorMessageObject messageObject = new ErrorMessageObject("InvalidPhoneNumber", message);
        return new ResponseEntity(messageObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException exception, WebRequest request){
        long value = exception.getUserId();
        String message = "User by id " + value + " not found.";
        ErrorMessageObject messageObject = new ErrorMessageObject("UserNotFound", message);
        return new ResponseEntity(messageObject,HttpStatus.BAD_REQUEST);
    }

}
