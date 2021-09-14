package me.kvq.HospitalTask.mapper;

import lombok.AllArgsConstructor;
import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientMapper {
    final private DoctorDao dao;

    public PatientDto entityToDto(Patient patient) {
        long doctorId = patient.getDoctor() == null ? 0 : patient.getDoctor().getId();
        return new PatientDto(patient.getId(), patient.getFirstName(),
                patient.getLastName(),
                patient.getPatronymic(),
                patient.getBirthDate(),
                patient.getPhoneNumber(),
                doctorId);
    }

    public Patient dtoToEntity(long id,PatientDto patientDto) {
        Doctor doctor = dao.getById(patientDto.getDoctor());
        return new Patient(id,
                patientDto.getFirstName(),
                patientDto.getLastName(),
                patientDto.getPatronymic(),
                patientDto.getBirthDate(),
                patientDto.getPhoneNumber(),
                doctor);
    }

    public List<PatientDto> entityListToDtoList(List<Patient> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());
    }

}
