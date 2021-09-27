package me.kvq.HospitalTask.service;

import lombok.RequiredArgsConstructor;
import me.kvq.HospitalTask.dao.AppointmentDao;
import me.kvq.HospitalTask.dto.AppointmentDto;
import me.kvq.HospitalTask.exception.InvalidDtoException;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.mapper.AppointmentMapper;
import me.kvq.HospitalTask.model.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AppointmentService {
    private final AppointmentMapper mapper;
    private final AppointmentDao dao;

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

    public List<AppointmentDto> findForPatient(long patientId) {
        List<Appointment> appointmentList = dao.findAllByPatient(patientId);
        return mapper.entityListToDtoList(appointmentList);
    }

    public List<AppointmentDto> findForDoctor(long doctorId) {
        List<Appointment> appointmentList = dao.findAllByDoctor(doctorId);
        return mapper.entityListToDtoList(appointmentList);
    }

    private void verify(Appointment appointment) {
        if (appointment.getDoctor() == null) {
            throw new NotFoundException("No doctor found");
        }
        if (appointment.getPatient() == null) {
            throw new NotFoundException("No patient found");
        }
        if (appointment.getDateTime() == null) {
            throw new NotFoundException("Time not entered");
        }
    }

}
