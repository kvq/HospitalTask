package me.kvq.HospitalTask.person;

import java.time.LocalDate;

public abstract class PersonDTO {

  private long id;
  private String firstName;
  private String lastName;
  private String patronymic;

  private LocalDate birthDate;
  private String phoneNumber;
  
  public PersonDTO() {
    
  }
  
  public PersonDTO(long id,String firstName, String lastName, String patronymic, LocalDate birthDate,
      String phoneNumber) {
    super();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.patronymic = patronymic;
    this.birthDate = birthDate;
    this.phoneNumber = phoneNumber;
  }

  
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getPatronymic() {
    return patronymic;
  }
  public void setPatronymic(String patronymic) {
    this.patronymic = patronymic;
  }
  public LocalDate getBirthDate() {
    return birthDate;
  }
  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }
  public String getPhoneNumber() {
    return phoneNumber;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  } 
}
