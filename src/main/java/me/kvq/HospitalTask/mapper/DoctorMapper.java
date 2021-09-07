package me.kvq.HospitalTask.mapper;

import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.dto.DoctorDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorMapper{

    public DoctorDto entityToDto(Doctor doctor) {
        return new DoctorDto(doctor.getId(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getPatronymic(),
                doctor.getBirthDate(),
                doctor.getPhoneNumber(),
                doctor.getPosition());
    }

    public Doctor dtoToEntity(DoctorDto doctorDto) {
        return new Doctor(doctorDto.getId(),
                doctorDto.getFirstName(),
                doctorDto.getLastName(),
                doctorDto.getPatronymic(),
                doctorDto.getBirthDate(),
                doctorDto.getPhoneNumber(),
                doctorDto.getPosition());
    }

    public List<DoctorDto> entityListToDtoList(List<Doctor> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<Doctor> dtoListToEntityList(List<DoctorDto> list) {
        return list.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
