package me.kvq.HospitalTask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import me.kvq.HospitalTask.person.patient.Patient;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Long>{

}
