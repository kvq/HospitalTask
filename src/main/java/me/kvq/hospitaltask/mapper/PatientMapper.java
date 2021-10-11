package me.kvq.hospitaltask.mapper;

import lombok.RequiredArgsConstructor;
import me.kvq.hospitaltask.dto.PatientDto;
import me.kvq.hospitaltask.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PatientMapper {

    public PatientDto entityToDto(Patient patient) {
        return PatientDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .patronymic(patient.getPatronymic())
                .birthDate(patient.getBirthDate())
                .phoneNumber(patient.getPhoneNumber())
                .build();
    }

    public Patient dtoToEntity(PatientDto patientDto) {
        return Patient.builder()
                .id(patientDto.getId())
                .firstName(patientDto.getFirstName())
                .lastName(patientDto.getLastName())
                .patronymic(patientDto.getPatronymic())
                .birthDate(patientDto.getBirthDate())
                .phoneNumber(patientDto.getPhoneNumber())
                .build();
    }

    public List<PatientDto> entityListToDtoList(List<Patient> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());
    }

}
