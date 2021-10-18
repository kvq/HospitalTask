package me.kvq.hospitaltask.service;

import lombok.RequiredArgsConstructor;
import me.kvq.hospitaltask.dao.AppointmentDao;
import me.kvq.hospitaltask.dto.AppointmentDto;
import me.kvq.hospitaltask.exception.InvalidDtoException;
import me.kvq.hospitaltask.exception.IsBusyException;
import me.kvq.hospitaltask.exception.NotFoundException;
import me.kvq.hospitaltask.mapper.AppointmentMapper;
import me.kvq.hospitaltask.model.Appointment;
import me.kvq.hospitaltask.model.Doctor;
import me.kvq.hospitaltask.model.Patient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AppointmentService {
    private final AppointmentMapper mapper;
    private final AppointmentDao dao;
    private final OffWorkService offWorkService;

    public AppointmentDto add(AppointmentDto appointmentDto) {
        if (appointmentDto.getId() != 0) {
            throw new InvalidDtoException("You should not specify id when creating new appointment");
        }
        Appointment appointment = mapper.dtoToEntity(appointmentDto);
        verify(appointment);
        Appointment returnedAppointment = dao.save(appointment);
        return mapper.entityToDto(returnedAppointment);
    }

    public AppointmentDto update(AppointmentDto appointmentDto) {
        if (!dao.existsById(appointmentDto.getId())) {
            throw new NotFoundException("No appointment found by that id");
        }
        Appointment appointment = mapper.dtoToEntity(appointmentDto);
        verify(appointment);
        Appointment returnedAppointment = dao.save(appointment);
        return mapper.entityToDto(returnedAppointment);
    }

    public void delete(long id) {
        if (!dao.existsById(id)) {
            throw new NotFoundException("No appointment found by that id");
        }
        dao.deleteById(id);
    }

    public List<AppointmentDto> findByPatient(long patientId) {
        List<Appointment> appointmentList = dao.findAllByPatientId(patientId);
        return mapper.entityListToDtoList(appointmentList);
    }

    public List<AppointmentDto> findByDoctor(long doctorId) {
        List<Appointment> appointmentList = dao.findAllByDoctorId(doctorId);
        return mapper.entityListToDtoList(appointmentList);
    }

    private void verify(Appointment appointment) {
        Doctor doctor = appointment.getDoctor();
        Patient patient = appointment.getPatient();
        LocalDateTime dateTime = appointment.getDateTime();
        if (doctor == null) {
            throw new NotFoundException("No doctor found");
        }
        if (patient == null) {
            throw new NotFoundException("No patient found");
        }
        validateTime(dateTime);
        if (!offWorkService.isAvailableAtDate(appointment.getDateTime().toLocalDate(), appointment.getDoctor().getId())) {
            throw new IsBusyException("Doctor is not available at this date");
        }
        if (patientHasAppointment(patient.getId(), dateTime)) {
            throw new IsBusyException("Patient has another appointment for that time");
        }
        if (doctorHasAppointment(doctor.getId(), dateTime)) {
            throw new IsBusyException("Doctor is not available at this time");
        }
    }

    private boolean doctorHasAppointment(long doctorId, LocalDateTime dateTime) {
        return dao.existsByDoctorIdAndDateTime(doctorId, dateTime);
    }

    private boolean patientHasAppointment(long patientId, LocalDateTime dateTime) {
        return dao.existsByPatientIdAndDateTime(patientId, dateTime);
    }

    private void validateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new InvalidDtoException("Time not entered");
        }
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new InvalidDtoException("Appointment time cannot be in the past");
        }
        int hour = dateTime.getHour();
        if (hour < 7 || hour > 19) {
            throw new InvalidDtoException("Hospital is not opened at this time");
        }
        int minute = dateTime.getMinute();
        if (minute % 15 != 0) {
            throw new InvalidDtoException("Your time should be multiple of 15");
        }
    }

}
