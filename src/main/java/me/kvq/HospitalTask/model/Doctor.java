package me.kvq.HospitalTask.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    @Column(columnDefinition = "DATE")
    private LocalDate birthDate;
    private String phoneNumber;
    private String position;

}
