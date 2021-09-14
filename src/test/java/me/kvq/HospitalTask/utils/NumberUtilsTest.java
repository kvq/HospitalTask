package me.kvq.HospitalTask.utils;

import me.kvq.HospitalTask.exception.InvalidPhoneNumberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NumberUtilsTest {

    @Test
    @DisplayName("Valid Mobile Number Parsing")
    void validMobileNumberParseTest() {
        String number = PhoneNumberUtils.fixPhoneNumber("+38(067)2804631");
        assertEquals("380672804631", number);
    }

    @Test
    @DisplayName("Invalid Mobile Number Parsing, expecting exception")
    void invalidMobileParseTest() {
        assertThrows(InvalidPhoneNumberException.class, () -> {
            PhoneNumberUtils.fixPhoneNumber("+38015152");
        });
    }

    @Test
    @DisplayName("Valid Landline Number Parsing")
    void validLandlineParseTest() {
        String number = PhoneNumberUtils.fixPhoneNumber("0444618061");
        assertEquals("380444618061", number);
    }

    @Test
    @DisplayName("Invalid Landline Number Parsing, expecting exception")
    void invalidLandlineParseTest() {
        assertThrows(InvalidPhoneNumberException.class, () -> {
            PhoneNumberUtils.fixPhoneNumber("+04423343242382");
        });
    }

}
