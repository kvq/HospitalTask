package me.kvq.HospitalTask.controller;

import me.kvq.HospitalTask.service.HospitalService;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.dto.DoctorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("doctor")
public class DoctorController extends PersonController<DoctorDto> {

    @Autowired
    public DoctorController(HospitalService<Doctor, DoctorDto> service) {
        super(service);
    }

}
