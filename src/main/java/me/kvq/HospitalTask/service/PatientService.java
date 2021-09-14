package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.exception.UserNotFoundException;
import me.kvq.HospitalTask.mapper.PatientMapper;
import me.kvq.HospitalTask.model.Patient;
import me.kvq.HospitalTask.dao.PatientDao;
import me.kvq.HospitalTask.utils.PhoneNumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService{
    private PatientDao dao;
    private PatientMapper mapper;

    public PatientService(PatientDao dao, PatientMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    public PatientDto add(PatientDto patientDto) {
        patientDto.setPhoneNumber(PhoneNumberUtils.fixPhoneNumber(patientDto.getPhoneNumber()));
        Patient patientEntity = dao.save(mapper.dtoToEntity(0,patientDto));
        return mapper.entityToDto(patientEntity);
    }

    public PatientDto update(long id, PatientDto patientDto) {
        if (!dao.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        patientDto.setPhoneNumber(PhoneNumberUtils.fixPhoneNumber(patientDto.getPhoneNumber()));
        Patient returnedPatient = dao.save(mapper.dtoToEntity(id,patientDto));
        return mapper.entityToDto(returnedPatient);
    }

    public boolean delete(long id) {
        if (!dao.existsById(id)) {
            throw new UserNotFoundException(id);
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
            throw new UserNotFoundException(id);
        }
        return mapper.entityToDto(entity);
    }

}
