package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dao.PatientDao;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.mapper.PatientMapper;
import me.kvq.HospitalTask.model.Patient;
import me.kvq.HospitalTask.testData.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PatientServiceTest {
    @MockBean
    PatientDao patientDao;
    @MockBean
    DoctorDao doctorDao;
    @MockBean
    PatientMapper mapper;
    @Autowired
    PatientService service;

    @Test
    @DisplayName("(add) Pass Valid Dto, then compare returned Dto to passed")
    void addNewValidPatientTest() {
        TestDataGenerator.TestData<Patient, PatientDto> testData = TestDataGenerator.getValidPatientData();
        Patient testPatient = testData.getEntity();
        PatientDto expectedPatientDto = testData.getDto();
        when(mapper.dtoToEntity(0, expectedPatientDto)).thenReturn(testPatient);
        when(mapper.entityToDto(testPatient)).thenReturn(expectedPatientDto);
        when(patientDao.save(testPatient)).thenReturn(testPatient);

        PatientDto returnedPatientDto = service.add(expectedPatientDto);
        assertEquals(expectedPatientDto.getId(), returnedPatientDto.getId());
        assertEquals(expectedPatientDto.getFirstName(), returnedPatientDto.getFirstName());
        assertEquals(expectedPatientDto.getLastName(), returnedPatientDto.getLastName());
        assertEquals(expectedPatientDto.getPatronymic(), returnedPatientDto.getPatronymic());
        assertEquals(expectedPatientDto.getBirthDate(), returnedPatientDto.getBirthDate());
        assertEquals(expectedPatientDto.getPhoneNumber(), returnedPatientDto.getPhoneNumber());
        assertArrayEquals(expectedPatientDto.getDoctors(), returnedPatientDto.getDoctors());
        verify(mapper, times(1)).dtoToEntity(0, expectedPatientDto);
        verify(mapper, times(1)).entityToDto(testPatient);
        verify(patientDao, times(1)).save(testPatient);
    }

    @Test
    @DisplayName("(update) Pass Id & Dto, then compare return Dto to passed")
    void updateExistingPatientByIdWithValidDataTest() {
        TestDataGenerator.TestData<Patient, PatientDto> testData = TestDataGenerator.getValidPatientData();
        Patient testPatient = testData.getEntity();
        PatientDto expectedPatientDto = testData.getDto();
        long id = testData.getId();
        when(mapper.entityToDto(testPatient)).thenReturn(expectedPatientDto);
        when(mapper.dtoToEntity(expectedPatientDto.getId(), expectedPatientDto)).thenReturn(testPatient);
        when(patientDao.existsById(testPatient.getId())).thenReturn(true);
        when(patientDao.save(testPatient)).thenReturn(testPatient);

        PatientDto returnedPatientDto = service.update(id, expectedPatientDto);
        assertEquals(expectedPatientDto.getId(), returnedPatientDto.getId());
        assertEquals(expectedPatientDto.getFirstName(), returnedPatientDto.getFirstName());
        assertEquals(expectedPatientDto.getLastName(), returnedPatientDto.getLastName());
        assertEquals(expectedPatientDto.getPatronymic(), returnedPatientDto.getPatronymic());
        assertEquals(expectedPatientDto.getBirthDate(), returnedPatientDto.getBirthDate());
        assertEquals(expectedPatientDto.getPhoneNumber(), returnedPatientDto.getPhoneNumber());
        assertArrayEquals(expectedPatientDto.getDoctors(), returnedPatientDto.getDoctors());
        verify(mapper, times(1)).entityToDto(testPatient);
        verify(mapper, times(1)).dtoToEntity(id, expectedPatientDto);
        verify(patientDao, times(1)).existsById(id);
        verify(patientDao, times(1)).save(testPatient);
    }

    @Test
    @DisplayName("(delete) Pass Id, expected to return true")
    void deleteExistingPatientByIdTest() {
        long testPatientId = 1;
        when(patientDao.existsById(testPatientId)).thenReturn(true);
        assertTrue(service.delete(testPatientId));
        verify(patientDao, times(1)).existsById(testPatientId);
        verify(patientDao, times(1)).deleteById(testPatientId);
    }

    @Test
    @DisplayName("(getList) expected to return 2 Dtos, then compare fields to origin entities")
    void getPatientListTest() {
        List<Patient> testPatientList = TestDataGenerator.validPatientList();
        List<PatientDto> expectedPatientDtoList = TestDataGenerator.validPatientDtoList();
        when(patientDao.findAll()).thenReturn(testPatientList);
        when(mapper.entityListToDtoList(testPatientList)).thenReturn(expectedPatientDtoList);
        List<PatientDto> returnedPatientDtoList = service.getList();
        assertEquals(2, returnedPatientDtoList.size(), "Expected 2 patients to be returned");
        for (int index = 0; index < returnedPatientDtoList.size(); index++) {
            PatientDto returnedPatientDto = returnedPatientDtoList.get(index);
            PatientDto expectedPatientDto = expectedPatientDtoList.get(index);
            assertEquals(expectedPatientDto.getId(), returnedPatientDto.getId());
            assertEquals(expectedPatientDto.getFirstName(), returnedPatientDto.getFirstName());
            assertEquals(expectedPatientDto.getLastName(), returnedPatientDto.getLastName());
            assertEquals(expectedPatientDto.getPatronymic(), returnedPatientDto.getPatronymic());
            assertEquals(expectedPatientDto.getBirthDate(), returnedPatientDto.getBirthDate());
            assertEquals(expectedPatientDto.getPhoneNumber(), returnedPatientDto.getPhoneNumber());
            assertArrayEquals(expectedPatientDto.getDoctors(), returnedPatientDto.getDoctors());
        }
        verify(patientDao, times(1)).findAll();
        verify(mapper, times(1)).entityListToDtoList(testPatientList);
    }

    @Test
    @DisplayName("(get) Pass Id, expected to return Dto, fields compared to origin entity")
    void getPatientByIdTest() {
        TestDataGenerator.TestData<Patient, PatientDto> testData = TestDataGenerator.getValidPatientData();
        Patient daoPatient = testData.getEntity();
        PatientDto expectedPatientDto = testData.getDto();
        long id = testData.getId();
        when(patientDao.getById(id)).thenReturn(daoPatient);
        when(mapper.entityToDto(daoPatient)).thenReturn(expectedPatientDto);
        PatientDto returnedPatientDto = service.get(id);
        assertEquals(expectedPatientDto.getId(), returnedPatientDto.getId());
        assertEquals(expectedPatientDto.getFirstName(), returnedPatientDto.getFirstName());
        assertEquals(expectedPatientDto.getLastName(), returnedPatientDto.getLastName());
        assertEquals(expectedPatientDto.getPatronymic(), returnedPatientDto.getPatronymic());
        assertEquals(expectedPatientDto.getBirthDate(), returnedPatientDto.getBirthDate());
        assertEquals(expectedPatientDto.getPhoneNumber(), returnedPatientDto.getPhoneNumber());
        assertArrayEquals(expectedPatientDto.getDoctors(), returnedPatientDto.getDoctors());
        verify(patientDao, times(1)).getById(id);
        verify(mapper, times(1)).entityToDto(daoPatient);
    }

    @Test
    @DisplayName("(get) Pass non existing Id, expected exception")
    void getNonExistingPatientByIdTest() {
        long nonExistingId = 1L;
        when(patientDao.getById(nonExistingId)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> {
            service.get(nonExistingId);
        });
        verify(patientDao, times(1)).getById(nonExistingId);
    }

    @Test
    @DisplayName("(delete) Pass non existing Id, expected exception")
    void deleteNonExistingPatientByIdTestTest() {
        long nonExistingId = 1L;
        when(patientDao.existsById(nonExistingId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
        verify(patientDao, times(1)).existsById(nonExistingId);
    }

    @Test
    @DisplayName("(update) Pass non existing Id, expected exception")
    void updateNonExistingPatientByIdTestTest() {
        long nonExistingId = 1L;
        when(patientDao.existsById(nonExistingId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> {
            service.update(nonExistingId, null);
        });
        verify(patientDao, times(1)).existsById(nonExistingId);
    }

}
