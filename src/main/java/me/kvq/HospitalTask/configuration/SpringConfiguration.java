package me.kvq.HospitalTask.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import me.kvq.HospitalTask.logic.HospitalService;
import me.kvq.HospitalTask.person.doctor.Doctor;
import me.kvq.HospitalTask.person.doctor.DoctorDAO;
import me.kvq.HospitalTask.person.doctor.DoctorDTO;
import me.kvq.HospitalTask.person.doctor.DoctorMapper;
import me.kvq.HospitalTask.person.patient.Patient;
import me.kvq.HospitalTask.person.patient.PatientDAO;
import me.kvq.HospitalTask.person.patient.PatientDTO;
import me.kvq.HospitalTask.person.patient.PatientMapper;

@Configuration
@EnableWebMvc
public class SpringConfiguration {
  
  @Autowired
  private DoctorDAO doctorDao;
  
  @Autowired
  private PatientDAO patientDao;
  
  @Autowired
  private DoctorMapper doctorMapper;
  
  @Autowired
  private PatientMapper patientMapper;
  
  @Bean
  public HospitalService<Doctor,DoctorDTO> doctorService() {
    return new HospitalService<Doctor,DoctorDTO>(doctorDao,doctorMapper);
  }
  
  @Bean
  public HospitalService<Patient,PatientDTO> patientService(){
    return new HospitalService<Patient,PatientDTO>(patientDao,patientMapper);
  }
  
}
