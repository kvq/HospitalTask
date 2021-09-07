package me.kvq.HospitalTask.controller;

import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("doctor")
public class DoctorController {

    private DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public List<DoctorDto> list() {
        return service.getList();
    }

    @PostMapping("/add")
    public DoctorDto add(@RequestBody DoctorDto patient) {
        return service.add(patient);
    }

    @PatchMapping("/edit/{id}")
    public DoctorDto patch(@PathVariable long id, @RequestBody DoctorDto person) {
        return service.update(id, person);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        service.delete(id);
        return "success";
    }
}
