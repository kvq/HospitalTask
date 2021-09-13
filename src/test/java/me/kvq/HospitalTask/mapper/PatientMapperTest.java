package me.kvq.HospitalTask.mapper;

import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.model.Patient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
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
    void mappingEntityToDtoTest(){
        Doctor testDoctor = new Doctor(3,
                "DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789", "DoctorA_Position");
        Patient testPatient = new Patient(1,
                "PatientA_Name","PatientA_LastName", "PatientA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789", testDoctor);

        PatientDto returnPatientDto = mapper.entityToDto(testPatient);
        assertEquals(returnPatientDto.getId(),testPatient.getId());
        assertEquals(returnPatientDto.getFirstName(),testPatient.getFirstName());
        assertEquals(returnPatientDto.getLastName(),testPatient.getLastName());
        assertEquals(returnPatientDto.getPatronymic(),testPatient.getPatronymic());
        assertEquals(returnPatientDto.getBirthDate(),testPatient.getBirthDate());
        assertEquals(returnPatientDto.getPhoneNumber(),testPatient.getPhoneNumber());
        assertEquals(returnPatientDto.getDoctor(),testDoctor.getId());
    }

    @Test
    @DisplayName("(dtoToEntity) passes Id & PatientDto, compare returned Dto fields")
    void mappingDtoToEntityTest(){
        Doctor testDoctor = new Doctor(3,
                "DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789", "DoctorA_Position");
        PatientDto testPatientDto = new PatientDto(1,
                "PatientA_Name","PatientA_LastName", "PatientA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789", 3);

        when(doctorDao.getById(3L)).thenReturn(testDoctor);

        Patient returnPatient = mapper.dtoToEntity(1,testPatientDto);
        assertEquals(returnPatient.getId(),testPatientDto.getId());
        assertEquals(returnPatient.getFirstName(),testPatientDto.getFirstName());
        assertEquals(returnPatient.getLastName(),testPatientDto.getLastName());
        assertEquals(returnPatient.getPatronymic(),testPatientDto.getPatronymic());
        assertEquals(returnPatient.getBirthDate(),testPatientDto.getBirthDate());
        assertEquals(returnPatient.getPhoneNumber(),testPatientDto.getPhoneNumber());
        assertEquals(returnPatient.getDoctor(),testDoctor);
    }

    @Test
    @DisplayName("(entityListToDtoList) passes Patient list, checks returned list size & compare Dtos")
    void mappingEntityListToDtoListTest(){
        Doctor testDoctorA = new Doctor(3,
                "DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789", "DoctorA_Position");
        Patient testPatientA = new Patient(1,
                "PatientA_Name","PatientA_LastName", "PatientA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789", testDoctorA);
        Patient testPatientB = new Patient(2,
                "PatientA_Name","PatientA_LastName", "PatientA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789", testDoctorA);
        List<Patient> patientList = Arrays.asList(testPatientA,testPatientB);

        List<PatientDto> returnPatientDtoList = mapper.entityListToDtoList(patientList);
        assertEquals(patientList.size(),returnPatientDtoList.size());
        for (int i = 0; i < patientList.size(); i++){
            Patient originEntity = patientList.get(i);
            PatientDto returnedDto = returnPatientDtoList.get(i);
            assertEquals(originEntity.getId(), returnedDto.getId());
            assertEquals(originEntity.getFirstName(), returnedDto.getFirstName());
            assertEquals(originEntity.getLastName(),returnedDto.getLastName());
            assertEquals(originEntity.getPatronymic(),returnedDto.getPatronymic());
            assertEquals(originEntity.getBirthDate(),returnedDto.getBirthDate());
            assertEquals(originEntity.getPhoneNumber(),returnedDto.getPhoneNumber());
            assertEquals(testDoctorA.getId(),returnedDto.getDoctor());
        }
    }

}
