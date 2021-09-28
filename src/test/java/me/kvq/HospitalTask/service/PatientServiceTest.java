package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dao.PatientDao;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.mapper.PatientMapper;
import me.kvq.HospitalTask.model.Patient;
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
    @DisplayName("Add valid patient, expected to return Dto with same fields")
    void addNewValidPatientTest() {
        Patient testPatient = validPatient();
        PatientDto expectedPatientDto = validPatientDto();
        expectedPatientDto.setId(0);
        when(mapper.dtoToEntity(expectedPatientDto)).thenReturn(testPatient);
        when(mapper.entityToDto(testPatient)).thenReturn(expectedPatientDto);
        when(patientDao.save(testPatient)).thenReturn(testPatient);

        PatientDto returnedPatientDto = service.add(expectedPatientDto);
        assertEquals(expectedPatientDto.getId(), returnedPatientDto.getId());
        assertEquals(expectedPatientDto.getFirstName(), returnedPatientDto.getFirstName());
        assertEquals(expectedPatientDto.getLastName(), returnedPatientDto.getLastName());
        assertEquals(expectedPatientDto.getPatronymic(), returnedPatientDto.getPatronymic());
        assertEquals(expectedPatientDto.getBirthDate(), returnedPatientDto.getBirthDate());
        assertEquals(expectedPatientDto.getPhoneNumber(), returnedPatientDto.getPhoneNumber());
        verify(mapper, times(1)).dtoToEntity(expectedPatientDto);
        verify(mapper, times(1)).entityToDto(testPatient);
        verify(patientDao, times(1)).save(testPatient);
    }

    @Test
    @DisplayName("Add valid patient, expected to return Dto with same fields")
    void updateExistingPatientByIdWithValidDataTest() {
        Patient testPatient = validPatient();
        PatientDto expectedPatientDto = validPatientDto();
        long id = expectedPatientDto.getId();
        when(mapper.entityToDto(testPatient)).thenReturn(expectedPatientDto);
        when(mapper.dtoToEntity(expectedPatientDto)).thenReturn(testPatient);
        when(patientDao.existsById(testPatient.getId())).thenReturn(true);
        when(patientDao.save(testPatient)).thenReturn(testPatient);

        PatientDto returnedPatientDto = service.update(expectedPatientDto);
        assertEquals(expectedPatientDto.getId(), returnedPatientDto.getId());
        assertEquals(expectedPatientDto.getFirstName(), returnedPatientDto.getFirstName());
        assertEquals(expectedPatientDto.getLastName(), returnedPatientDto.getLastName());
        assertEquals(expectedPatientDto.getPatronymic(), returnedPatientDto.getPatronymic());
        assertEquals(expectedPatientDto.getBirthDate(), returnedPatientDto.getBirthDate());
        assertEquals(expectedPatientDto.getPhoneNumber(), returnedPatientDto.getPhoneNumber());
        verify(mapper, times(1)).entityToDto(testPatient);
        verify(mapper, times(1)).dtoToEntity(expectedPatientDto);
        verify(patientDao, times(1)).existsById(id);
        verify(patientDao, times(1)).save(testPatient);
    }

    @Test
    @DisplayName("Delete existing patient, no exception expected")
    void deleteExistingPatientByIdTest() {
        long testPatientId = 1;
        when(patientDao.existsById(testPatientId)).thenReturn(true);
        service.delete(testPatientId);
        verify(patientDao, times(1)).existsById(testPatientId);
        verify(patientDao, times(1)).deleteById(testPatientId);
    }

    @Test
    @DisplayName("Find list of all patients, compare Dto fields")
    void getPatientListTest() {
        List<Patient> testPatientList = validPatientList();
        List<PatientDto> expectedPatientDtoList = validPatientDtoList();
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
        }
        verify(patientDao, times(1)).findAll();
        verify(mapper, times(1)).entityListToDtoList(testPatientList);
    }

    @Test
    @DisplayName("Get patient by id, compare Dto fields")
    void getPatientByIdTest() {
        Patient daoPatient = validPatient();
        PatientDto expectedPatientDto = validPatientDto();
        long id = expectedPatientDto.getId();
        when(patientDao.getById(id)).thenReturn(daoPatient);
        when(mapper.entityToDto(daoPatient)).thenReturn(expectedPatientDto);

        PatientDto returnedPatientDto = service.get(id);
        assertEquals(expectedPatientDto.getId(), returnedPatientDto.getId());
        assertEquals(expectedPatientDto.getFirstName(), returnedPatientDto.getFirstName());
        assertEquals(expectedPatientDto.getLastName(), returnedPatientDto.getLastName());
        assertEquals(expectedPatientDto.getPatronymic(), returnedPatientDto.getPatronymic());
        assertEquals(expectedPatientDto.getBirthDate(), returnedPatientDto.getBirthDate());
        assertEquals(expectedPatientDto.getPhoneNumber(), returnedPatientDto.getPhoneNumber());
        verify(patientDao, times(1)).getById(id);
        verify(mapper, times(1)).entityToDto(daoPatient);
    }

    @Test
    @DisplayName("Get patient by invalid id, exception expected")
    void getNonExistingPatientByIdTest() {
        long nonExistingId = 1L;
        when(patientDao.getById(nonExistingId)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> {
            service.get(nonExistingId);
        });
        verify(patientDao, times(1)).getById(nonExistingId);
    }

    @Test
    @DisplayName("Delete patient by invalid id, exception expected")
    void deleteNonExistingPatientByIdTestTest() {
        long nonExistingId = 1L;
        when(patientDao.existsById(nonExistingId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
        verify(patientDao, times(1)).existsById(nonExistingId);
    }

    @Test
    @DisplayName("Update patient with invalid id, exception expected")
    void updateNonExistingPatientByIdTestTest() {
        PatientDto dto = validPatientDto();
        long nonExistingId = dto.getId();
        when(patientDao.existsById(nonExistingId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> {
            service.update(dto);
        });
        verify(patientDao, times(1)).existsById(nonExistingId);
    }

}
