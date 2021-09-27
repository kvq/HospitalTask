package me.kvq.HospitalTask.mapper;

import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.model.Patient;
import me.kvq.HospitalTask.testData.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PatientMapperTest {
    @MockBean
    DoctorDao doctorDao;
    @Autowired
    PatientMapper mapper;

    @Test
    @DisplayName("(entityToDto) passes Patient, compare returned Dto fields")
    void mappingEntityToDtoTest() {
        Patient expected = TestDataGenerator.validPatient();
        PatientDto actual = mapper.entityToDto(expected);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getPatronymic(), actual.getPatronymic());
        assertEquals(expected.getBirthDate(), actual.getBirthDate());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
        for (int index = 0; index < expected.getDoctors().size(); index++) {
            Doctor expectedDoctor = expected.getDoctors().get(index);
            DoctorDto actualDoctor = actual.getDoctors()[index];
            assertEquals(expectedDoctor.getId(), actualDoctor.getId());
            assertEquals(expectedDoctor.getFirstName(), actualDoctor.getFirstName());
            assertEquals(expectedDoctor.getLastName(), actualDoctor.getLastName());
            assertEquals(expectedDoctor.getPatronymic(), actualDoctor.getPatronymic());
            assertEquals(expectedDoctor.getPhoneNumber(), actualDoctor.getPhoneNumber());
            assertEquals(expectedDoctor.getPosition(), actualDoctor.getPosition());
        }
    }

    @Test
    @DisplayName("(dtoToEntity) passes Id & PatientDto, compare returned Dto fields")
    void mappingDtoToEntityTest() {
        PatientDto expected = TestDataGenerator.validPatientDto();
        when(doctorDao.existsById(1L)).thenReturn(true);
        when(doctorDao.existsById(3L)).thenReturn(true);
        Patient actual = mapper.dtoToEntity(expected);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getPatronymic(), actual.getPatronymic());
        assertEquals(expected.getBirthDate(), actual.getBirthDate());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
        assertEquals(expected.getDoctors()[0].getId(), actual.getDoctors().get(0).getId());
        assertEquals(expected.getDoctors()[1].getId(), actual.getDoctors().get(1).getId());
    }

    @Test
    @DisplayName("(entityListToDtoList) passes Patient list, checks returned list size & compare Dtos")
    void mappingEntityListToDtoListTest() {
        List<Patient> patientList = TestDataGenerator.validPatientList();
        List<PatientDto> returnPatientDtoList = mapper.entityListToDtoList(patientList);
        assertEquals(patientList.size(), returnPatientDtoList.size());
        for (int i = 0; i < patientList.size(); i++) {
            Patient expected = patientList.get(i);
            PatientDto actual = returnPatientDtoList.get(i);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getFirstName(), actual.getFirstName());
            assertEquals(expected.getLastName(), actual.getLastName());
            assertEquals(expected.getPatronymic(), actual.getPatronymic());
            assertEquals(expected.getBirthDate(), actual.getBirthDate());
            assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
            for (int index = 0; index < expected.getDoctors().size(); index++) {
                Doctor expectedDoctor = expected.getDoctors().get(index);
                DoctorDto actualDoctor = actual.getDoctors()[index];
                assertEquals(expectedDoctor.getId(), actualDoctor.getId());
                assertEquals(expectedDoctor.getFirstName(), actualDoctor.getFirstName());
                assertEquals(expectedDoctor.getLastName(), actualDoctor.getLastName());
                assertEquals(expectedDoctor.getPatronymic(), actualDoctor.getPatronymic());
                assertEquals(expectedDoctor.getPhoneNumber(), actualDoctor.getPhoneNumber());
                assertEquals(expectedDoctor.getPosition(), actualDoctor.getPosition());
            }
        }
    }

}
