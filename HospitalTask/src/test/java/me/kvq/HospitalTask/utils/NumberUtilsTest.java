package me.kvq.HospitalTask.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class NumberUtilsTest {
  
  @Test
  void test() {
    PhoneNumberUtils utils = new PhoneNumberUtils();
    utils.fixPhoneNumber("+380(0542)60-48-61 ");
    utils.fixPhoneNumber("0512)36-21-95");
  }

}
