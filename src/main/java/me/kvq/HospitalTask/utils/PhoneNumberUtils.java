package me.kvq.HospitalTask.utils;

import me.kvq.HospitalTask.exception.InvalidPhoneNumberException;

public class PhoneNumberUtils {
  
  public static String fixPhoneNumber(String number) throws InvalidPhoneNumberException{
    if (number == null) return null;
    number = number.replaceAll("[^\\d.]", "");
    if (!number.startsWith("38")) number = "38" + number; 
    int length = number.length();
    if (length < 12 || length > 13) throw new InvalidPhoneNumberException(number);
    return number;
  }
  
}
