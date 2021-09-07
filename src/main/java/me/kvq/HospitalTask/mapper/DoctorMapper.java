package me.kvq.HospitalTask.mapper;

import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.dto.DoctorDto;
import org.springframework.stereotype.Service;

@Service
public class DoctorMapper extends PersonMapper<Doctor, DoctorDto> {

    @Override
    public DoctorDto entityToDto(Doctor p) {
        return new DoctorDto(p.getId(),
                p.getFirstName(),
                p.getLastName(),
                p.getPatronymic(),
                p.getBirthDate(),
                p.getPhoneNumber(),
                p.getPosition());
    }

    @Override
    public Doctor dtoToEntity(DoctorDto d) {
        return new Doctor(d.getId(),
                d.getFirstName(),
                d.getLastName(),
                d.getPatronymic(),
                d.getBirthDate(),
                d.getPhoneNumber(),
                d.getPosition());
    }

}
