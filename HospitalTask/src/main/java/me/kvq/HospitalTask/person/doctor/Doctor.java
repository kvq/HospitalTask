package me.kvq.HospitalTask.person.doctor;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import me.kvq.HospitalTask.person.Person;

@Entity
public class Doctor extends Person {
  
  private String position;

  public Doctor() {
    
  }
  
  public Doctor(String firstName, String lastName, String patronymic, 
                LocalDate birthDate,
                String phoneNumber,
                String position) {
    this(0, firstName, lastName, patronymic, birthDate, phoneNumber, position);
  }
  
  public Doctor(long id,String firstName, String lastName, String patronymic, 
                LocalDate birthDate,
                String phoneNumber,
                String position) {
    super(id,firstName, lastName, patronymic, birthDate, phoneNumber);
    this.position = position;
  }
  
  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }
  
}
