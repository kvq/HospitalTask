package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.dao.AppointmentDao;
import me.kvq.HospitalTask.dto.AppointmentDto;
import me.kvq.HospitalTask.exception.InvalidDtoException;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.mapper.AppointmentMapper;
import me.kvq.HospitalTask.model.Appointment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static me.kvq.HospitalTask.testData.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class AppointmentServiceTest {
    @MockBean
    AppointmentMapper mapper;
    @MockBean
    AppointmentDao dao;
    @Autowired
    AppointmentService service;

    @Test
    @DisplayName("Add valid appointment, expected to return dto with same fields")
    void validCreateTest() {
        Appointment appointment = validAppointment();
        AppointmentDto expectedDto = validAppointmentDto();
        expectedDto.setId(0);
        when(mapper.dtoToEntity(expectedDto)).thenReturn(appointment);
        when(mapper.entityToDto(appointment)).thenReturn(expectedDto);
        when(dao.save(appointment)).thenReturn(appointment);

        AppointmentDto returnedDto = service.add(expectedDto);
        assertEquals(expectedDto.getId(), returnedDto.getId());
        assertEquals(expectedDto.getDoctor(), returnedDto.getDoctor());
        assertEquals(expectedDto.getPatient(), returnedDto.getPatient());
        assertEquals(expectedDto.getDateTime(), returnedDto.getDateTime());
        verify(mapper, times(1)).dtoToEntity(expectedDto);
        verify(mapper, times(1)).entityToDto(appointment);
        verify(dao, times(1)).save(appointment);
    }

    @Test
    @DisplayName("Update appointment with valid data, expected to return dto with same fields")
    void validUpdateTest() {
        Appointment appointment = validAppointment();
        AppointmentDto expectedDto = validAppointmentDto();
        long id = expectedDto.getId();
        when(mapper.dtoToEntity(expectedDto)).thenReturn(appointment);
        when(mapper.entityToDto(appointment)).thenReturn(expectedDto);
        when(dao.save(appointment)).thenReturn(appointment);
        when(dao.existsById(id)).thenReturn(true);

        AppointmentDto returnedDto = service.update(expectedDto);
        assertEquals(expectedDto.getId(), returnedDto.getId());
        assertEquals(expectedDto.getDoctor(), returnedDto.getDoctor());
        assertEquals(expectedDto.getPatient(), returnedDto.getPatient());
        assertEquals(expectedDto.getDateTime(), returnedDto.getDateTime());
        verify(mapper, times(1)).dtoToEntity(expectedDto);
        verify(mapper, times(1)).entityToDto(appointment);
        verify(dao, times(1)).save(appointment);
        verify(dao, times(1)).existsById(id);
    }

    @Test
    @DisplayName("Add invalid appointment, exception expected")
    void invalidCreateTest() {
        AppointmentDto dto = invalidAppointmentDto();
        assertThrows(InvalidDtoException.class, () -> {
            service.add(dto);
        });
    }

    @Test
    @DisplayName("Update invalid appointment, exception expected")
    void invalidUpdateTest() {
        AppointmentDto dto = invalidAppointmentDto();
        long id = dto.getId();
        assertThrows(NotFoundException.class, () -> {
            service.update(dto);
        });
    }

    @Test
    @DisplayName("Delete appointment by id, no exception expected")
    void validCancelTest() {
        long id = 1;
        when(dao.existsById(id)).thenReturn(true);
        service.delete(id);
        verify(dao, times(1)).existsById(id);
    }

    @Test
    @DisplayName("Delete non existing appointment by id, exception expected")
    void invalidCancelTest() {
        long id = 1;
        assertThrows(NotFoundException.class, () -> {
            service.delete(id);
        });
        verify(dao, times(1)).existsById(id);
    }

    @Test
    @DisplayName("Find all appointments for patient by id, compares returned list")
    void getAllForPatientTest() {
        List<Appointment> appointmentList = getAppointmentsList();
        List<AppointmentDto> appointmentDtoList = getAppointmentsDtoList();
        long id = 2L;
        when(dao.findAllByPatient(id)).thenReturn(appointmentList);
        when(mapper.entityListToDtoList(appointmentList)).thenReturn(appointmentDtoList);

        List<AppointmentDto> returnedDtoList = service.findForPatient(id);
        for (int index = 0; index < appointmentDtoList.size(); index++) {
            AppointmentDto expectedDto = appointmentDtoList.get(index);
            AppointmentDto returnedDto = returnedDtoList.get(index);
            assertEquals(expectedDto.getId(), returnedDto.getId());
            assertEquals(expectedDto.getDoctor(), returnedDto.getDoctor());
            assertEquals(expectedDto.getPatient(), returnedDto.getPatient());
            assertEquals(expectedDto.getDateTime(), returnedDto.getDateTime());
        }
        verify(dao, times(1)).findAllByPatient(id);
        verify(mapper, times(1)).entityListToDtoList(appointmentList);
    }

    @Test
    @DisplayName("Find all appointments for doctor by id, compares returned list")
    void getAllForDoctorTest() {
        List<Appointment> appointmentList = getAppointmentsList();
        List<AppointmentDto> appointmentDtoList = getAppointmentsDtoList();
        long id = 1L;
        when(dao.findAllByDoctor(id)).thenReturn(appointmentList);
        when(mapper.entityListToDtoList(appointmentList)).thenReturn(appointmentDtoList);

        List<AppointmentDto> returnedDtoList = service.findForDoctor(id);
        for (int index = 0; index < appointmentDtoList.size(); index++) {
            AppointmentDto expectedDto = appointmentDtoList.get(index);
            AppointmentDto returnedDto = returnedDtoList.get(index);
            assertEquals(expectedDto.getId(), returnedDto.getId());
            assertEquals(expectedDto.getDoctor(), returnedDto.getDoctor());
            assertEquals(expectedDto.getPatient(), returnedDto.getPatient());
            assertEquals(expectedDto.getDateTime(), returnedDto.getDateTime());
        }
        verify(dao, times(1)).findAllByDoctor(id);
        verify(mapper, times(1)).entityListToDtoList(appointmentList);
    }

}
