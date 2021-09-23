package me.kvq.HospitalTask.mapper;

import lombok.AllArgsConstructor;
import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dao.PatientDao;
import me.kvq.HospitalTask.dto.AppointmentDto;
import me.kvq.HospitalTask.model.Appointment;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentMapper {
    final private DoctorDao doctorDao;
    final private PatientDao patientDao;

    public AppointmentDto entityToDto(Appointment appointment) {
        return AppointmentDto.builder()
                .id(appointment.getId())
                .doctorId(appointment.getDoctor().getId())
                .patientId(appointment.getPatient().getId())
                .time(appointment.getTime())
                .build();
    }

    public Appointment dtoToEntity(long id, AppointmentDto appointmentDto) {
        Doctor doctor = doctorDao.getById(appointmentDto.getDoctorId());
        Patient patient = patientDao.getById(appointmentDto.getPatientId());
        return Appointment.builder()
                .id(id)
                .doctor(doctor)
                .patient(patient)
                .time(appointmentDto.getTime())
                .build();
    }

    public List<AppointmentDto> entityListToDtoList(List<Appointment> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());
    }

}
