package me.kvq.HospitalTask.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Patient extends Person {

    @ManyToOne
    private Doctor doctor;

    public Patient() {
        super();
    }

    public Patient(long id, String firstName, String lastName, String patronymic,
                   LocalDate birthDate,
                   String phoneNumber,
                   Doctor doctor) {
        super(id, firstName, lastName, patronymic, birthDate, phoneNumber);
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
