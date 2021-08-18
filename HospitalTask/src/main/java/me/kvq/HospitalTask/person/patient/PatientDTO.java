package me.kvq.HospitalTask.person.patient;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import me.kvq.HospitalTask.person.Person;
import me.kvq.HospitalTask.person.PersonDTO;
import me.kvq.HospitalTask.person.doctor.Doctor;

public class PatientDTO extends PersonDTO {

  private long doctor;
  
  public PatientDTO() {
    
  }
  
  public PatientDTO(long id,String firstName, String lastName, String fathersName, 
                 LocalDate birthDate,
                 String phoneNumber,
                 long doctor) {

    super(id,firstName, lastName, fathersName, birthDate, phoneNumber);
    this.doctor = doctor;
  }
  
  public long getDoctor() {
    return doctor;
  }

  public void setDoctor(long id) {
    this.doctor = id;
  }

}
