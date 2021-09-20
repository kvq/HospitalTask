package me.kvq.HospitalTask.service;

import lombok.AllArgsConstructor;
import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.mapper.DoctorMapper;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.utils.PhoneNumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorService {
    final private DoctorDao dao;
    final private DoctorMapper mapper;

    public DoctorDto add(DoctorDto doctorDto) {
        PhoneNumberUtils.checkPhoneNumber(doctorDto.getPhoneNumber());
        Doctor doctor = mapper.dtoToEntity(0, doctorDto);
        Doctor returnDoctor = dao.save(doctor);
        return mapper.entityToDto(returnDoctor);
    }

    public DoctorDto update(long id, DoctorDto doctorDto) {
        if (!dao.existsById(id)) {
            throw new NotFoundException("No doctor found by that id");
        }
        PhoneNumberUtils.checkPhoneNumber(doctorDto.getPhoneNumber());
        Doctor doctor = mapper.dtoToEntity(id, doctorDto);
        Doctor returnDoctor = dao.save(doctor);
        return mapper.entityToDto(returnDoctor);
    }

    public boolean delete(long id) {
        if (!dao.existsById(id)) {
            throw new NotFoundException("No doctor found by that id");
        }
        dao.deleteById(id);
        return true;
    }

    public List<DoctorDto> getList() {
        List<Doctor> doctorList = dao.findAll();
        return mapper.entityListToDtoList(doctorList);
    }

    public DoctorDto get(long id) {
        Doctor entity = dao.getById(id);
        if (entity == null) {
            throw new NotFoundException("No doctor found by that id");
        }
        return mapper.entityToDto(entity);
    }

}
