package me.kvq.HospitalTask.service;

import lombok.RequiredArgsConstructor;
import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.exception.InvalidDtoException;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.mapper.DoctorMapper;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.utils.PhoneNumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DoctorService {
    private final DoctorDao dao;
    private final DoctorMapper doctorMapper;

    public DoctorDto add(DoctorDto doctorDto) {
        if (doctorDto.getId() != 0) {
            throw new InvalidDtoException("You should not specify id when adding new doctor");
        }
        PhoneNumberUtils.checkPhoneNumber(doctorDto.getPhoneNumber());
        Doctor doctor = doctorMapper.dtoToEntity(doctorDto);
        Doctor returnDoctor = dao.save(doctor);
        return doctorMapper.entityToDto(returnDoctor);
    }

    public DoctorDto update(DoctorDto doctorDto) {
        if (!dao.existsById(doctorDto.getId())) {
            throw new NotFoundException("No doctor found by that id");
        }
        PhoneNumberUtils.checkPhoneNumber(doctorDto.getPhoneNumber());
        Doctor doctor = doctorMapper.dtoToEntity(doctorDto);
        Doctor returnDoctor = dao.save(doctor);
        return doctorMapper.entityToDto(returnDoctor);
    }

    public void delete(long id) {
        if (!dao.existsById(id)) {
            throw new NotFoundException("No doctor found by that id");
        }
        dao.deleteById(id);
    }

    public List<DoctorDto> getList() {
        List<Doctor> doctorList = dao.findAll();
        return doctorMapper.entityListToDtoList(doctorList);
    }

    public DoctorDto get(long id) {
        Doctor entity = dao.getById(id);
        if (entity == null) {
            throw new NotFoundException("No doctor found by that id");
        }
        return doctorMapper.entityToDto(entity);
    }

}
