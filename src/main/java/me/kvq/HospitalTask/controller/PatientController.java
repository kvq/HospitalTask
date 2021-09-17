package me.kvq.HospitalTask.controller;

import lombok.AllArgsConstructor;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController()
@RequestMapping("patient")
public class PatientController {
    private final PatientService service;

    @GetMapping("/list")
    public List<PatientDto> getList() {
        return service.getList();
    }

    @PostMapping("/add")
    public PatientDto add(@RequestBody PatientDto patientDto) {
        return service.add(patientDto);
    }

    @PatchMapping("/edit/{id}")
    public PatientDto update(@PathVariable long id, @RequestBody PatientDto patientDto) {
        return service.update(id, patientDto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        service.delete(id);
        return "success";
    }

}
