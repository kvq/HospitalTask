package me.kvq.HospitalTask.controller;

import lombok.RequiredArgsConstructor;
import me.kvq.HospitalTask.dto.AppointmentDto;
import me.kvq.HospitalTask.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("appointment")
public class AppointmentController {
    private final AppointmentService service;

    @PostMapping("/add")
    public AppointmentDto create(@RequestBody AppointmentDto appointmentDto) {
        return service.add(appointmentDto);
    }

    @PostMapping("/edit")
    public AppointmentDto update(@RequestBody AppointmentDto appointmentDto) {
        return service.update(appointmentDto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @GetMapping("/doctor/{id}")
    public List<AppointmentDto> getPatientAppointments(@PathVariable long id) {
        return service.findForDoctor(id);
    }

    @GetMapping("/patient/{id}")
    public List<AppointmentDto> getDoctorAppointments(@PathVariable long id) {
        return service.findForPatient(id);
    }

}
