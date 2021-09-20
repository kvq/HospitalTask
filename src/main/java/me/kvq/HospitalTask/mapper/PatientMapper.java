package me.kvq.HospitalTask.mapper;

import lombok.AllArgsConstructor;
import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PatientMapper {
    final private DoctorDao dao;

    public PatientDto entityToDto(Patient patient) {
        long doctorId = patient.getDoctor() == null ? 0 : patient.getDoctor().getId();
        return PatientDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .patronymic(patient.getPatronymic())
                .birthDate(patient.getBirthDate())
                .phoneNumber(patient.getPhoneNumber())
                .doctor(doctorId)
                .build();
    }

    public Patient dtoToEntity(long id, PatientDto patientDto) {
        Doctor doctor = dao.getById(patientDto.getDoctor());
        return Patient.builder()
                .id(id)
                .firstName(patientDto.getFirstName())
                .lastName(patientDto.getLastName())
                .patronymic(patientDto.getPatronymic())
                .birthDate(patientDto.getBirthDate())
                .phoneNumber(patientDto.getPhoneNumber())
                .doctor(doctor)
                .build();
    }

    public List<PatientDto> entityListToDtoList(List<Patient> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());
    }

}
