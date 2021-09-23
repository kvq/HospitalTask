package me.kvq.HospitalTask.mapper;

import lombok.AllArgsConstructor;
import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.model.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PatientMapper {
    final private DoctorDao doctorDao;

    public PatientDto entityToDto(Patient patient) {
        long[] doctors = listToIds(patient.getDoctors());
        return PatientDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .patronymic(patient.getPatronymic())
                .birthDate(patient.getBirthDate())
                .phoneNumber(patient.getPhoneNumber())
                .doctors(doctors)
                .build();
    }

    public Patient dtoToEntity(long id, PatientDto patientDto) {
        List<Doctor> doctors = idsToDoctorList(patientDto.getDoctors());
        return Patient.builder()
                .id(id)
                .firstName(patientDto.getFirstName())
                .lastName(patientDto.getLastName())
                .patronymic(patientDto.getPatronymic())
                .birthDate(patientDto.getBirthDate())
                .phoneNumber(patientDto.getPhoneNumber())
                .doctors(doctors)
                .build();
    }

    public List<PatientDto> entityListToDtoList(List<Patient> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public long[] listToIds(List<Doctor> doctorsList) {
        if (doctorsList == null) return new long[]{};
        return doctorsList.stream().mapToLong(doctor -> doctor.getId()).toArray();
    }

    private List<Doctor> idsToDoctorList(long[] doctorsArray) {
        List<Doctor> list = new ArrayList<>();
        if (doctorsArray == null) {
            return list;
        }
        for (long doctorId : doctorsArray) {
            if (!doctorDao.existsById(doctorId)) {
                throw new NotFoundException("Doctor by id " + doctorId + " not found.");
            }
            Doctor doctor = Doctor.builder().id(doctorId).build();
            list.add(doctor);
        }
        return list;
    }

}
