package me.kvq.HospitalTask.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.mapper.DoctorMapper;
import me.kvq.HospitalTask.model.Doctor;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class DoctorServiceTest {
    @Mock
    DoctorDao doctorDao;
    @Autowired
    DoctorMapper doctorMapper;
    DoctorService service;

    @BeforeEach
    void resetMocks(){
        service = new DoctorService(doctorDao,doctorMapper);
    }

    @Test
    @DisplayName("(add) Pass Valid Dto, then compares return Dto to passed, and checks id not same")
    void serviceAddNewValidDoctorTest(){
        DoctorDto testDoctor = new DoctorDto(0,
                "Name", "LastName", "Patronymic",
                LocalDate.of(1999,10,2),
                "380123456789", "Position");

        when(doctorDao.save(any(Doctor.class))).thenAnswer(invocation -> {
            Doctor doctor = invocation.getArgument(0,Doctor.class);
            doctor.setId(1);
            return doctor;
        });

        DoctorDto returnedDoctorDto = service.add(testDoctor);
        assertNotNull(returnedDoctorDto,"Dto returned by service is null");

        assertNotEquals(testDoctor.getId(),returnedDoctorDto.getId());
        assertEquals(testDoctor.getFirstName(),returnedDoctorDto.getFirstName());
        assertEquals(testDoctor.getLastName(),returnedDoctorDto.getLastName());
        assertEquals(testDoctor.getPatronymic(),returnedDoctorDto.getPatronymic());
        assertEquals(testDoctor.getBirthDate(),returnedDoctorDto.getBirthDate());
        assertEquals(testDoctor.getPhoneNumber(),returnedDoctorDto.getPhoneNumber());
        assertEquals(testDoctor.getPosition(),returnedDoctorDto.getPosition());
    }

    @Test
    @DisplayName("(update) Pass existing Id & Valid Dto, then compares return Dto to passed")
    void serviceUpdateExistingDoctorByIdWithValidDataTest(){
        DoctorDto testDoctor = new DoctorDto(1,
                "DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789","DoctorA_Position");

        when(doctorDao.existsById(1L)).thenReturn(true);
        when(doctorDao.save(any(Doctor.class))).thenAnswer(invocation -> {
            return invocation.getArgument(0,Doctor.class);
        });

        DoctorDto returnedDoctor = service.update(testDoctor.getId(),testDoctor);
        assertNotNull(returnedDoctor, "Dto returned by service is null");
        assertEquals(testDoctor.getId(),returnedDoctor.getId());
        assertEquals(testDoctor.getFirstName(),returnedDoctor.getFirstName());
        assertEquals(testDoctor.getLastName(),returnedDoctor.getLastName());
        assertEquals(testDoctor.getPatronymic(),returnedDoctor.getPatronymic());
        assertEquals(testDoctor.getBirthDate(),returnedDoctor.getBirthDate());
        assertEquals(testDoctor.getPhoneNumber(),returnedDoctor.getPhoneNumber());
        assertEquals(testDoctor.getPosition(),returnedDoctor.getPosition());
    }

    @Test
    @DisplayName("(delete) Pass existing Id, expected to return true")
    void serviceDeleteExistingDoctorByIdTest(){
        Doctor doctor = new Doctor(1,"DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789","DoctorA_Position");
        doNothing().when(doctorDao).deleteById(1L);
        when(doctorDao.existsById(1L)).thenReturn(true);

        assertTrue(service.delete(doctor.getId()), "Doctor wasn't deleted");
    }

    @Test
    @DisplayName("(getList) expected to return 2 Dtos, then compare fields to origin entities")
    void serviceGetDoctorListTest(){
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

        for (int i = 0; i < doctorReturnDtoList.size(); i++){
            DoctorDto returnedDoctorDto = doctorReturnDtoList.get(i);
            Doctor testDoctor = testDoctorList.get(i);
            assertNotNull(returnedDoctorDto);
            assertEquals(testDoctor.getId(),returnedDoctorDto.getId());
            assertEquals(testDoctor.getFirstName(),returnedDoctorDto.getFirstName());
            assertEquals(testDoctor.getLastName(),returnedDoctorDto.getLastName());
            assertEquals(testDoctor.getPatronymic(),returnedDoctorDto.getPatronymic());
            assertEquals(testDoctor.getBirthDate(),returnedDoctorDto.getBirthDate());
            assertEquals(testDoctor.getPhoneNumber(),returnedDoctorDto.getPhoneNumber());
            assertEquals(testDoctor.getPosition(),returnedDoctorDto.getPosition());
        }
    }

    @Test
    @DisplayName("(get) Pass Id, expected to return Dto, fields compared to origin entity")
    void serviceGetDoctorByIdTest(){
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
    }

}
