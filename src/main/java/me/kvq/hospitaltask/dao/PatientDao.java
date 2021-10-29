package me.kvq.hospitaltask.dao;

import me.kvq.hospitaltask.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDao extends JpaRepository<Patient, Long> {

}
