package me.kvq.HospitalTask.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    @ManyToOne
    @JoinColumn(name = "doctor")
    Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "patient")
    Patient patient;
    LocalDateTime dateTime;

}
