package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.mapper.DoctorMapper;
import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.utils.PhoneNumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DoctorService {

    private DoctorDao dao;
    private DoctorMapper mapper;

    public DoctorService(DoctorDao dao, DoctorMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    public DoctorDto add(DoctorDto doctor) {
        doctor.setId(0);
        doctor.setPhoneNumber(PhoneNumberUtils.fixPhoneNumber(doctor.getPhoneNumber()));
        Doctor doctorEntity = dao.save(mapper.dtoToEntity(doctor));
        return mapper.entityToDto(doctorEntity);
    }

    public DoctorDto update(long id, DoctorDto doctor) {
        if (!dao.existsById(id)) {
            throw new NoSuchElementException("User does not exists");
        }
        doctor.setId(id);
        doctor.setPhoneNumber(PhoneNumberUtils.fixPhoneNumber(doctor.getPhoneNumber()));
        Doctor doctorEntity = dao.save(mapper.dtoToEntity(doctor));
        return mapper.entityToDto(doctorEntity);
    }

    public boolean delete(long id) {
        if (!dao.existsById(id)) {
            throw new NoSuchElementException("User does not exists");
        }
        dao.deleteById(id);
        return true;
    }

    public List<DoctorDto> getList() {
        return mapper.entityListToDtoList(dao.findAll());
    }

    public DoctorDto get(long id){
        Doctor entity = dao.getById(id);
        if (entity == null) {
            throw new NoSuchElementException("User does not exists");
        }
        return mapper.entityToDto(entity);
    }

}
