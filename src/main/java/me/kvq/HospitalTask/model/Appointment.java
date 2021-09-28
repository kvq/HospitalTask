package me.kvq.HospitalTask.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
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
