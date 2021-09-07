package me.kvq.HospitalTask.utils;

public class PhoneNumberUtils {
  
  public static String fixPhoneNumber(String number) throws IllegalArgumentException{
    number = number.replaceAll("[^\\d.]", "");
    if (!number.startsWith("38")) number = "38" + number; 
    int length = number.length();
    if (length < 12 || length > 13) throw new IllegalArgumentException("Phone number is not valid");
    return number;
  }
  
}
