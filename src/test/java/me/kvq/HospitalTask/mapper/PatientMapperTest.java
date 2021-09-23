package me.kvq.HospitalTask.mapper;

import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.model.Patient;
import me.kvq.HospitalTask.testData.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PatientMapperTest {
    @MockBean
    DoctorDao doctorDao;
    @Autowired
    PatientMapper mapper;

    @Test
    @DisplayName("(entityToDto) passes Patient, compare returned Dto fields")
    void mappingEntityToDtoTest() {
        Patient originPatient = TestDataGenerator.validPatient();
        PatientDto returnedPatientDto = mapper.entityToDto(originPatient);
        assertEquals(originPatient.getId(), returnedPatientDto.getId());
        assertEquals(originPatient.getFirstName(), returnedPatientDto.getFirstName());
        assertEquals(originPatient.getLastName(), returnedPatientDto.getLastName());
        assertEquals(originPatient.getPatronymic(), returnedPatientDto.getPatronymic());
        assertEquals(originPatient.getBirthDate(), returnedPatientDto.getBirthDate());
        assertEquals(originPatient.getPhoneNumber(), returnedPatientDto.getPhoneNumber());
        for (int index = 0; index < originPatient.getDoctors().size(); index++) {
            long expectedPatientId = originPatient.getDoctors().get(index).getId();
            long returnedPatientId = returnedPatientDto.getDoctors()[index];
            assertEquals(expectedPatientId, returnedPatientId);
        }
    }

    @Test
    @DisplayName("(dtoToEntity) passes Id & PatientDto, compare returned Dto fields")
    void mappingDtoToEntityTest() {
        PatientDto originPatientDto = TestDataGenerator.validPatientDto();
        when(doctorDao.existsById(1L)).thenReturn(true);
        when(doctorDao.existsById(3L)).thenReturn(true);
        Patient returnedPatient = mapper.dtoToEntity(originPatientDto.getId(), originPatientDto);
        assertEquals(originPatientDto.getId(), returnedPatient.getId());
        assertEquals(originPatientDto.getFirstName(), returnedPatient.getFirstName());
        assertEquals(originPatientDto.getLastName(), returnedPatient.getLastName());
        assertEquals(originPatientDto.getPatronymic(), returnedPatient.getPatronymic());
        assertEquals(originPatientDto.getBirthDate(), returnedPatient.getBirthDate());
        assertEquals(originPatientDto.getPhoneNumber(), returnedPatient.getPhoneNumber());
        for (int index = 0; index < originPatientDto.getDoctors().length; index++) {
            long expectedPatientId = originPatientDto.getDoctors()[index];
            long returnedPatientId = returnedPatient.getDoctors().get(index).getId();
            assertEquals(expectedPatientId, returnedPatientId);
        }
        verify(doctorDao, times(1)).existsById(1L);
        verify(doctorDao, times(1)).existsById(3L);
    }

    @Test
    @DisplayName("(entityListToDtoList) passes Patient list, checks returned list size & compare Dtos")
    void mappingEntityListToDtoListTest() {
        List<Patient> patientList = TestDataGenerator.validPatientList();
        List<PatientDto> returnPatientDtoList = mapper.entityListToDtoList(patientList);
        assertEquals(patientList.size(), returnPatientDtoList.size());
        for (int i = 0; i < patientList.size(); i++) {
            Patient originEntity = patientList.get(i);
            PatientDto returnedDto = returnPatientDtoList.get(i);
            assertEquals(originEntity.getId(), returnedDto.getId());
            assertEquals(originEntity.getFirstName(), returnedDto.getFirstName());
            assertEquals(originEntity.getLastName(), returnedDto.getLastName());
            assertEquals(originEntity.getPatronymic(), returnedDto.getPatronymic());
            assertEquals(originEntity.getBirthDate(), returnedDto.getBirthDate());
            assertEquals(originEntity.getPhoneNumber(), returnedDto.getPhoneNumber());
            for (int doctorIndex = 0; doctorIndex < originEntity.getDoctors().size(); doctorIndex++) {
                long expectedPatientId = originEntity.getDoctors().get(doctorIndex).getId();
                long returnedPatientId = returnedDto.getDoctors()[doctorIndex];
                assertEquals(expectedPatientId, returnedPatientId);
            }
        }
    }

}
