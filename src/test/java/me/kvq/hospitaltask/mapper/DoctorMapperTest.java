package me.kvq.hospitaltask.mapper;

import me.kvq.hospitaltask.dto.DoctorDto;
import me.kvq.hospitaltask.dto.TariffDto;
import me.kvq.hospitaltask.model.Doctor;
import me.kvq.hospitaltask.model.Tariff;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static me.kvq.hospitaltask.testData.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class DoctorMapperTest {
    @MockBean
    TariffMapper tariffMapper;
    @Autowired
    DoctorMapper mapper;

    @Test
    @DisplayName("(entityToDto) passes Doctor, compare returned Dto fields")
    void mappingEntityToDtoTest() {
        Doctor expected = validDoctor();
        TariffDto expectedTariff = validTariffDto();
        when(tariffMapper.entityToDto(expected.getTariff())).thenReturn(expectedTariff);
        DoctorDto actual = mapper.entityToDto(expected);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getPatronymic(), actual.getPatronymic());
        assertEquals(expected.getBirthDate(), actual.getBirthDate());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertEquals(expectedTariff, actual.getTariff());
    }

    @Test
    @DisplayName("(dtoToEntity) passes Id & DoctorDto, compare returned Dto fields")
    void mappingDtoToEntityTest() {
        DoctorDto expected = validDoctorDto();
        Tariff expectedTariff = validTariff();
        when(tariffMapper.dtoToEntity(expected.getTariff())).thenReturn(expectedTariff);
        Doctor actual = mapper.dtoToEntity(expected);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getPatronymic(), actual.getPatronymic());
        assertEquals(expected.getBirthDate(), actual.getBirthDate());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertEquals(expectedTariff, actual.getTariff());
    }

    @Test
    @DisplayName("(entityListToDtoList) passes Doctor list, checks returned list size & compare Dtos")
    void mappingEntityListToDtoListTest() {
        List<Doctor> doctorList = validDoctorList();
        TariffDto expectedTariff = validTariffDto();
        when(tariffMapper.entityToDto(any(Tariff.class))).thenReturn(expectedTariff);
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
            assertEquals(expectedTariff, actual.getTariff());
        }
    }

}
