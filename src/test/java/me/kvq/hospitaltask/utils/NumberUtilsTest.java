package me.kvq.hospitaltask.utils;

import me.kvq.hospitaltask.exception.InvalidPhoneNumberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NumberUtilsTest {

    @Test
    @DisplayName("Valid Mobile Number Checking, no exception shall be thrown")
    void validMobileNumberParseTest() {
        PhoneNumberUtils.checkPhoneNumber("380672844631");
    }

    @Test
    @DisplayName("Invalid Mobile Number Parsing, expecting exception")
    void invalidMobileParseTest() {
        assertThrows(InvalidPhoneNumberException.class, () -> {
            PhoneNumberUtils.checkPhoneNumber("+38015152");
        });
    }

    @Test
    @DisplayName("Valid Landline Number Parsing, no exception shall be thrown")
    void validLandlineParseTest() {
        PhoneNumberUtils.checkPhoneNumber("380444618061");
    }

    @Test
    @DisplayName("Invalid Landline Number Parsing, expecting exception")
    void invalidLandlineParseTest() {
        assertThrows(InvalidPhoneNumberException.class, () -> {
            PhoneNumberUtils.checkPhoneNumber("+04423343242382");
        });
    }
    
}
