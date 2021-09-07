package me.kvq.HospitalTask.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import me.kvq.HospitalTask.model.Patient;

@Repository
public interface PatientDao extends JpaRepository<Patient, Long>{

}
