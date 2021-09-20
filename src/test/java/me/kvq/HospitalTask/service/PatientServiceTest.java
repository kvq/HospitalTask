package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dao.PatientDao;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.mapper.PatientMapper;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.model.Patient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PatientServiceTest {
    @MockBean
    PatientDao patientDao;
    @MockBean
    DoctorDao doctorDao;
    @Autowired
    PatientMapper mapper;
    @Autowired
    PatientService service;

    @Test
    @DisplayName("(add) Pass Valid Dto, then compare return Dto to passed, and checks id not same")
    void addNewValidPatientTest() {
        Doctor testDoctor = new Doctor(3, "DoctorA_Name", "DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991, 5, 4),
                "380123455789", "DoctorA_Position");
        PatientDto testingPatient = new PatientDto(0, "Name", "LastName", "Patronymic",
                LocalDate.of(1999, 10, 2),
                "380123456789", testDoctor.getId());
        Patient expectedPatient = new Patient(1, "Name", "LastName", "Patronymic",
                LocalDate.of(1999, 10, 2),
                "380123456789", testDoctor);

        when(doctorDao.getById(testDoctor.getId())).thenReturn(testDoctor);
        when(patientDao.save(any(Patient.class))).thenReturn(expectedPatient);
        PatientDto returnedDto = service.add(testingPatient);
        assertNotNull(returnedDto, "Dto returned by service is null");
        assertEquals(expectedPatient.getId(), returnedDto.getId());
        assertEquals(expectedPatient.getFirstName(), returnedDto.getFirstName());
        assertEquals(expectedPatient.getLastName(), returnedDto.getLastName());
        assertEquals(expectedPatient.getPatronymic(), returnedDto.getPatronymic());
        assertEquals(expectedPatient.getBirthDate(), returnedDto.getBirthDate());
        assertEquals(expectedPatient.getPhoneNumber(), returnedDto.getPhoneNumber());
        assertEquals(expectedPatient.getDoctor().getId(), testDoctor.getId());
        verify(doctorDao, times(1)).getById(testDoctor.getId());
        verify(patientDao, times(1)).save(any(Patient.class));
    }

    @Test
    @DisplayName("(update) Pass Id & Dto, then compare return Dto to passed")
    void updateExistingPatientByIdWithValidDataTest() {
        Doctor testDoctor = new Doctor(3,
                "DoctorA_Name", "DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991, 5, 4),
                "380123455789", "DoctorA_Position");
        PatientDto testPatient = new PatientDto(1,
                "PatientA_Name", "PatientA_LastName", "PatientA_Patronymic",
                LocalDate.of(1991, 5, 4),
                "380123455789", testDoctor.getId());
        Patient expectedPatient = new Patient(1,
                "PatientA_Name", "PatientA_LastName", "PatientA_Patronymic",
                LocalDate.of(1991, 5, 4),
                "380123455789", testDoctor);

        when(doctorDao.getById(testDoctor.getId())).thenReturn(testDoctor);
        when(patientDao.existsById(testPatient.getId())).thenReturn(true);
        when(patientDao.save(any(Patient.class))).thenReturn(expectedPatient);
        PatientDto returnedPatient = service.update(testPatient.getId(), testPatient);
        assertNotNull(returnedPatient, "Dto returned by service is null");
        assertEquals(expectedPatient.getId(), returnedPatient.getId());
        assertEquals(expectedPatient.getFirstName(), returnedPatient.getFirstName());
        assertEquals(expectedPatient.getLastName(), returnedPatient.getLastName());
        assertEquals(expectedPatient.getPatronymic(), returnedPatient.getPatronymic());
        assertEquals(expectedPatient.getBirthDate(), returnedPatient.getBirthDate());
        assertEquals(expectedPatient.getPhoneNumber(), returnedPatient.getPhoneNumber());
        assertEquals(expectedPatient.getDoctor().getId(), returnedPatient.getDoctor());
        verify(doctorDao, times(1)).getById(testDoctor.getId());
        verify(patientDao, times(1)).existsById(testPatient.getId());
        verify(patientDao, times(1)).save(any(Patient.class));
    }

    @Test
    @DisplayName("(delete) Pass Id, expected to return true")
    void deleteExistingPatientByIdTest() {
        long testPatientId = 1;
        doNothing().when(patientDao).deleteById(testPatientId);
        when(patientDao.existsById(testPatientId)).thenReturn(true);
        assertTrue(service.delete(testPatientId));
        verify(patientDao, times(1)).existsById(testPatientId);
    }

    @Test
    @DisplayName("(getList) expected to return 2 Dtos, then compare fields to origin entities")
    void detPatientListTest() {
        Patient testPatientA = new Patient(1,
                "PatientA_Name", "PatientA_LastName", "PatientA_Patronymic",
                LocalDate.of(1991, 5, 4),
                "380123455789", null);
        Patient testPatientB = new Patient(2,
                "PatientB_Name", "PatientB_LastName", "PatientB_Patronymic",
                LocalDate.of(1990, 2, 15),
                "380123856789", null);
        List<Patient> testPatientList = Arrays.asList(testPatientA, testPatientB);

        when(patientDao.findAll()).thenReturn(testPatientList);
        List<PatientDto> returnedPatientDtoList = service.getList();
        assertEquals(2, returnedPatientDtoList.size(), "Expected 2 patients to be returned");
        for (int index = 0; index < returnedPatientDtoList.size(); index++) {
            PatientDto returnedPatientDto = returnedPatientDtoList.get(index);
            Patient expectedPatient = testPatientList.get(index);
            assertNotNull(returnedPatientDto);
            assertEquals(expectedPatient.getId(), returnedPatientDto.getId());
            assertEquals(expectedPatient.getFirstName(), returnedPatientDto.getFirstName());
            assertEquals(expectedPatient.getLastName(), returnedPatientDto.getLastName());
            assertEquals(expectedPatient.getPatronymic(), returnedPatientDto.getPatronymic());
            assertEquals(expectedPatient.getBirthDate(), returnedPatientDto.getBirthDate());
            assertEquals(expectedPatient.getPhoneNumber(), returnedPatientDto.getPhoneNumber());
        }
        verify(patientDao, times(1)).findAll();
    }

    @Test
    @DisplayName("(get) Pass Id, expected to return Dto, fields compared to origin entity")
    void getPatientByIdTest() {
        Doctor testDoctor = new Doctor(3,
                "DoctorA_Name", "DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991, 5, 4),
                "380123455789", "DoctorA_Position");
        Patient expectedPatient = new Patient(1,
                "PatientA_Name", "PatientA_LastName", "PatientA_Patronymic",
                LocalDate.of(1991, 5, 4),
                "380123455789", testDoctor);

        when(patientDao.getById(expectedPatient.getId())).thenReturn(expectedPatient);
        PatientDto returnedPatient = service.get(expectedPatient.getId());
        assertNotNull(returnedPatient);
        assertEquals(expectedPatient.getId(), returnedPatient.getId());
        assertEquals(expectedPatient.getId(), returnedPatient.getId());
        assertEquals(expectedPatient.getFirstName(), returnedPatient.getFirstName());
        assertEquals(expectedPatient.getLastName(), returnedPatient.getLastName());
        assertEquals(expectedPatient.getPatronymic(), returnedPatient.getPatronymic());
        assertEquals(expectedPatient.getBirthDate(), returnedPatient.getBirthDate());
        assertEquals(expectedPatient.getPhoneNumber(), returnedPatient.getPhoneNumber());
        assertEquals(expectedPatient.getDoctor().getId(), returnedPatient.getDoctor());
        verify(patientDao, times(1)).getById(expectedPatient.getId());
    }

    @Test
    @DisplayName("(get) Pass non existing Id, expected exception")
    void getNonExistingPatientById() {
        long nonExistingId = 1L;
        when(patientDao.getById(nonExistingId)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> {
            service.get(nonExistingId);
        });
        verify(patientDao, times(1)).getById(nonExistingId);
    }

    @Test
    @DisplayName("(delete) Pass non existing Id, expected exception")
    void deleteNonExistingPatientByIdTest() {
        long nonExistingId = 1L;
        when(patientDao.existsById(nonExistingId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
        verify(patientDao, times(1)).existsById(nonExistingId);
    }

    @Test
    @DisplayName("(update) Pass non existing Id, expected exception")
    void updateNonExistingPatientByIdTest() {
        long nonExistingId = 1L;
        when(patientDao.existsById(nonExistingId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> {
            service.update(nonExistingId, null);
        });
        verify(patientDao, times(1)).existsById(nonExistingId);
    }

}
