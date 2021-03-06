package me.kvq.hospitaltask.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Setter
public class AppointmentDto {
    private long id;
    private DoctorDto doctor;
    private PatientDto patient;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateTime;

}
