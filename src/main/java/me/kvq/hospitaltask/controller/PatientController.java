package me.kvq.hospitaltask.controller;

import lombok.RequiredArgsConstructor;
import me.kvq.hospitaltask.dto.PatientDto;
import me.kvq.hospitaltask.service.PatientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("patient")
public class PatientController {
    private final PatientService service;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority(\"CREATE_PATIENT\")")
    public PatientDto add(@RequestBody PatientDto patientDto) {
        return service.add(patientDto);
    }

    @PatchMapping("/edit")
    @PreAuthorize("(hasAuthority(\"UPDATE_PATIENT\")) " +
            "OR (hasAuthority(\"UPDATE_SELF\") AND @securityService.ownsAccount(authentication.principal, #patient.getId()))")
    public PatientDto update(@RequestBody @P("patient") PatientDto patientDto) {
        return service.update(patientDto);
    }

    @PreAuthorize("(hasAuthority(\"DELETE_PATIENT\")) " +
            "OR (hasAuthority(\"DELETE_SELF\") AND @securityService.ownsAccount(authentication.principal, #id))")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable @P("id") long id) {
        service.delete(id);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority(\"SEE_ALL_PATIENTS\")")
    public List<PatientDto> getList() {
        return service.getList();
    }

}
