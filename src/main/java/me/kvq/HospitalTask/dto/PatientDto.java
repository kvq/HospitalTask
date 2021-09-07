package me.kvq.HospitalTask.dto;

import java.time.LocalDate;

public class PatientDto extends PersonDto {

    private long doctor;

    public PatientDto() {

    }

    public PatientDto(long id, String firstName, String lastName, String fathersName,
                      LocalDate birthDate,
                      String phoneNumber,
                      long doctor) {

        super(id, firstName, lastName, fathersName, birthDate, phoneNumber);
        this.doctor = doctor;
    }

    public long getDoctor() {
        return doctor;
    }

    public void setDoctor(long id) {
        this.doctor = id;
    }

}
