package me.kvq.HospitalTask.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidPhoneNumberException extends RuntimeException{
    private String enteredValue;

}
