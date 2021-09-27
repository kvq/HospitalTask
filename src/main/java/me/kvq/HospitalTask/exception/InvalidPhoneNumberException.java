package me.kvq.HospitalTask.exception;

public class InvalidPhoneNumberException extends InvalidDtoException {

    public InvalidPhoneNumberException(String invalidValue) {
        super("Number you entered (" + invalidValue + ") is invalid.");
    }

}
