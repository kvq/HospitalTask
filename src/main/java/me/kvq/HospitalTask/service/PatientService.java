package me.kvq.HospitalTask.service;

import lombok.RequiredArgsConstructor;
import me.kvq.HospitalTask.dao.PatientDao;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.exception.InvalidDtoException;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.mapper.PatientMapper;
import me.kvq.HospitalTask.model.Patient;
import me.kvq.HospitalTask.utils.PhoneNumberUtils;
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
        List<PatientDto> patientDtoList = patientMapper.entityListToDtoList(patientList);
        return patientDtoList;
    }

}
