package me.kvq.hospitaltask.utils;

import me.kvq.hospitaltask.exception.InvalidPhoneNumberException;

public class PhoneNumberUtils {

    private PhoneNumberUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void checkPhoneNumber(String number) throws InvalidPhoneNumberException {
        if (number == null) {
            return;
        }
        int length = number.length();
        if (!number.matches("^[0-9]+$")
                || !number.startsWith("38")
                || length < 12 || length > 13) {
            throw new InvalidPhoneNumberException(number);
        }
    }

}
