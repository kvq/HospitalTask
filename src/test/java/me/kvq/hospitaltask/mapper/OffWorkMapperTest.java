package me.kvq.hospitaltask.mapper;

import me.kvq.hospitaltask.dto.DoctorDto;
import me.kvq.hospitaltask.dto.OffWorkDto;
import me.kvq.hospitaltask.model.Doctor;
import me.kvq.hospitaltask.model.OffWork;
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
class OffWorkMapperTest {
    @Autowired
    OffWorkMapper mapper;
    @MockBean
    DoctorMapper doctorMapper;

    @Test
    @DisplayName("Converts entity to dto then compares fields")
    void entityToDto() {
        OffWork expected = validOffWorkCurrent();
        DoctorDto expectedDoctor = validDoctorDto();
        when(doctorMapper.entityToDto(expected.getDoctor())).thenReturn(expectedDoctor);
        OffWorkDto actual = mapper.entityToDto(expected);
        assertEquals(expected.getDateFrom(), actual.getDateFrom());
        assertEquals(expected.getDateUntil(), actual.getDateUntil());
        assertEquals(expected.getReason(), actual.getReason());
        assertEquals(expectedDoctor, actual.getDoctor());
    }

    @Test
    @DisplayName("Converts dto to entity then compares fields")
    void dtoToEntity() {
        OffWorkDto expected = validOffWorkDtoCurrent();
        Doctor expectedDoctor = validDoctor();
        when(doctorMapper.dtoToEntity(expected.getDoctor())).thenReturn(expectedDoctor);
        OffWork actual = mapper.dtoToEntity(expected);
        assertEquals(expected.getDateFrom(), actual.getDateFrom());
        assertEquals(expected.getDateUntil(), actual.getDateUntil());
        assertEquals(expected.getReason(), actual.getReason());
        assertEquals(expectedDoctor, actual.getDoctor());
    }

    @Test
    @DisplayName("Converts entity list to dto list then compares fields for each in the list")
    void entityListToDtoList() {
        List<OffWork> expectedList = validOffWorkList();
        DoctorDto expectedDoctor = validDoctorDto();
        when(doctorMapper.entityToDto(any(Doctor.class))).thenReturn(expectedDoctor);
        List<OffWorkDto> actualList = mapper.entityListToDtoList(expectedList);
        assertEquals(expectedList.size(), actualList.size());
        for (int index = 0; index < expectedList.size(); index++) {
            OffWork expected = expectedList.get(index);
            OffWorkDto actual = actualList.get(index);
            assertEquals(expected.getDateFrom(), actual.getDateFrom());
            assertEquals(expected.getDateUntil(), actual.getDateUntil());
            assertEquals(expected.getReason(), actual.getReason());
            assertEquals(expectedDoctor, actual.getDoctor());
        }
    }

}