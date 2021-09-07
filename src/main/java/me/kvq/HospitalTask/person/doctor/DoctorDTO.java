package me.kvq.HospitalTask.person.doctor;

import java.time.LocalDate;
import me.kvq.HospitalTask.person.PersonDTO;

public class DoctorDTO extends PersonDTO {

  private String position;

  public DoctorDTO() {
    
  }
  
  public DoctorDTO(long id,String firstName, String lastName, String fathersName, 
                LocalDate birthDate,
                String phoneNumber,
                String position) {
    
    super(id,firstName, lastName, fathersName, birthDate, phoneNumber);
    this.position = position;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }
  
}
