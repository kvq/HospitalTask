package me.kvq.HospitalTask.mapper;

import me.kvq.HospitalTask.dao.PatientDao;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.model.Doctor;
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
public class DoctorMapperTest {
    @MockBean
    PatientDao patientDao;
    @Autowired
    DoctorMapper mapper;

    @Test
    @DisplayName("(entityToDto) passes Doctor, compare returned Dto fields")
    void mappingEntityToDtoTest() {
        Doctor originDoctor = TestDataGenerator.validDoctor();
        DoctorDto returnedDoctorDto = mapper.entityToDto(originDoctor);
        assertEquals(originDoctor.getId(), returnedDoctorDto.getId());
        assertEquals(originDoctor.getFirstName(), returnedDoctorDto.getFirstName());
        assertEquals(originDoctor.getLastName(), returnedDoctorDto.getLastName());
        assertEquals(originDoctor.getPatronymic(), returnedDoctorDto.getPatronymic());
        assertEquals(originDoctor.getBirthDate(), returnedDoctorDto.getBirthDate());
        assertEquals(originDoctor.getPhoneNumber(), returnedDoctorDto.getPhoneNumber());
        assertEquals(originDoctor.getPosition(), returnedDoctorDto.getPosition());
        for (int index = 0; index < originDoctor.getPatients().size(); index++) {
            long expectedPatientId = originDoctor.getPatients().get(index).getId();
            long returnedPatientId = returnedDoctorDto.getPatients()[index];
            assertEquals(expectedPatientId, returnedPatientId);
        }
    }

    @Test
    @DisplayName("(dtoToEntity) passes Id & DoctorDto, compare returned Dto fields")
    void mappingDtoToEntityTest() {
        DoctorDto originDoctorDto = TestDataGenerator.validDoctorDto();
        when(patientDao.existsById(2L)).thenReturn(true);
        when(patientDao.existsById(4L)).thenReturn(true);
        Doctor returnedDoctor = mapper.dtoToEntity(1, originDoctorDto);
        assertEquals(originDoctorDto.getId(), returnedDoctor.getId());
        assertEquals(originDoctorDto.getFirstName(), returnedDoctor.getFirstName());
        assertEquals(originDoctorDto.getLastName(), returnedDoctor.getLastName());
        assertEquals(originDoctorDto.getPatronymic(), returnedDoctor.getPatronymic());
        assertEquals(originDoctorDto.getBirthDate(), returnedDoctor.getBirthDate());
        assertEquals(originDoctorDto.getPhoneNumber(), returnedDoctor.getPhoneNumber());
        assertEquals(originDoctorDto.getPosition(), returnedDoctor.getPosition());
        for (int index = 0; index < originDoctorDto.getPatients().length; index++) {
            long expectedPatientId = originDoctorDto.getPatients()[index];
            long returnedPatientId = returnedDoctor.getPatients().get(index).getId();
            assertEquals(expectedPatientId, returnedPatientId);
        }
        verify(patientDao, times(1)).existsById(2L);
        verify(patientDao, times(1)).existsById(4L);
    }

    @Test
    @DisplayName("(entityListToDtoList) passes Doctor list, checks returned list size & compare Dtos")
    void mappingEntityListToDtoListTest() {
        List<Doctor> doctorList = TestDataGenerator.validDoctorList();
        List<DoctorDto> returnDoctorDtoList = mapper.entityListToDtoList(doctorList);
        assertEquals(doctorList.size(), returnDoctorDtoList.size());
        for (int i = 0; i < doctorList.size(); i++) {
            Doctor originEntity = doctorList.get(i);
            DoctorDto returnedDto = returnDoctorDtoList.get(i);
            assertEquals(originEntity.getId(), returnedDto.getId());
            assertEquals(originEntity.getFirstName(), returnedDto.getFirstName());
            assertEquals(originEntity.getLastName(), returnedDto.getLastName());
            assertEquals(originEntity.getPatronymic(), returnedDto.getPatronymic());
            assertEquals(originEntity.getBirthDate(), returnedDto.getBirthDate());
            assertEquals(originEntity.getPhoneNumber(), returnedDto.getPhoneNumber());
            assertEquals(originEntity.getPosition(), returnedDto.getPosition());
            for (int patientIndex = 0; patientIndex < originEntity.getPatients().size(); patientIndex++) {
                long expectedPatientId = originEntity.getPatients().get(patientIndex).getId();
                long returnedPatientId = returnedDto.getPatients()[patientIndex];
                assertEquals(expectedPatientId, returnedPatientId);
            }
        }
    }

}
