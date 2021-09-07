package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.mapper.PatientMapper;
import me.kvq.HospitalTask.model.Patient;
import me.kvq.HospitalTask.dao.PatientDao;
import org.springframework.stereotype.Service;

@Service
public class PatientService extends HospitalService<Patient, PatientDto> {

    public PatientService(PatientDao dao, PatientMapper mapper) {
        super(dao, mapper);
    }

}
