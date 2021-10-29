package me.kvq.hospitaltask.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OffWorkDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateUntil;
    private DoctorDto doctor;
    private String reason;

}
