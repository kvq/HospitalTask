package me.kvq.HospitalTask.mapper;

import me.kvq.HospitalTask.dao.PatientDao;
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
public class DoctorMapperTest {
    @MockBean
    PatientDao patientDao;
    @Autowired
    DoctorMapper mapper;

    @Test
    @DisplayName("(entityToDto) passes Doctor, compare returned Dto fields")
    void mappingEntityToDtoTest() {
        Doctor expected = TestDataGenerator.validDoctor();
        DoctorDto actual = mapper.entityToDto(expected);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getPatronymic(), actual.getPatronymic());
        assertEquals(expected.getBirthDate(), actual.getBirthDate());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
        assertEquals(expected.getPosition(), actual.getPosition());
        for (int index = 0; index < expected.getPatients().size(); index++) {
            Patient expectedPatient = expected.getPatients().get(index);
            PatientDto actualPatient = actual.getPatients()[index];
            assertEquals(expectedPatient.getId(), actualPatient.getId());
            assertEquals(expectedPatient.getFirstName(), actualPatient.getFirstName());
            assertEquals(expectedPatient.getLastName(), actualPatient.getLastName());
            assertEquals(expectedPatient.getPatronymic(), actualPatient.getPatronymic());
            assertEquals(expectedPatient.getPhoneNumber(), actualPatient.getPhoneNumber());
        }
    }

    @Test
    @DisplayName("(dtoToEntity) passes Id & DoctorDto, compare returned Dto fields")
    void mappingDtoToEntityTest() {
        DoctorDto expected = TestDataGenerator.validDoctorDto();
        when(patientDao.existsById(2L)).thenReturn(true);
        when(patientDao.existsById(4L)).thenReturn(true);
        Doctor actual = mapper.dtoToEntity(expected);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getPatronymic(), actual.getPatronymic());
        assertEquals(expected.getBirthDate(), actual.getBirthDate());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertEquals(expected.getPatients()[0].getId(), actual.getPatients().get(0).getId());
        assertEquals(expected.getPatients()[1].getId(), actual.getPatients().get(1).getId());
    }

    @Test
    @DisplayName("(entityListToDtoList) passes Doctor list, checks returned list size & compare Dtos")
    void mappingEntityListToDtoListTest() {
        List<Doctor> doctorList = TestDataGenerator.validDoctorList();
        List<DoctorDto> returnDoctorDtoList = mapper.entityListToDtoList(doctorList);
        assertEquals(doctorList.size(), returnDoctorDtoList.size());
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor expected = doctorList.get(i);
            DoctorDto actual = returnDoctorDtoList.get(i);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getFirstName(), actual.getFirstName());
            assertEquals(expected.getLastName(), actual.getLastName());
            assertEquals(expected.getPatronymic(), actual.getPatronymic());
            assertEquals(expected.getBirthDate(), actual.getBirthDate());
            assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
            assertEquals(expected.getPosition(), actual.getPosition());
            for (int index = 0; index < expected.getPatients().size(); index++) {
                Patient expectedPatient = expected.getPatients().get(index);
                PatientDto actualPatient = actual.getPatients()[index];
                assertEquals(expectedPatient.getId(), actualPatient.getId());
                assertEquals(expectedPatient.getFirstName(), actualPatient.getFirstName());
                assertEquals(expectedPatient.getLastName(), actualPatient.getLastName());
                assertEquals(expectedPatient.getPatronymic(), actualPatient.getPatronymic());
                assertEquals(expectedPatient.getPhoneNumber(), actualPatient.getPhoneNumber());
            }
        }
    }

}
