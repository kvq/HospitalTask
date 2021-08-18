package me.kvq.HospitalTask.person.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.kvq.HospitalTask.person.PersonMapper;

@Service
public class DoctorMapper extends PersonMapper<Doctor, DoctorDTO>{
  
  @Autowired
  DoctorDAO Ddao;

  @Override
  public DoctorDTO entityToDto(Doctor p) {
    return new DoctorDTO(p.getId(),
                         p.getFirstName(),
                         p.getLastName(),
                         p.getPatronymic(),
                         p.getBirthDate(),
                         p.getPhoneNumber(),
                         p.getPosition());
  }

  @Override
  public Doctor dtoToEntity(DoctorDTO d) {
    return new Doctor(d.getId(),
                      d.getFirstName(), 
                      d.getLastName(), 
                      d.getPatronymic(), 
                      d.getBirthDate(), 
                      d.getPhoneNumber(),
                      d.getPosition());
  }
  
}
