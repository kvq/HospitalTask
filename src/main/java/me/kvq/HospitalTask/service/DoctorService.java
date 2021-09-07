package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.mapper.DoctorMapper;
import me.kvq.HospitalTask.dao.DoctorDao;
import org.springframework.stereotype.Service;

@Service
public class DoctorService extends HospitalService<Doctor, DoctorDto> {

    public DoctorService(DoctorDao dao, DoctorMapper mapper) {
        super(dao, mapper);
    }

}
