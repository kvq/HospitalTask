package me.kvq.HospitalTask.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NumberUtilsTest {

    @Test
    @DisplayName("Valid Mobile Number +38(067)2804631")
    void testValidMobile() {
        String number = PhoneNumberUtils.fixPhoneNumber("+38(067)2804631");
        assertEquals("380672804631", number);
    }

    @Test
    @DisplayName("Invalid Mobile Number +38015152")
    void testInvalidMobile() {
        assertThrows(IllegalArgumentException.class, () -> PhoneNumberUtils.fixPhoneNumber("+38015152"));
    }

    @Test
    @DisplayName("Valid Landline Number0 0444618061")
    void testValidLandline() {
        String number = PhoneNumberUtils.fixPhoneNumber("0444618061");
        assertEquals("380444618061", number);
    }

    @Test
    @DisplayName("Invalid Landline Number +04423343242382")
    void testInvalidLandline() {
        assertThrows(IllegalArgumentException.class, () -> PhoneNumberUtils.fixPhoneNumber("+04423343242382"));
    }
}
