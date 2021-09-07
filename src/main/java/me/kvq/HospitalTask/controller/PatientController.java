package me.kvq.HospitalTask.controller;

import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.model.Patient;
import me.kvq.HospitalTask.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("patient")
public class PatientController {

    private PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public List<PatientDto> list() {
        return service.getList();
    }

    @PostMapping("/add")
    public PatientDto add(@RequestBody PatientDto patient) {
        return service.add(patient);
    }

    @PatchMapping("/edit/{id}")
    public PatientDto patch(@PathVariable long id, @RequestBody PatientDto person) {
        return service.update(id, person);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        service.delete(id);
        return "success";
    }
}
