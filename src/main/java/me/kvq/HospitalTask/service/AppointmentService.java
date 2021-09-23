package me.kvq.HospitalTask.service;

import lombok.AllArgsConstructor;
import me.kvq.HospitalTask.dao.AppointmentDao;
import me.kvq.HospitalTask.dto.AppointmentDto;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.mapper.AppointmentMapper;
import me.kvq.HospitalTask.model.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AppointmentService {
    final private AppointmentMapper mapper;
    final private AppointmentDao dao;

    public AppointmentDto add(AppointmentDto appointmentDto) {
        Appointment appointment = mapper.dtoToEntity(0, appointmentDto);
        verify(appointment);
        Appointment returnedAppointment = dao.save(appointment);
        return mapper.entityToDto(returnedAppointment);
    }

    public AppointmentDto update(long id, AppointmentDto appointmentDto) {
        if (!dao.existsById(id)) {
            throw new NotFoundException("No appointment found by that id");
        }
        Appointment appointment = mapper.dtoToEntity(id, appointmentDto);
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

    public List<AppointmentDto> getAllForPatient(long patientId) {
        List<Appointment> appointmentList = dao.findAllByPatient_id(patientId);
        return mapper.entityListToDtoList(appointmentList);
    }

    public List<AppointmentDto> getAllForDoctor(long doctorId) {
        List<Appointment> appointmentList = dao.findAllByDoctor_id(doctorId);
        return mapper.entityListToDtoList(appointmentList);
    }

    private void verify(Appointment appointment) {
        if (appointment.getDoctor() == null) {
            throw new NotFoundException("No doctor found");
        }
        if (appointment.getPatient() == null) {
            throw new NotFoundException("No patient found");
        }
        if (appointment.getTime() == null) {
            throw new NotFoundException("Time not entered");
        }
    }

}
