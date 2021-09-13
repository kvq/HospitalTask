package me.kvq.HospitalTask.mapper;

import static org.junit.jupiter.api.Assertions.*;

import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.model.Doctor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class DoctorMapperTest {
    @Autowired
    DoctorMapper mapper;

    @Test
    @DisplayName("(entityToDto) passes Doctor, compare returned Dto fields")
    void mappingEntityToDtoTest(){
        Doctor testDoctor = new Doctor(1,
                "DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789", "DoctorA_Position");

        DoctorDto returnDoctorDto = mapper.entityToDto(testDoctor);
        assertEquals(returnDoctorDto.getId(),testDoctor.getId());
        assertEquals(returnDoctorDto.getFirstName(),testDoctor.getFirstName());
        assertEquals(returnDoctorDto.getLastName(),testDoctor.getLastName());
        assertEquals(returnDoctorDto.getPatronymic(),testDoctor.getPatronymic());
        assertEquals(returnDoctorDto.getBirthDate(),testDoctor.getBirthDate());
        assertEquals(returnDoctorDto.getPhoneNumber(),testDoctor.getPhoneNumber());
        assertEquals(returnDoctorDto.getPosition(),testDoctor.getPosition());
    }

    @Test
    @DisplayName("(dtoToEntity) passes Id & DoctorDto, compare returned Dto fields")
    void mappingDtoToEntityTest(){
        DoctorDto testDoctorDto = new DoctorDto(1,
                "DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789", "DoctorA_Position");

        Doctor returnDoctor = mapper.dtoToEntity(1,testDoctorDto);
        assertEquals(returnDoctor.getId(),testDoctorDto.getId());
        assertEquals(returnDoctor.getFirstName(),testDoctorDto.getFirstName());
        assertEquals(returnDoctor.getLastName(),testDoctorDto.getLastName());
        assertEquals(returnDoctor.getPatronymic(),testDoctorDto.getPatronymic());
        assertEquals(returnDoctor.getBirthDate(),testDoctorDto.getBirthDate());
        assertEquals(returnDoctor.getPhoneNumber(),testDoctorDto.getPhoneNumber());
        assertEquals(returnDoctor.getPosition(),testDoctorDto.getPosition());
    }

    @Test
    @DisplayName("(entityListToDtoList) passes Doctor list, checks returned list size & compare Dtos")
    void mappingEntityListToDtoListTest(){
        Doctor testDoctorA = new Doctor(1,
                "DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789", "DoctorA_Position");
        Doctor testDoctorB = new Doctor(1,
                "DoctorB_Name","DoctorB_LastName", "DoctorB_Patronymic",
                LocalDate.of(1994,2,1),
                "380123455780", "DoctorB_Position");
        List<Doctor> doctorList = Arrays.asList(testDoctorA,testDoctorB);

        List<DoctorDto> returnDoctorDtoList = mapper.entityListToDtoList(doctorList);
        assertEquals(doctorList.size(),returnDoctorDtoList.size());
        for (int i = 0; i < doctorList.size(); i++){
            Doctor originEntity = doctorList.get(i);
            DoctorDto returnedDto = returnDoctorDtoList.get(i);
            assertEquals(originEntity.getId(), returnedDto.getId());
            assertEquals(originEntity.getFirstName(), returnedDto.getFirstName());
            assertEquals(originEntity.getLastName(),returnedDto.getLastName());
            assertEquals(originEntity.getPatronymic(),returnedDto.getPatronymic());
            assertEquals(originEntity.getBirthDate(),returnedDto.getBirthDate());
            assertEquals(originEntity.getPhoneNumber(),returnedDto.getPhoneNumber());
            assertEquals(originEntity.getPosition(),returnedDto.getPosition());
        }
    }

}
