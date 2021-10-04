package me.kvq.HospitalTask.controller;

import lombok.RequiredArgsConstructor;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.service.DoctorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("doctor")
public class DoctorController {
    private final DoctorService service;

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

}
