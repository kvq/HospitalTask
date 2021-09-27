package me.kvq.HospitalTask.mapper;

import lombok.RequiredArgsConstructor;
import me.kvq.HospitalTask.dto.AppointmentDto;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.model.Appointment;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AppointmentMapper {
    private final DoctorMapper doctorMapper;
    private final PatientMapper patientMapper;

    public AppointmentDto entityToDto(Appointment appointment) {
        DoctorDto doctorDto = doctorMapper.entityToDto(appointment.getDoctor());
        PatientDto patientDto = patientMapper.entityToDto(appointment.getPatient());
        return AppointmentDto.builder()
                .id(appointment.getId())
                .doctor(doctorDto)
                .patient(patientDto)
                .dateTime(appointment.getDateTime())
                .build();
    }

    public Appointment dtoToEntity(AppointmentDto appointmentDto) {
        Doctor doctor = doctorMapper.dtoToEntity(appointmentDto.getDoctor());
        Patient patient = patientMapper.dtoToEntity(appointmentDto.getPatient());
        return Appointment.builder()
                .id(appointmentDto.getId())
                .doctor(doctor)
                .patient(patient)
                .dateTime(appointmentDto.getDateTime())
                .build();
    }

    public List<AppointmentDto> entityListToDtoList(List<Appointment> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());
    }

}
