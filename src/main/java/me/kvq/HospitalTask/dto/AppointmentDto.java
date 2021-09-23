package me.kvq.HospitalTask.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AppointmentDto {
    long id;
    long doctorId;
    long patientId;
    LocalDateTime time;

}
