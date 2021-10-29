package me.kvq.hospitaltask.service;

import lombok.RequiredArgsConstructor;
import me.kvq.hospitaltask.dao.DoctorDao;
import me.kvq.hospitaltask.dto.DoctorDto;
import me.kvq.hospitaltask.exception.InvalidDtoException;
import me.kvq.hospitaltask.exception.NotFoundException;
import me.kvq.hospitaltask.mapper.DoctorMapper;
import me.kvq.hospitaltask.model.Doctor;
import me.kvq.hospitaltask.utils.PhoneNumberUtils;
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

}
