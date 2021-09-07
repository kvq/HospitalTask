package me.kvq.HospitalTask.dto;

import java.time.LocalDate;

public class DoctorDto extends PersonDto {

    private String position;

    public DoctorDto() {

    }

    public DoctorDto(long id, String firstName, String lastName, String fathersName,
                     LocalDate birthDate,
                     String phoneNumber,
                     String position) {

        super(id, firstName, lastName, fathersName, birthDate, phoneNumber);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
