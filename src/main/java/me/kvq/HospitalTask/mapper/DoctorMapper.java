package me.kvq.HospitalTask.mapper;

import lombok.AllArgsConstructor;
import me.kvq.HospitalTask.dao.PatientDao;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.model.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DoctorMapper {
    private final PatientDao patientDao;

    public DoctorDto entityToDto(Doctor doctor) {
        long[] patients = listToIds(doctor.getPatients());
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

    public Doctor dtoToEntity(long id, DoctorDto doctorDto) {
        List<Patient> patients = dtoArrayToSet(doctorDto.getPatients());
        return Doctor.builder()
                .id(id)
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

    public long[] listToIds(List<Patient> patientList) {
        if (patientList == null) return new long[]{};
        return patientList.stream().mapToLong(patient -> patient.getId()).toArray();
    }

    private List<Patient> dtoArrayToSet(long[] patientsArray) {
        List<Patient> list = new ArrayList<>();
        if (patientsArray == null) {
            return list;
        }
        for (long patientId : patientsArray) {
            if (!patientDao.existsById(patientId)) {
                throw new NotFoundException("Patient by id " + patientId + " not found.");
            }
            Patient patient = Patient.builder().id(patientId).build();
            list.add(patient);
        }
        return list;
    }

}
