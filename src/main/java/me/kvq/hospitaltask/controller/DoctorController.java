package me.kvq.hospitaltask.controller;

import lombok.RequiredArgsConstructor;
import me.kvq.hospitaltask.dto.DoctorDto;
import me.kvq.hospitaltask.dto.OffWorkDto;
import me.kvq.hospitaltask.service.DoctorService;
import me.kvq.hospitaltask.service.OffWorkService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("doctor")
public class DoctorController {
    private final DoctorService service;
    private final OffWorkService offWorkService; // FIX

    @PostMapping("/add")
    @PreAuthorize("hasAuthority(\"CREATE_DOCTOR\")")
    public DoctorDto add(@RequestBody DoctorDto doctorDto) {
        return service.add(doctorDto);
    }

    @PatchMapping("/edit")
    @PreAuthorize("(hasAuthority(\"UPDATE_DOCTOR\")) " +
            "OR (hasAuthority(\"UPDATE_SELF\") AND @securityService.ownsAccount(authentication.principal,#doctor.getId()))")
    public DoctorDto update(@RequestBody @P("doctor") DoctorDto doctorDto) {
        return service.update(doctorDto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("(hasAuthority(\"DELETE_DOCTOR\")) " +
            "OR (hasAuthority(\"DELETE_SELF\") AND @securityService.ownsAccount(authentication.principal,#id))")
    public void delete(@PathVariable @P("id") long id) {
        service.delete(id);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority(\"SEE_ALL_DOCTORS\")")
    public List<DoctorDto> getList() {
        return service.getList();
    }

    @GetMapping("/unavailability/{id}")
    @PreAuthorize("hasAuthority(\"SEE_DOCTOR_UNAVAILABILITY\")")
    public List<OffWorkDto> unavailability(@PathVariable long id) { //fix
        return offWorkService.getAllActiveOffWorks(id);
    }

    @PostMapping("/updateOffWork")
    @PreAuthorize("hasAuthority(\"UPDATE_OFFWORK\")")
    public OffWorkDto addOffWork(OffWorkDto offWorkDto) {
        return offWorkService.updateOffWork(offWorkDto);
    }

}
