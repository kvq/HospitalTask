package me.kvq.HospitalTask.controller;

import lombok.RequiredArgsConstructor;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("doctor")
public class DoctorController {
    private final DoctorService service;

    @GetMapping("/list")
    public List<DoctorDto> getList() {
        return service.getList();
    }

    @PostMapping("/add")
    public DoctorDto add(@RequestBody DoctorDto doctorDto) {
        return service.add(doctorDto);
    }

    @PatchMapping("/edit")
    public DoctorDto update(@RequestBody DoctorDto doctorDto) {
        return service.update(doctorDto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}
