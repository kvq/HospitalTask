package me.kvq.HospitalTask.model;

import me.kvq.HospitalTask.model.Person;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Doctor extends Person {

    private String position;

    public Doctor() {
        super();
    }

    public Doctor(String firstName, String lastName, String patronymic,
                  LocalDate birthDate,
                  String phoneNumber,
                  String position) {
        this(0, firstName, lastName, patronymic, birthDate, phoneNumber, position);
    }

    public Doctor(long id, String firstName, String lastName, String patronymic,
                  LocalDate birthDate,
                  String phoneNumber,
                  String position) {
        super(id, firstName, lastName, patronymic, birthDate, phoneNumber);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
