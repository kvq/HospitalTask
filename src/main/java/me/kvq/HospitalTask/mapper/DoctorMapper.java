package me.kvq.HospitalTask.mapper;

import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.dto.DoctorDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorMapper{

    public DoctorDto entityToDto(Doctor p) {
        return new DoctorDto(p.getId(),
                p.getFirstName(),
                p.getLastName(),
                p.getPatronymic(),
                p.getBirthDate(),
                p.getPhoneNumber(),
                p.getPosition());
    }

    public Doctor dtoToEntity(DoctorDto d) {
        return new Doctor(d.getId(),
                d.getFirstName(),
                d.getLastName(),
                d.getPatronymic(),
                d.getBirthDate(),
                d.getPhoneNumber(),
                d.getPosition());
    }

    public List<DoctorDto> entityListToDtoList(List<Doctor> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<Doctor> dtoListToEntityList(List<DoctorDto> list) {
        return list.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
