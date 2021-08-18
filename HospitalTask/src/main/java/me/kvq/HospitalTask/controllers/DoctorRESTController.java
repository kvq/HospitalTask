package me.kvq.HospitalTask.controllers;

import java.time.LocalDate;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import me.kvq.HospitalTask.logic.HospitalService;
import me.kvq.HospitalTask.person.doctor.Doctor;
import me.kvq.HospitalTask.person.doctor.DoctorDTO;
import me.kvq.HospitalTask.person.patient.PatientDTO;

@RestController
@RequestMapping("doctor")
public class DoctorRESTController extends PersonRESTController<DoctorDTO>{

  @Autowired
  protected DoctorRESTController(HospitalService<Doctor,DoctorDTO> service) {
    super(service);
  }
  
}
