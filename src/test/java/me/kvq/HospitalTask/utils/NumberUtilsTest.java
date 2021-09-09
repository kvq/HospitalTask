package me.kvq.HospitalTask.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NumberUtilsTest {

    @Test
    @DisplayName("Valid Mobile Number Check")
    void validMobileNumberParseTest() {
        String number = PhoneNumberUtils.fixPhoneNumber("+38(067)2804631");
        assertEquals("380672804631", number);
    }

    @Test
    @DisplayName("Invalid Mobile Number Check")
    void invalidMobileParseTest() {
        assertThrows(IllegalArgumentException.class, () -> PhoneNumberUtils.fixPhoneNumber("+38015152"));
    }

    @Test
    @DisplayName("Valid Landline Number Check")
    void validLandlineParseTest() {
        String number = PhoneNumberUtils.fixPhoneNumber("0444618061");
        assertEquals("380444618061", number);
    }

    @Test
    @DisplayName("Invalid Landline Number Check")
    void invalidLandlineParseTest() {
        assertThrows(IllegalArgumentException.class, () -> PhoneNumberUtils.fixPhoneNumber("+04423343242382"));
    }
}