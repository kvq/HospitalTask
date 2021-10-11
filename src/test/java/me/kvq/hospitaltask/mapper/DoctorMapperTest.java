package me.kvq.hospitaltask.mapper;

import me.kvq.hospitaltask.dto.DoctorDto;
import me.kvq.hospitaltask.model.Doctor;
import me.kvq.hospitaltask.testData.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DoctorMapperTest {
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
    }

    @Test
    @DisplayName("(dtoToEntity) passes Id & DoctorDto, compare returned Dto fields")
    void mappingDtoToEntityTest() {
        DoctorDto expected = TestDataGenerator.validDoctorDto();
        Doctor actual = mapper.dtoToEntity(expected);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getPatronymic(), actual.getPatronymic());
        assertEquals(expected.getBirthDate(), actual.getBirthDate());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

    @Test
    @DisplayName("(entityListToDtoList) passes Doctor list, checks returned list size & compare Dtos")
    void mappingEntityListToDtoListTest() {
        List<Doctor> doctorList = TestDataGenerator.validDoctorList();
        List<DoctorDto> returnDoctorDtoList = mapper.entityListToDtoList(doctorList);
        assertEquals(doctorList.size(), returnDoctorDtoList.size());
        for (int firstIndex = 0; firstIndex < doctorList.size(); firstIndex++) {
            Doctor expected = doctorList.get(firstIndex);
            DoctorDto actual = returnDoctorDtoList.get(firstIndex);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getFirstName(), actual.getFirstName());
            assertEquals(expected.getLastName(), actual.getLastName());
            assertEquals(expected.getPatronymic(), actual.getPatronymic());
            assertEquals(expected.getBirthDate(), actual.getBirthDate());
            assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
            assertEquals(expected.getPosition(), actual.getPosition());
        }
    }

}
