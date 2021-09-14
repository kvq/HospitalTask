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
    public List<DoctorDto> getList() {
        return service.getList();
    }

    @PostMapping("/add")
    public DoctorDto add(@RequestBody DoctorDto doctorDto) {
        return service.add(doctorDto);
    }

    @PatchMapping("/edit/{id}")
    public DoctorDto update(@PathVariable long id, @RequestBody DoctorDto doctorDto) {
        return service.update(id, doctorDto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        service.delete(id);
        return "success";
    }

}
