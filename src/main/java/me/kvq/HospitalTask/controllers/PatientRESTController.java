package me.kvq.HospitalTask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import me.kvq.HospitalTask.logic.HospitalService;
import me.kvq.HospitalTask.person.doctor.DoctorDAO;
import me.kvq.HospitalTask.person.patient.Patient;
import me.kvq.HospitalTask.person.patient.PatientDTO;

@RestController()
@RequestMapping("patient")
public class PatientRESTController extends PersonRESTController<PatientDTO>{

  @Autowired
  private DoctorDAO ddao;
  
  @Autowired
  public PatientRESTController(HospitalService<Patient,PatientDTO> service) {
    super(service);
  }
  
}
