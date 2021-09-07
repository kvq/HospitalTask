package me.kvq.HospitalTask.controller;

import me.kvq.HospitalTask.service.HospitalService;
import me.kvq.HospitalTask.model.Patient;
import me.kvq.HospitalTask.dto.PatientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("patient")
public class PatientController extends PersonController<PatientDto> {

    @Autowired
    public PatientController(HospitalService<Patient, PatientDto> service) {
        super(service);
    }

}
