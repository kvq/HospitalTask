package me.kvq.hospitaltask.service;

import lombok.RequiredArgsConstructor;
import me.kvq.hospitaltask.dao.PatientDao;
import me.kvq.hospitaltask.dto.PatientDto;
import me.kvq.hospitaltask.exception.InvalidDtoException;
import me.kvq.hospitaltask.exception.NotFoundException;
import me.kvq.hospitaltask.mapper.PatientMapper;
import me.kvq.hospitaltask.model.Patient;
import me.kvq.hospitaltask.utils.PhoneNumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientService {
    private final PatientDao dao;
    private final PatientMapper patientMapper;

    public PatientDto add(PatientDto patientDto) {
        if (patientDto.getId() != 0) {
            throw new InvalidDtoException("You should not specify id when adding new doctor");
        }
        PhoneNumberUtils.checkPhoneNumber(patientDto.getPhoneNumber());
        Patient patient = patientMapper.dtoToEntity(patientDto);
        Patient returnPatient = dao.save(patient);
        return patientMapper.entityToDto(returnPatient);
    }

    public PatientDto update(PatientDto patientDto) {
        if (!dao.existsById(patientDto.getId())) {
            throw new NotFoundException("No patient found by that id");
        }
        PhoneNumberUtils.checkPhoneNumber(patientDto.getPhoneNumber());
        Patient patient = patientMapper.dtoToEntity(patientDto);
        Patient returnPatient = dao.save(patient);
        return patientMapper.entityToDto(returnPatient);
    }

    public void delete(long id) {
        if (!dao.existsById(id)) {
            throw new NotFoundException("No patient found by that id");
        }
        dao.deleteById(id);
    }

    public List<PatientDto> getList() {
        List<Patient> patientList = dao.findAll();
        return patientMapper.entityListToDtoList(patientList);
    }

}
