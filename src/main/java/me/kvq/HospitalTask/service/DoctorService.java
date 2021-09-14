package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.exception.UserNotFoundException;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.mapper.DoctorMapper;
import me.kvq.HospitalTask.dao.DoctorDao;

import me.kvq.HospitalTask.utils.PhoneNumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private DoctorDao dao;
    private DoctorMapper mapper;

    public DoctorService(DoctorDao dao, DoctorMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    public DoctorDto add(DoctorDto doctorDto) {
        doctorDto.setPhoneNumber(PhoneNumberUtils.fixPhoneNumber(doctorDto.getPhoneNumber()));
        Doctor doctor = dao.save(mapper.dtoToEntity(0,doctorDto));
        return mapper.entityToDto(doctor);
    }

    public DoctorDto update(long id, DoctorDto doctorDto) {
        if (!dao.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        doctorDto.setPhoneNumber(PhoneNumberUtils.fixPhoneNumber(doctorDto.getPhoneNumber()));
        Doctor doctorEntity = dao.save(mapper.dtoToEntity(id,doctorDto));
        return mapper.entityToDto(doctorEntity);
    }

    public boolean delete(long id) {
        if (!dao.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        dao.deleteById(id);
        return true;
    }

    public List<DoctorDto> getList() {
        List<Doctor> doctorList = dao.findAll();
        return mapper.entityListToDtoList(doctorList);
    }

    public DoctorDto get(long id){
        Doctor entity = dao.getById(id);
        if (entity == null) {
            throw new UserNotFoundException(id);
        }
        return mapper.entityToDto(entity);
    }

}
