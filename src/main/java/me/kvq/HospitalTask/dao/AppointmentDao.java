package me.kvq.HospitalTask.dao;

import me.kvq.HospitalTask.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentDao extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByDoctor(long id);

    List<Appointment> findAllByPatient(long id);

}
