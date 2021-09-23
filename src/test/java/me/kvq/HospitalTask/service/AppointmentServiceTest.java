package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.dao.AppointmentDao;
import me.kvq.HospitalTask.dto.AppointmentDto;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.mapper.AppointmentMapper;
import me.kvq.HospitalTask.model.Appointment;
import me.kvq.HospitalTask.testData.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

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
    @DisplayName("(add) Pass Valid Dto, then compares returned Dto to passed")
    void validCreateTest() {
        TestDataGenerator.TestData<Appointment, AppointmentDto> testData = TestDataGenerator.getValidAppointmentData();
        Appointment appointment = testData.getEntity();
        AppointmentDto expectedDto = testData.getDto();
        when(mapper.dtoToEntity(0, expectedDto)).thenReturn(appointment);
        when(mapper.entityToDto(appointment)).thenReturn(expectedDto);
        when(dao.save(appointment)).thenReturn(appointment);

        AppointmentDto returnedDto = service.add(expectedDto);
        assertEquals(expectedDto.getId(), returnedDto.getId());
        assertEquals(expectedDto.getDoctorId(), returnedDto.getDoctorId());
        assertEquals(expectedDto.getPatientId(), returnedDto.getPatientId());
        assertEquals(expectedDto.getTime(), returnedDto.getTime());
        verify(mapper, times(1)).dtoToEntity(0, expectedDto);
        verify(mapper, times(1)).entityToDto(appointment);
        verify(dao, times(1)).save(appointment);
    }

    @Test
    @DisplayName("(update) Pass existing Id & Valid Dto, then compares return Dto to passed")
    void validUpdateTest() {
        TestDataGenerator.TestData<Appointment, AppointmentDto> testData = TestDataGenerator.getValidAppointmentData();
        Appointment appointment = testData.getEntity();
        AppointmentDto expectedDto = testData.getDto();
        long id = testData.getId();
        when(mapper.dtoToEntity(id, expectedDto)).thenReturn(appointment);
        when(mapper.entityToDto(appointment)).thenReturn(expectedDto);
        when(dao.save(appointment)).thenReturn(appointment);
        when(dao.existsById(id)).thenReturn(true);

        AppointmentDto returnedDto = service.update(id, expectedDto);
        assertEquals(expectedDto.getId(), returnedDto.getId());
        assertEquals(expectedDto.getDoctorId(), returnedDto.getDoctorId());
        assertEquals(expectedDto.getPatientId(), returnedDto.getPatientId());
        assertEquals(expectedDto.getTime(), returnedDto.getTime());
        verify(mapper, times(1)).dtoToEntity(id, expectedDto);
        verify(mapper, times(1)).entityToDto(appointment);
        verify(dao, times(1)).save(appointment);
        verify(dao, times(1)).existsById(id);
    }

    @Test
    @DisplayName("(add) Pass Invalid Dto, exception expected")
    void invalidCreateTest() {
        TestDataGenerator.TestData<Appointment, AppointmentDto> testData = TestDataGenerator.getInvalidAppointmentData();
        AppointmentDto dto = testData.getDto();
        Appointment entity = testData.getEntity();
        when(mapper.dtoToEntity(0, dto)).thenReturn(entity);
        assertThrows(NotFoundException.class, () -> {
            service.add(dto);
        });
        verify(mapper, times(1)).dtoToEntity(0, dto);
    }

    @Test
    @DisplayName("(update) Pass Id & Invalid Dto, exception expected")
    void invalidUpdateTest() {
        TestDataGenerator.TestData<Appointment, AppointmentDto> testData = TestDataGenerator.getInvalidAppointmentData();
        AppointmentDto dto = testData.getDto();
        long id = testData.getId();
        assertThrows(NotFoundException.class, () -> {
            service.update(id, dto);
        });
    }

    @Test
    @DisplayName("(delete) Pass existing Id, no exception shall be thrown")
    void validCancelTest() {
        long id = 1;
        when(dao.existsById(id)).thenReturn(true);
        service.delete(id);
        verify(dao, times(1)).existsById(id);
    }

    @Test
    @DisplayName("(delete) Pass non existing id, exception expected")
    void invalidCancelTest() {
        long id = 1;
        assertThrows(NotFoundException.class, () -> {
            service.delete(id);
        });
        verify(dao, times(1)).existsById(id);
    }

    @Test
    @DisplayName("(getAllForPatient) Pass valid id, expects valid list of appointments")
    void getAllForPatientTest() {
        List<Appointment> appointmentList = TestDataGenerator.getAppointmentsList();
        List<AppointmentDto> appointmentDtoList = TestDataGenerator.getAppointmentsDtoList();
        long id = 2L;
        when(dao.findAllByPatient_id(id)).thenReturn(appointmentList);
        when(mapper.entityListToDtoList(appointmentList)).thenReturn(appointmentDtoList);

        List<AppointmentDto> returnedDtoList = service.getAllForPatient(id);
        for (int index = 0; index < appointmentDtoList.size(); index++) {
            AppointmentDto expectedDto = appointmentDtoList.get(index);
            AppointmentDto returnedDto = returnedDtoList.get(index);
            assertEquals(expectedDto.getId(), returnedDto.getId());
            assertEquals(expectedDto.getDoctorId(), returnedDto.getDoctorId());
            assertEquals(expectedDto.getPatientId(), returnedDto.getPatientId());
            assertEquals(expectedDto.getTime(), returnedDto.getTime());
        }
        verify(dao, times(1)).findAllByPatient_id(id);
        verify(mapper, times(1)).entityListToDtoList(appointmentList);
    }

    @Test
    @DisplayName("(getAllForDoctor) Pass valid id, expects valid list of appointments")
    void getAllForDoctorTest() {
        List<Appointment> appointmentList = TestDataGenerator.getAppointmentsList();
        List<AppointmentDto> appointmentDtoList = TestDataGenerator.getAppointmentsDtoList();
        long id = 1L;
        when(dao.findAllByDoctor_id(id)).thenReturn(appointmentList);
        when(mapper.entityListToDtoList(appointmentList)).thenReturn(appointmentDtoList);

        List<AppointmentDto> returnedDtoList = service.getAllForDoctor(id);
        for (int index = 0; index < appointmentDtoList.size(); index++) {
            AppointmentDto expectedDto = appointmentDtoList.get(index);
            AppointmentDto returnedDto = returnedDtoList.get(index);
            assertEquals(expectedDto.getId(), returnedDto.getId());
            assertEquals(expectedDto.getDoctorId(), returnedDto.getDoctorId());
            assertEquals(expectedDto.getPatientId(), returnedDto.getPatientId());
            assertEquals(expectedDto.getTime(), returnedDto.getTime());
        }
        verify(dao, times(1)).findAllByDoctor_id(id);
        verify(mapper, times(1)).entityListToDtoList(appointmentList);
    }

}
