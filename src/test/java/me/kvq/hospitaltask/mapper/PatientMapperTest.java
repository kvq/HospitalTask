package me.kvq.hospitaltask.mapper;

import me.kvq.hospitaltask.dto.PatientDto;
import me.kvq.hospitaltask.model.Patient;
import me.kvq.hospitaltask.testData.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PatientMapperTest {
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
    }

    @Test
    @DisplayName("(dtoToEntity) passes Id & PatientDto, compare returned Dto fields")
    void mappingDtoToEntityTest() {
        PatientDto expected = TestDataGenerator.validPatientDto();
        Patient actual = mapper.dtoToEntity(expected);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getPatronymic(), actual.getPatronymic());
        assertEquals(expected.getBirthDate(), actual.getBirthDate());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
    }

    @Test
    @DisplayName("(entityListToDtoList) passes Patient list, checks returned list size & compare Dtos")
    void mappingEntityListToDtoListTest() {
        List<Patient> patientList = TestDataGenerator.validPatientList();
        List<PatientDto> returnPatientDtoList = mapper.entityListToDtoList(patientList);
        assertEquals(patientList.size(), returnPatientDtoList.size());
        for (int firstIntex = 0; firstIntex < patientList.size(); firstIntex++) {
            Patient expected = patientList.get(firstIntex);
            PatientDto actual = returnPatientDtoList.get(firstIntex);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getFirstName(), actual.getFirstName());
            assertEquals(expected.getLastName(), actual.getLastName());
            assertEquals(expected.getPatronymic(), actual.getPatronymic());
            assertEquals(expected.getBirthDate(), actual.getBirthDate());
            assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
        }
    }

}
