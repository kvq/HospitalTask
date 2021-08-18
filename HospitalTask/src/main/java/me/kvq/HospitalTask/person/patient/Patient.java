package me.kvq.HospitalTask.person.patient;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import me.kvq.HospitalTask.person.Person;
import me.kvq.HospitalTask.person.doctor.Doctor;

@Entity
public class Patient extends Person {
  
  @ManyToOne
  private Doctor doctor;
  
  public Patient() {
    super();
  }
  
  public Patient(long id,String firstName, String lastName, String patronymic, 
                 LocalDate birthDate,
                 String phoneNumber,
                 Doctor doctor) {
    super(id,firstName, lastName, patronymic, birthDate, phoneNumber);
    this.doctor = doctor;
  }
  
  public Patient(String firstName, String lastName, String patronymic, 
                 LocalDate birthDate,
                 String phoneNumber,
                 Doctor doctor) {
    this(0, firstName, lastName, patronymic, birthDate, phoneNumber, doctor);
  }

  public Doctor getDoctor() {
    return doctor;
  }

  public void setDoctor(Doctor doctor) {
    this.doctor = doctor;
  }

}
