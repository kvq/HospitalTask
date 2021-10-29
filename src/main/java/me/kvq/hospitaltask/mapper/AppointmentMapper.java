package me.kvq.hospitaltask.mapper;

import lombok.RequiredArgsConstructor;
import me.kvq.hospitaltask.dto.AppointmentDto;
import me.kvq.hospitaltask.dto.DoctorDto;
import me.kvq.hospitaltask.dto.PatientDto;
import me.kvq.hospitaltask.model.Appointment;
import me.kvq.hospitaltask.model.Doctor;
import me.kvq.hospitaltask.model.Patient;
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
