package me.kvq.hospitaltask.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OffWork {
    @Id
    private long id;
    private LocalDate dateFrom;
    private LocalDate dateUntil;
    private String reason;
    @ManyToOne
    private Doctor doctor;

}
