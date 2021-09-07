package me.kvq.HospitalTask.utils;

import org.junit.jupiter.api.Test;

class NumberUtilsTest {
  
  @Test
  void test() {
    PhoneNumberUtils.fixPhoneNumber("+380(0542)60-48-61 ");
    PhoneNumberUtils.fixPhoneNumber("0512)36-21-95");
  }

}
