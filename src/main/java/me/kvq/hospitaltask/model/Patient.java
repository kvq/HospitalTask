package me.kvq.hospitaltask.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    private long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    @Column(columnDefinition = "DATE")
    private LocalDate birthDate;
    private String phoneNumber;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "doctor_patients",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private List<Doctor> doctors = new ArrayList<>();

}
