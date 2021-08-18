package me.kvq.HospitalTask.person.patient;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.kvq.HospitalTask.person.PersonDAO;
import me.kvq.HospitalTask.person.doctor.Doctor;
import me.kvq.HospitalTask.repositories.PatientRepo;

@Service
public class PatientDAO extends PersonDAO<Patient>{

  @Autowired
  public PatientDAO(PatientRepo repository) {
    super(repository);
  }
  
}
