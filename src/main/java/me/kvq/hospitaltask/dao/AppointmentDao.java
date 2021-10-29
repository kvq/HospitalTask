package me.kvq.hospitaltask.dao;

import me.kvq.hospitaltask.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentDao extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByDoctorId(long id);

    List<Appointment> findAllByPatientId(long id);

    boolean existsByDoctorIdAndDateTime(long id, LocalDateTime time);

    boolean existsByPatientIdAndDateTime(long id, LocalDateTime time);

}
