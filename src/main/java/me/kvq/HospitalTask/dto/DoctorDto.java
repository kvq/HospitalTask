package me.kvq.HospitalTask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    private long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate birthDate;
    private String phoneNumber;
    private String position;

}
