package me.kvq.HospitalTask.service;

import lombok.AllArgsConstructor;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.mapper.PatientMapper;
import me.kvq.HospitalTask.model.Patient;
import me.kvq.HospitalTask.dao.PatientDao;
import me.kvq.HospitalTask.utils.PhoneNumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientService{
    final private PatientDao dao;
    final private PatientMapper mapper;

    public PatientDto add(PatientDto patientDto) {
        String fixedPhoneNumber = PhoneNumberUtils.fixPhoneNumber(patientDto.getPhoneNumber());
        patientDto.setPhoneNumber(fixedPhoneNumber);
        Patient patient = mapper.dtoToEntity(0,patientDto);
        Patient returnPatient = dao.save(patient);
        return mapper.entityToDto(returnPatient);
    }

    public PatientDto update(long id, PatientDto patientDto) {
        if (!dao.existsById(id)) {
            throw new NotFoundException("No patient found by that Id");
        }
        String fixedPhoneNumber = PhoneNumberUtils.fixPhoneNumber(patientDto.getPhoneNumber());
        patientDto.setPhoneNumber(fixedPhoneNumber);
        Patient patient = mapper.dtoToEntity(id,patientDto);
        Patient returnPatient = dao.save(patient);
        return mapper.entityToDto(returnPatient);
    }

    public boolean delete(long id) {
        if (!dao.existsById(id)) {
            throw new NotFoundException("No patient found by that Id");
        }
        dao.deleteById(id);
        return true;
    }

    public List<PatientDto> getList() {
        List<Patient> patientList = dao.findAll();
        List<PatientDto> patientDtoList = mapper.entityListToDtoList(patientList);
        return patientDtoList;
    }

    public PatientDto get(long id){
        Patient entity = dao.getById(id);
        if (entity == null) {
            throw new NotFoundException("No patient found by that Id");
        }
        return mapper.entityToDto(entity);
    }

}
