package me.kvq.HospitalTask.dao;

import me.kvq.HospitalTask.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDao extends JpaRepository<Patient, Long> {

}
