package me.kvq.hospitaltask.service;

import me.kvq.hospitaltask.dao.AppointmentDao;
import me.kvq.hospitaltask.dto.AppointmentDto;
import me.kvq.hospitaltask.exception.InvalidDtoException;
import me.kvq.hospitaltask.exception.IsBusyException;
import me.kvq.hospitaltask.exception.NotFoundException;
import me.kvq.hospitaltask.mapper.AppointmentMapper;
import me.kvq.hospitaltask.model.Appointment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static me.kvq.hospitaltask.testData.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class AppointmentServiceTest {
    @MockBean
    AppointmentMapper mapper;
    @MockBean
    AppointmentDao dao;
    @MockBean
    OffWorkService offWorkService;
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
        when(offWorkService.isAvailableAtDate(
                appointment.getDateTime().toLocalDate(), appointment.getDoctor().getId())).thenReturn(true);

        AppointmentDto returnedDto = service.add(expectedDto);
        assertEquals(expectedDto.getId(), returnedDto.getId());
        assertEquals(expectedDto.getDoctor(), returnedDto.getDoctor());
        assertEquals(expectedDto.getPatient(), returnedDto.getPatient());
        assertEquals(expectedDto.getDateTime(), returnedDto.getDateTime());
        verify(mapper, times(1)).dtoToEntity(expectedDto);
        verify(mapper, times(1)).entityToDto(appointment);
        verify(dao, times(1)).save(appointment);
        verify(offWorkService, times(1))
                .isAvailableAtDate(appointment.getDateTime().toLocalDate(), appointment.getDoctor().getId());
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
        when(offWorkService.isAvailableAtDate(
                appointment.getDateTime().toLocalDate(), appointment.getDoctor().getId())).thenReturn(true);

        AppointmentDto returnedDto = service.update(expectedDto);
        assertEquals(expectedDto.getId(), returnedDto.getId());
        assertEquals(expectedDto.getDoctor(), returnedDto.getDoctor());
        assertEquals(expectedDto.getPatient(), returnedDto.getPatient());
        assertEquals(expectedDto.getDateTime(), returnedDto.getDateTime());
        verify(mapper, times(1)).dtoToEntity(expectedDto);
        verify(mapper, times(1)).entityToDto(appointment);
        verify(dao, times(1)).save(appointment);
        verify(dao, times(1)).existsById(id);
        verify(offWorkService, times(1))
                .isAvailableAtDate(appointment.getDateTime().toLocalDate(), appointment.getDoctor().getId());
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
        when(dao.findAllByPatientId(id)).thenReturn(appointmentList);
        when(mapper.entityListToDtoList(appointmentList)).thenReturn(appointmentDtoList);

        List<AppointmentDto> returnedDtoList = service.findByPatient(id);
        for (int index = 0; index < appointmentDtoList.size(); index++) {
            AppointmentDto expectedDto = appointmentDtoList.get(index);
            AppointmentDto returnedDto = returnedDtoList.get(index);
            assertEquals(expectedDto.getId(), returnedDto.getId());
            assertEquals(expectedDto.getDoctor(), returnedDto.getDoctor());
            assertEquals(expectedDto.getPatient(), returnedDto.getPatient());
            assertEquals(expectedDto.getDateTime(), returnedDto.getDateTime());
        }
        verify(dao, times(1)).findAllByPatientId(id);
        verify(mapper, times(1)).entityListToDtoList(appointmentList);
    }

    @Test
    @DisplayName("Find all appointments for doctor by id, compares returned list")
    void getAllForDoctorTest() {
        List<Appointment> appointmentList = getAppointmentsList();
        List<AppointmentDto> appointmentDtoList = getAppointmentsDtoList();
        long id = 1L;
        when(dao.findAllByDoctorId(id)).thenReturn(appointmentList);
        when(mapper.entityListToDtoList(appointmentList)).thenReturn(appointmentDtoList);

        List<AppointmentDto> returnedDtoList = service.findByDoctor(id);
        for (int index = 0; index < appointmentDtoList.size(); index++) {
            AppointmentDto expectedDto = appointmentDtoList.get(index);
            AppointmentDto returnedDto = returnedDtoList.get(index);
            assertEquals(expectedDto.getId(), returnedDto.getId());
            assertEquals(expectedDto.getDoctor(), returnedDto.getDoctor());
            assertEquals(expectedDto.getPatient(), returnedDto.getPatient());
            assertEquals(expectedDto.getDateTime(), returnedDto.getDateTime());
        }
        verify(dao, times(1)).findAllByDoctorId(id);
        verify(mapper, times(1)).entityListToDtoList(appointmentList);
    }

    public static Stream<Arguments> invalidTimeList() {
        return Stream.of(
                null,
                Arguments.of(LocalDateTime.now().minusDays(1).withHour(9).withMinute(0)),
                Arguments.of(LocalDateTime.now().plusDays(1).withHour(3).withMinute(15)),
                Arguments.of(LocalDateTime.now().plusDays(1).withHour(10).withMinute(14)));
    }

    @ParameterizedTest
    @MethodSource("invalidTimeList")
    @DisplayName("Creates appointment with invalid time, expected exception")
    void createAppointmentWithInvalidTimeTest(LocalDateTime invalidTime) {
        AppointmentDto appointmentDto = validAppointmentDto();
        appointmentDto.setId(0);
        Appointment appointment = validAppointment();
        appointment.setDateTime(invalidTime);
        when(mapper.dtoToEntity(appointmentDto)).thenReturn(appointment);
        assertThrows(InvalidDtoException.class, () -> {
            service.add(appointmentDto);
        });
        verify(mapper, times(1)).dtoToEntity(appointmentDto);
    }

    @Test
    @DisplayName("Creates appointment with doctor that is unavailable for that date")
    void doctorUnavailable() {
        AppointmentDto appointmentDto = validAppointmentDto();
        appointmentDto.setId(0);
        Appointment appointment = validAppointment();
        when(mapper.dtoToEntity(appointmentDto)).thenReturn(appointment);
        assertThrows(IsBusyException.class, () -> {
            service.add(appointmentDto);
        });
        verify(mapper, times(1)).dtoToEntity(appointmentDto);
    }

    @Test
    @DisplayName("Creates appointment if patient already has different appointment for that time")
    void patientHasAnotherAppointmentTest() {
        AppointmentDto appointmentDto = validAppointmentDto();
        appointmentDto.setId(0);
        Appointment appointment = validAppointment();
        when(mapper.dtoToEntity(appointmentDto)).thenReturn(appointment);
        when(offWorkService.isAvailableAtDate(
                appointment.getDateTime().toLocalDate(), appointment.getDoctor().getId())).thenReturn(true);
        when(dao.existsByPatientIdAndDateTime(appointment.getPatient().getId(), appointment.getDateTime()))
                .thenReturn(true);
        assertThrows(IsBusyException.class, () -> {
            service.add(appointmentDto);
        });
        verify(mapper, times(1)).dtoToEntity(appointmentDto);
        verify(offWorkService, times(1))
                .isAvailableAtDate(appointment.getDateTime().toLocalDate(), appointment.getDoctor().getId());
        verify(dao, times(1))
                .existsByPatientIdAndDateTime(appointment.getPatient().getId(), appointment.getDateTime());
    }

    @Test
    @DisplayName("Creates appointment if doctor already has different appointment for that time")
    void doctorHasAnotherAppointmentTest() {
        AppointmentDto appointmentDto = validAppointmentDto();
        appointmentDto.setId(0);
        Appointment appointment = validAppointment();
        when(mapper.dtoToEntity(appointmentDto)).thenReturn(appointment);
        when(offWorkService.isAvailableAtDate(
                appointment.getDateTime().toLocalDate(), appointment.getDoctor().getId())).thenReturn(true);
        when(dao.existsByDoctorIdAndDateTime(appointment.getDoctor().getId(), appointment.getDateTime()))
                .thenReturn(true);
        assertThrows(IsBusyException.class, () -> {
            service.add(appointmentDto);
        });
        verify(mapper, times(1)).dtoToEntity(appointmentDto);
        verify(offWorkService, times(1))
                .isAvailableAtDate(appointment.getDateTime().toLocalDate(), appointment.getDoctor().getId());
        verify(dao, times(1))
                .existsByDoctorIdAndDateTime(appointment.getDoctor().getId(), appointment.getDateTime());
    }

}
