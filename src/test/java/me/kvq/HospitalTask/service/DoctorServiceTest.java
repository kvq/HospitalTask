package me.kvq.HospitalTask.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.model.Doctor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class DoctorServiceTest {
    @MockBean
    DoctorDao doctorDao;
    @Autowired
    DoctorService service;

    @Test
    @DisplayName("(add) Pass Valid Dto, then compares return Dto to passed, and checks id not same")
    void addNewValidDoctorTest(){
        DoctorDto testDoctorDto = new DoctorDto(0,
                "Name", "LastName", "Patronymic",
                LocalDate.of(1999,10,2),
                "380123456789", "Position");
        Doctor testDaoDoctor = new Doctor(1,
                "Name", "LastName", "Patronymic",
                LocalDate.of(1999,10,2),
                "380123456789", "Position");

        when(doctorDao.save(any(Doctor.class))).thenReturn(testDaoDoctor);

        DoctorDto returnedDoctorDto = service.add(testDoctorDto);
        assertNotNull(returnedDoctorDto,"Dto returned by service is null");

        assertNotEquals(testDoctorDto.getId(),returnedDoctorDto.getId());
        assertEquals(testDoctorDto.getFirstName(),returnedDoctorDto.getFirstName());
        assertEquals(testDoctorDto.getLastName(),returnedDoctorDto.getLastName());
        assertEquals(testDoctorDto.getPatronymic(),returnedDoctorDto.getPatronymic());
        assertEquals(testDoctorDto.getBirthDate(),returnedDoctorDto.getBirthDate());
        assertEquals(testDoctorDto.getPhoneNumber(),returnedDoctorDto.getPhoneNumber());
        assertEquals(testDoctorDto.getPosition(),returnedDoctorDto.getPosition());
        verify(doctorDao, times(1)).save(any(Doctor.class));
    }

    @Test
    @DisplayName("(update) Pass existing Id & Valid Dto, then compares return Dto to passed")
    void updateExistingDoctorByIdWithValidDataTest(){
        DoctorDto testDoctorDto = new DoctorDto(1,
                "DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789","DoctorA_Position");
        Doctor testDaoDoctor = new Doctor(1,
                "DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789","DoctorA_Position");

        when(doctorDao.existsById(testDoctorDto.getId())).thenReturn(true);
        when(doctorDao.save(any(Doctor.class))).thenReturn(testDaoDoctor);

        DoctorDto returnedDoctor = service.update(testDoctorDto.getId(),testDoctorDto);
        assertNotNull(returnedDoctor, "Dto returned by service is null");
        assertEquals(testDoctorDto.getId(),returnedDoctor.getId());
        assertEquals(testDoctorDto.getFirstName(),returnedDoctor.getFirstName());
        assertEquals(testDoctorDto.getLastName(),returnedDoctor.getLastName());
        assertEquals(testDoctorDto.getPatronymic(),returnedDoctor.getPatronymic());
        assertEquals(testDoctorDto.getBirthDate(),returnedDoctor.getBirthDate());
        assertEquals(testDoctorDto.getPhoneNumber(),returnedDoctor.getPhoneNumber());
        assertEquals(testDoctorDto.getPosition(),returnedDoctor.getPosition());
        verify(doctorDao,times(1)).existsById(anyLong());
        verify(doctorDao,times(1)).save(any(Doctor.class));
    }

    @Test
    @DisplayName("(delete) Pass existing Id, expected to return true")
    void deleteExistingDoctorByIdTest(){
        Doctor doctor = new Doctor(1,"DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789","DoctorA_Position");
        doNothing().when(doctorDao).deleteById(1L);
        when(doctorDao.existsById(1L)).thenReturn(true);

        assertTrue(service.delete(doctor.getId()), "Doctor wasn't deleted");
    }

    @Test
    @DisplayName("(getList) expected to return 2 Dtos, then compare fields to origin entities")
    void getDoctorListTest(){
        Doctor testDoctorA = new Doctor(1,
                "DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789","DoctorA_Position");
        Doctor testDoctorB = new Doctor(2,
                "DoctorB_Name","DoctorB_LastName", "DoctorB_Patronymic",
                LocalDate.of(1990,2,15),
                "380123856789","DoctorB_Position");
        List<Doctor> testDoctorList = Arrays.asList(testDoctorA,testDoctorB);

        when(doctorDao.findAll()).thenReturn(testDoctorList);

        List<DoctorDto> doctorReturnDtoList = service.getList();
        assertNotNull(doctorReturnDtoList);
        assertEquals(doctorReturnDtoList.size() ,testDoctorList.size());

        for (int index = 0; index < doctorReturnDtoList.size(); index++){
            DoctorDto returnedDoctorDto = doctorReturnDtoList.get(index);
            Doctor testDoctor = testDoctorList.get(index);
            assertNotNull(returnedDoctorDto);
            assertEquals(testDoctor.getId(),returnedDoctorDto.getId());
            assertEquals(testDoctor.getFirstName(),returnedDoctorDto.getFirstName());
            assertEquals(testDoctor.getLastName(),returnedDoctorDto.getLastName());
            assertEquals(testDoctor.getPatronymic(),returnedDoctorDto.getPatronymic());
            assertEquals(testDoctor.getBirthDate(),returnedDoctorDto.getBirthDate());
            assertEquals(testDoctor.getPhoneNumber(),returnedDoctorDto.getPhoneNumber());
            assertEquals(testDoctor.getPosition(),returnedDoctorDto.getPosition());
        }
        verify(doctorDao,times(1)).findAll();
    }

    @Test
    @DisplayName("(get) Pass Id, expected to return Dto, fields compared to origin entity")
    void getDoctorByIdTest(){
        Doctor testDoctor = new Doctor(1,
                "DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789","DoctorA_Position");

        when(doctorDao.getById(1L)).thenReturn(testDoctor);

        DoctorDto returnedDoctor = service.get(testDoctor.getId());
        assertNotNull(returnedDoctor);
        assertEquals(testDoctor.getId(),returnedDoctor.getId());
        assertEquals(testDoctor.getFirstName(),returnedDoctor.getFirstName());
        assertEquals(testDoctor.getLastName(),returnedDoctor.getLastName());
        assertEquals(testDoctor.getPatronymic(),returnedDoctor.getPatronymic());
        assertEquals(testDoctor.getBirthDate(),returnedDoctor.getBirthDate());
        assertEquals(testDoctor.getPhoneNumber(),returnedDoctor.getPhoneNumber());
        assertEquals(testDoctor.getPosition(),returnedDoctor.getPosition());
        verify(doctorDao, times(1)).getById(anyLong());
    }

}
