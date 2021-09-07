package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.mapper.PatientMapper;
import me.kvq.HospitalTask.model.Patient;
import me.kvq.HospitalTask.dao.PatientDao;
import me.kvq.HospitalTask.utils.PhoneNumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PatientService{

    private PatientDao dao;
    private PatientMapper mapper;

    public PatientService(PatientDao dao, PatientMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    public PatientDto add(PatientDto patient) {
        patient.setId(0);
        patient.setPhoneNumber(PhoneNumberUtils.fixPhoneNumber(patient.getPhoneNumber()));
        Patient patientEntity = dao.save(mapper.dtoToEntity(patient));
        return mapper.entityToDto(patientEntity);
    }

    public PatientDto update(long id, PatientDto patient) {
        if (!dao.existsById(id)) {
            throw new NoSuchElementException("User does not exists");
        }
        patient.setId(id);
        patient.setPhoneNumber(PhoneNumberUtils.fixPhoneNumber(patient.getPhoneNumber()));
        Patient patientEntity = dao.save(mapper.dtoToEntity(patient));
        return mapper.entityToDto(patientEntity);
    }

    public boolean delete(long id) {
        if (!dao.existsById(id)) {
            throw new NoSuchElementException("User does not exists");
        }
        dao.deleteById(id);
        return true;
    }

    public List<PatientDto> getList() {
        return mapper.entityListToDtoList(dao.findAll());
    }

    public PatientDto get(long id){
        Patient entity = dao.getById(id);
        if (entity == null) {
            throw new NoSuchElementException("User does not exists");
        }
        return mapper.entityToDto(entity);
    }

}
