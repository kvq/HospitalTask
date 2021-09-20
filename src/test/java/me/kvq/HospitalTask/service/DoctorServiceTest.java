package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.model.Doctor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class DoctorServiceTest {
    @MockBean
    DoctorDao doctorDao;
    @Autowired
    DoctorService service;

    @Test
    @DisplayName("(add) Pass Valid Dto, then compares return Dto to passed, and checks id not same")
    void addNewValidDoctorTest() {
        DoctorDto testDoctorDto = new DoctorDto(0,
                "Name", "LastName", "Patronymic",
                LocalDate.of(1999, 10, 2),
                "380123456789", "Position");
        Doctor expectedDoctor = new Doctor(1,
                "Name", "LastName", "Patronymic",
                LocalDate.of(1999, 10, 2),
                "380123456789", "Position");

        when(doctorDao.save(any(Doctor.class))).thenReturn(expectedDoctor);
        DoctorDto returnedDoctorDto = service.add(testDoctorDto);
        assertNotNull(returnedDoctorDto, "Dto returned by service is null");
        assertEquals(expectedDoctor.getId(), returnedDoctorDto.getId());
        assertEquals(expectedDoctor.getFirstName(), returnedDoctorDto.getFirstName());
        assertEquals(expectedDoctor.getLastName(), returnedDoctorDto.getLastName());
        assertEquals(expectedDoctor.getPatronymic(), returnedDoctorDto.getPatronymic());
        assertEquals(expectedDoctor.getBirthDate(), returnedDoctorDto.getBirthDate());
        assertEquals(expectedDoctor.getPhoneNumber(), returnedDoctorDto.getPhoneNumber());
        assertEquals(expectedDoctor.getPosition(), returnedDoctorDto.getPosition());
        verify(doctorDao, times(1)).save(any(Doctor.class));
    }

    @Test
    @DisplayName("(update) Pass existing Id & Valid Dto, then compares return Dto to passed")
    void updateExistingDoctorByIdWithValidDataTest() {
        DoctorDto expectedDoctorDto = new DoctorDto(1,
                "DoctorA_Name", "DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991, 5, 4),
                "380123455789", "DoctorA_Position");
        Doctor testDaoDoctor = new Doctor(1,
                "DoctorA_Name", "DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991, 5, 4),
                "380123455789", "DoctorA_Position");

        when(doctorDao.existsById(expectedDoctorDto.getId())).thenReturn(true);
        when(doctorDao.save(any(Doctor.class))).thenReturn(testDaoDoctor);
        DoctorDto returnedDoctorDto = service.update(expectedDoctorDto.getId(), expectedDoctorDto);
        assertNotNull(returnedDoctorDto, "Dto returned by service is null");
        assertEquals(expectedDoctorDto.getId(), returnedDoctorDto.getId());
        assertEquals(expectedDoctorDto.getFirstName(), returnedDoctorDto.getFirstName());
        assertEquals(expectedDoctorDto.getLastName(), returnedDoctorDto.getLastName());
        assertEquals(expectedDoctorDto.getPatronymic(), returnedDoctorDto.getPatronymic());
        assertEquals(expectedDoctorDto.getBirthDate(), returnedDoctorDto.getBirthDate());
        assertEquals(expectedDoctorDto.getPhoneNumber(), returnedDoctorDto.getPhoneNumber());
        assertEquals(expectedDoctorDto.getPosition(), returnedDoctorDto.getPosition());
        verify(doctorDao, times(1)).existsById(expectedDoctorDto.getId());
        verify(doctorDao, times(1)).save(any(Doctor.class));
    }

    @Test
    @DisplayName("(delete) Pass existing Id, expected to return true")
    void deleteExistingDoctorByIdTest() {
        long testId = 1;
        Doctor doctor = new Doctor(testId, "DoctorA_Name", "DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991, 5, 4),
                "380123455789", "DoctorA_Position");
        doNothing().when(doctorDao).deleteById(1L);
        when(doctorDao.existsById(1L)).thenReturn(true);
        assertTrue(service.delete(doctor.getId()));
        verify(doctorDao, times(1)).existsById(testId);
    }

    @Test
    @DisplayName("(getList) expected to return 2 Dtos, then compare fields to origin entities")
    void getDoctorListTest() {
        Doctor testDoctorA = new Doctor(1,
                "DoctorA_Name", "DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991, 5, 4),
                "380123455789", "DoctorA_Position");
        Doctor testDoctorB = new Doctor(2,
                "DoctorB_Name", "DoctorB_LastName", "DoctorB_Patronymic",
                LocalDate.of(1990, 2, 15),
                "380123856789", "DoctorB_Position");
        List<Doctor> testDoctorList = Arrays.asList(testDoctorA, testDoctorB);

        when(doctorDao.findAll()).thenReturn(testDoctorList);
        List<DoctorDto> doctorReturnDtoList = service.getList();
        assertNotNull(doctorReturnDtoList);
        assertEquals(doctorReturnDtoList.size(), testDoctorList.size());
        for (int index = 0; index < doctorReturnDtoList.size(); index++) {
            DoctorDto returnedDoctorDto = doctorReturnDtoList.get(index);
            Doctor expectedDoctor = testDoctorList.get(index);
            assertNotNull(returnedDoctorDto);
            assertEquals(expectedDoctor.getId(), returnedDoctorDto.getId());
            assertEquals(expectedDoctor.getFirstName(), returnedDoctorDto.getFirstName());
            assertEquals(expectedDoctor.getLastName(), returnedDoctorDto.getLastName());
            assertEquals(expectedDoctor.getPatronymic(), returnedDoctorDto.getPatronymic());
            assertEquals(expectedDoctor.getBirthDate(), returnedDoctorDto.getBirthDate());
            assertEquals(expectedDoctor.getPhoneNumber(), returnedDoctorDto.getPhoneNumber());
            assertEquals(expectedDoctor.getPosition(), returnedDoctorDto.getPosition());
        }
        verify(doctorDao, times(1)).findAll();
    }

    @Test
    @DisplayName("(get) Pass Id, expected to return Dto, fields compared to origin entity")
    void getDoctorByIdTest() {
        long testId = 1;
        Doctor expectedDoctor = new Doctor(testId,
                "DoctorA_Name", "DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991, 5, 4),
                "380123455789", "DoctorA_Position");

        when(doctorDao.getById(testId)).thenReturn(expectedDoctor);
        DoctorDto returnedDoctorDto = service.get(expectedDoctor.getId());
        assertNotNull(returnedDoctorDto);
        assertEquals(expectedDoctor.getId(), returnedDoctorDto.getId());
        assertEquals(expectedDoctor.getFirstName(), returnedDoctorDto.getFirstName());
        assertEquals(expectedDoctor.getLastName(), returnedDoctorDto.getLastName());
        assertEquals(expectedDoctor.getPatronymic(), returnedDoctorDto.getPatronymic());
        assertEquals(expectedDoctor.getBirthDate(), returnedDoctorDto.getBirthDate());
        assertEquals(expectedDoctor.getPhoneNumber(), returnedDoctorDto.getPhoneNumber());
        assertEquals(expectedDoctor.getPosition(), returnedDoctorDto.getPosition());
        verify(doctorDao, times(1)).getById(testId);
    }

    @Test
    @DisplayName("(get) Pass non existing Id, expected exception")
    void getNonExistingDoctorById() {
        long nonExistingId = 1L;
        when(doctorDao.getById(nonExistingId)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> {
            service.get(nonExistingId);
        });
        verify(doctorDao, times(1)).getById(nonExistingId);
    }

    @Test
    @DisplayName("(delete) Pass non existing Id, expected exception")
    void deleteNonExistingDoctorByIdTest() {
        long nonExistingId = 1L;
        when(doctorDao.existsById(nonExistingId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
        verify(doctorDao, times(1)).existsById(nonExistingId);
    }

    @Test
    @DisplayName("(update) Pass non existing Id, expected exception")
    void updateNonExistingDoctorByIdTest() {
        long nonExistingId = 1L;
        when(doctorDao.existsById(nonExistingId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> {
            service.update(nonExistingId, null);
        });
        verify(doctorDao, times(1)).existsById(nonExistingId);
    }

}
