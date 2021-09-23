package me.kvq.HospitalTask.controller;

import lombok.AllArgsConstructor;
import me.kvq.HospitalTask.dto.AppointmentDto;
import me.kvq.HospitalTask.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("appointment")
public class AppointmentController {
    private final AppointmentService service;

    @PostMapping("/add")
    public AppointmentDto create(@RequestBody AppointmentDto appointmentDto) {
        return service.add(appointmentDto);
    }

    @PostMapping("/edit/{id}")
    public AppointmentDto update(@RequestBody AppointmentDto appointmentDto, @PathVariable long id) {
        return service.update(id, appointmentDto);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        service.delete(id);
        return "success";
    }

    @GetMapping("/doctor/{id}")
    public List<AppointmentDto> getPatientAppointments(@PathVariable long id) {
        return service.getAllForDoctor(id);
    }

    @GetMapping("/patient/{id}")
    public List<AppointmentDto> getDoctorAppointments(@PathVariable long id) {
        return service.getAllForPatient(id);
    }

}
