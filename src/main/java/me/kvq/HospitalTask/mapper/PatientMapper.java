package me.kvq.HospitalTask.mapper;

import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientMapper {

    private DoctorDao dao;

    public PatientMapper(DoctorDao dao){
        this.dao = dao;
    }

    public PatientDto entityToDto(Patient p) {
        long doctorId = p.getDoctor() == null ? 0 : p.getDoctor().getId();
        return new PatientDto(p.getId(), p.getFirstName(),
                p.getLastName(),
                p.getPatronymic(),
                p.getBirthDate(),
                p.getPhoneNumber(),
                doctorId);
    }

    public Patient dtoToEntity(PatientDto d) {
        Doctor doctor = dao.getById(d.getDoctor());
        return new Patient(d.getId(),
                d.getFirstName(),
                d.getLastName(),
                d.getPatronymic(),
                d.getBirthDate(),
                d.getPhoneNumber(),
                doctor);
    }

    public List<PatientDto> entityListToDtoList(List<Patient> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<Patient> dtoListToEntityList(List<PatientDto> list) {
        return list.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
