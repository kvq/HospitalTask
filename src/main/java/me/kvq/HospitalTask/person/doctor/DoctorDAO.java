package me.kvq.HospitalTask.person.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.kvq.HospitalTask.person.PersonDAO;
import me.kvq.HospitalTask.repositories.DoctorRepo;

@Service
public class DoctorDAO extends PersonDAO<Doctor> {

  @Autowired
  public DoctorDAO(DoctorRepo repository) {
    super(repository);
  }
  
 
  
}
