package me.kvq.HospitalTask.controller;

import lombok.RequiredArgsConstructor;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
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

    @PatchMapping("/edit")
    public PatientDto update(@RequestBody PatientDto patientDto) {
        return service.update(patientDto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}
