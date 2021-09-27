package me.kvq.HospitalTask.mapper;

import lombok.RequiredArgsConstructor;
import me.kvq.HospitalTask.dao.PatientDao;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.model.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DoctorMapper {
    private final PatientDao patientDao;

    public DoctorDto entityToDto(Doctor doctor) {
        PatientDto[] patients = patientsToDtoArray(doctor.getPatients());
        return DoctorDto.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .patronymic(doctor.getPatronymic())
                .birthDate(doctor.getBirthDate())
                .phoneNumber(doctor.getPhoneNumber())
                .position(doctor.getPosition())
                .patients(patients)
                .build();
    }

    public Doctor dtoToEntity(DoctorDto doctorDto) {
        List<Patient> patients = patientsDtoArrayToList(doctorDto.getPatients());
        return Doctor.builder()
                .id(doctorDto.getId())
                .firstName(doctorDto.getFirstName())
                .lastName(doctorDto.getLastName())
                .patronymic(doctorDto.getPatronymic())
                .birthDate(doctorDto.getBirthDate())
                .phoneNumber(doctorDto.getPhoneNumber())
                .position(doctorDto.getPosition())
                .patients(patients)
                .build();
    }

    public List<DoctorDto> entityListToDtoList(List<Doctor> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    private PatientDto[] patientsToDtoArray(List<Patient> patientList) {
        if (patientList == null) {
            return new PatientDto[]{};
        }
        PatientDto[] dtoArray = new PatientDto[patientList.size()];
        for (int index = 0; index < patientList.size(); index++) {
            Patient patient = patientList.get(index);
            dtoArray[index] = PatientDto.builder()
                    .id(patient.getId())
                    .firstName(patient.getFirstName())
                    .lastName(patient.getLastName())
                    .patronymic(patient.getPatronymic())
                    .birthDate(patient.getBirthDate())
                    .phoneNumber(patient.getPhoneNumber())
                    .build();
        }
        return dtoArray;
    }

    private List<Patient> patientsDtoArrayToList(PatientDto[] patientsArray) {
        ArrayList<Patient> list = new ArrayList<>();
        if (patientsArray == null) {
            return list;
        }
        list.ensureCapacity(patientsArray.length);
        for (int index = 0; index < patientsArray.length; index++) {
            long patientId = patientsArray[index].getId();
            if (!patientDao.existsById(patientId)) {
                throw new NotFoundException("Patient by id " + patientId + " not found.");
            }
            Patient patient = Patient.builder().id(patientId).build();
            list.add(patient);
        }
        return list;
    }

}
