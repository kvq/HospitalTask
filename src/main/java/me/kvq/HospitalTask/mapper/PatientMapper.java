package me.kvq.HospitalTask.mapper;

import lombok.RequiredArgsConstructor;
import me.kvq.HospitalTask.dao.DoctorDao;
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
public class PatientMapper {
    private final DoctorDao doctorDao;

    public PatientDto entityToDto(Patient patient) {
        DoctorDto[] doctors = doctorsToDtoArray(patient.getDoctors());
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

    public Patient dtoToEntity(PatientDto patientDto) {
        List<Doctor> doctors = doctorsDtoArrayToList(patientDto.getDoctors());
        return Patient.builder()
                .id(patientDto.getId())
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

    private DoctorDto[] doctorsToDtoArray(List<Doctor> doctorList) {
        if (doctorList == null) {
            return new DoctorDto[]{};
        }
        DoctorDto[] dtoArray = new DoctorDto[doctorList.size()];
        for (int index = 0; index < doctorList.size(); index++) {
            Doctor doctor = doctorList.get(index);
            dtoArray[index] = DoctorDto.builder()
                    .id(doctor.getId())
                    .firstName(doctor.getFirstName())
                    .lastName(doctor.getLastName())
                    .patronymic(doctor.getPatronymic())
                    .birthDate(doctor.getBirthDate())
                    .phoneNumber(doctor.getPhoneNumber())
                    .position(doctor.getPosition())
                    .build();
        }
        return dtoArray;
    }

    private List<Doctor> doctorsDtoArrayToList(DoctorDto[] doctorsArray) {
        ArrayList<Doctor> list = new ArrayList<>();
        if (doctorsArray == null) {
            return list;
        }
        list.ensureCapacity(doctorsArray.length);
        for (int index = 0; index < doctorsArray.length; index++) {
            long doctorId = doctorsArray[index].getId();
            if (!doctorDao.existsById(doctorId)) {
                throw new NotFoundException("Doctor by id " + doctorId + " not found.");
            }
            Doctor doctor = Doctor.builder().id(doctorId).build();
            list.add(doctor);
        }
        return list;
    }


}
