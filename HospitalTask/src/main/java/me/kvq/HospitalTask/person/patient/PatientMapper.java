package me.kvq.HospitalTask.person.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.kvq.HospitalTask.person.PersonMapper;
import me.kvq.HospitalTask.person.doctor.Doctor;
import me.kvq.HospitalTask.person.doctor.DoctorDAO;

@Service
public class PatientMapper extends PersonMapper<Patient, PatientDTO>{
  
  @Autowired
  DoctorDAO Ddao;

  @Override
  public PatientDTO entityToDto(Patient p) {
    return new PatientDTO(p.getId(),p.getFirstName(),
                          p.getLastName(),
                          p.getPatronymic(),
                          p.getBirthDate(),
                          p.getPhoneNumber(),
                          p.getDoctor().getId());
  }

  @Override
  public Patient dtoToEntity(PatientDTO d) {
    Doctor doc = Ddao.get(d.getDoctor()).orElseGet(null); 
    return new Patient(d.getId(),
                       d.getFirstName(), 
                       d.getLastName(), 
                       d.getPatronymic(), 
                       d.getBirthDate(), 
                       d.getPhoneNumber(),
                       doc);
  }
  
}
