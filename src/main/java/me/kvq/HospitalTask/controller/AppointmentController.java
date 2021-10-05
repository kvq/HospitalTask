package me.kvq.HospitalTask.controller;

import lombok.RequiredArgsConstructor;
import me.kvq.HospitalTask.dto.AppointmentDto;
import me.kvq.HospitalTask.service.AppointmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("appointment")
public class AppointmentController {
    private final AppointmentService service;

    @PostMapping("/add")
    @PreAuthorize("(hasAuthority(\"CREATE_APPOINTMENT\")) " +
            "OR (hasAuthority(\"CREATE_OWN_APPOINTMENT\") " +
            "AND @appointmentSecurityService.canCreateAppointment(authentication.principal, #appointment.getPatient().getId()))")
    public AppointmentDto create(@RequestBody @P("appointment") AppointmentDto appointmentDto) {
        return service.add(appointmentDto);
    }

    @PatchMapping("/edit")
    @PreAuthorize("(hasAuthority(\"UPDATE_APPOINTMENT\"))" +
            "OR (hasAuthority(\"UPDATE_OWN_APPOINTMENT\") AND @appointmentSecurityService.ownsAppointment(authentication.principal, #appointment.getId()))")
    public AppointmentDto update(@RequestBody @P("appointment") AppointmentDto appointmentDto) {
        return service.update(appointmentDto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("(hasAuthority(\"DELETE_APPOINTMENT\")) " +
            "OR (hasAuthority(\"DELETE_OWN_APPOINTMENT\") AND @appointmentSecurityService.ownsAppointment(authentication.principal, #id))")
    public void delete(@PathVariable @P("id") long id) {
        service.delete(id);
    }

    @GetMapping("/doctor/{id}")
    @PreAuthorize("(hasAuthority(\"SEE_ALL_APPOINTMENTS\")) " +
            "OR (hasAuthority(\"SEE_OWN_APPOINTMENTS\") AND @securityService.ownsAccount(authentication.principal, #id))")
    public List<AppointmentDto> getPatientAppointments(@PathVariable @P("id") long id) {
        return service.findByDoctor(id);
    }

    @GetMapping("/patient/{id}")
    @PreAuthorize("(hasAuthority(\"SEE_ALL_APPOINTMENTS\")) " +
            "OR (hasAuthority(\"SEE_OWN_APPOINTMENTS\") AND @securityService.ownsAccount(authentication.principal, #id))")
    public List<AppointmentDto> getDoctorAppointments(@PathVariable @P("id") long id) {
        return service.findByPatient(id);
    }

}
