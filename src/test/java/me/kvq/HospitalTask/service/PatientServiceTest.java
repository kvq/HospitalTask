package me.kvq.HospitalTask.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dao.PatientDao;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.mapper.PatientMapper;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.model.Patient;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class PatientServiceTest {
    @Mock
    PatientDao patientDao;
    @Mock
    DoctorDao doctorDao;
    PatientService service;

    @BeforeEach
    void prepareData(){
        PatientMapper patientMapper = new PatientMapper(doctorDao);
        service = new PatientService(patientDao,patientMapper);
    }

    @Test
    @DisplayName("(add) Pass Valid Dto, then compare return Dto to passed, and checks id not same")
    void serviceAddNewValidPatientTest(){
        Doctor testDoctor = new Doctor(3,"DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789","DoctorA_Position");
        PatientDto testingPatient = new PatientDto(0,"Name", "LastName", "Patronymic",
                LocalDate.of(1999,10,2),
                "380123456789", testDoctor.getId());

        when(doctorDao.getById(3L)).thenReturn(testDoctor);
        when(patientDao.save(any(Patient.class))).thenAnswer(invocation -> {
            Patient patient = invocation.getArgument(0,Patient.class);
            patient.setId(1);
            return patient;
        });

        PatientDto returnedDto = service.add(testingPatient);
        assertNotNull(returnedDto,"Dto returned by service is null");
        assertNotEquals(testingPatient.getId(),returnedDto.getId());
        assertEquals(testingPatient.getFirstName(),returnedDto.getFirstName());
        assertEquals(testingPatient.getLastName(),returnedDto.getLastName());
        assertEquals(testingPatient.getPatronymic(),returnedDto.getPatronymic());
        assertEquals(testingPatient.getBirthDate(),returnedDto.getBirthDate());
        assertEquals(testingPatient.getPhoneNumber(),returnedDto.getPhoneNumber());
        assertEquals(testingPatient.getDoctor(),testDoctor.getId());
    }

    @Test
    @DisplayName("(update) Pass Id & Dto, then compare return Dto to passed")
    void serviceUpdateExistingPatientByIdWithValidDataTest(){
        Doctor testDoctor = new Doctor(3,"DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789","DoctorA_Position");
        PatientDto testPatient = new PatientDto(1,"PatientA_Name","PatientA_LastName", "PatientA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789",testDoctor.getId());

        when(doctorDao.getById(3L)).thenReturn(testDoctor);
        when(patientDao.existsById(1L)).thenReturn(true);
        when(patientDao.save(any(Patient.class))).thenAnswer(invocation -> {
            return invocation.getArgument(0,Patient.class);
        });

        PatientDto returnedPatient = service.update(testPatient.getId(),testPatient);
        assertNotNull(returnedPatient, "Dto returned by service is null");
        assertEquals(testPatient.getId(),returnedPatient.getId());
        assertEquals(testPatient.getFirstName(),returnedPatient.getFirstName());
        assertEquals(testPatient.getLastName(),returnedPatient.getLastName());
        assertEquals(testPatient.getPatronymic(),returnedPatient.getPatronymic());
        assertEquals(testPatient.getBirthDate(),returnedPatient.getBirthDate());
        assertEquals(testPatient.getPhoneNumber(),returnedPatient.getPhoneNumber());
        assertEquals(testPatient.getDoctor(),returnedPatient.getDoctor());
    }

    @Test
    @DisplayName("(delete) Pass Id, expected to return true")
    void serviceDeleteExistingPatientByIdTest(){
        Patient testPatient = new Patient(1,
                "PatientA_Name","PatientA_LastName", "PatientA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789",null);
        doNothing().when(patientDao).deleteById(1L);
        when(patientDao.existsById(1L)).thenReturn(true);

        assertTrue(service.delete(testPatient.getId()), "Patient wasn't deleted");
    }

    @Test
    @DisplayName("(getList) expected to return 2 Dtos, then compare fields to origin entities")
    void serviceGetPatientListTest(){
        Doctor testDoctor = new Doctor(3,
                "DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789","DoctorA_Position");
        Patient testPatientA = new Patient(1,
                "PatientA_Name","PatientA_LastName", "PatientA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789",testDoctor);
        Patient testPatientB = new Patient(2,
                "PatientB_Name","PatientB_LastName", "PatientB_Patronymic",
                LocalDate.of(1990,2,15),
                "380123856789",testDoctor);
        List<Patient> testPatientList = Arrays.asList(testPatientA,testPatientB);

        when(doctorDao.getById(3L)).thenReturn(testDoctor);
        when(patientDao.findAll()).thenReturn(testPatientList);

        List<PatientDto> returnedPatientDtoList = service.getList();
        assertEquals(2, returnedPatientDtoList.size(),"Expected 2 patients to be returned");

        for (int i = 0; i < returnedPatientDtoList.size(); i++){
            PatientDto returnedPatientDto = returnedPatientDtoList.get(i);
            Patient testPatient = testPatientList.get(i);
            assertNotNull(returnedPatientDto);
            assertEquals(testPatient.getId(),returnedPatientDto.getId());
            assertEquals(testPatient.getFirstName(),returnedPatientDto.getFirstName());
            assertEquals(testPatient.getLastName(),returnedPatientDto.getLastName());
            assertEquals(testPatient.getPatronymic(),returnedPatientDto.getPatronymic());
            assertEquals(testPatient.getBirthDate(),returnedPatientDto.getBirthDate());
            assertEquals(testPatient.getPhoneNumber(),returnedPatientDto.getPhoneNumber());
            assertEquals(testPatient.getDoctor().getId(),returnedPatientDto.getDoctor());
        }
    }

    @Test
    @DisplayName("(get) Pass Id, expected to return Dto, fields compared to origin entity")
    void serviceGetPatientByIdTest(){
        Doctor testDoctor = new Doctor(3,
                "DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789","DoctorA_Position");
        Patient testPatient = new Patient(1,
                "PatientA_Name","PatientA_LastName", "PatientA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789",testDoctor);

        when(doctorDao.getById(3L)).thenReturn(testDoctor);
        when(patientDao.getById(1L)).thenReturn(testPatient);

        PatientDto returnedPatient = service.get(testPatient.getId());
        assertNotNull(returnedPatient);
        assertEquals(testPatient.getId(),returnedPatient.getId());
        assertEquals(testPatient.getFirstName(),returnedPatient.getFirstName());
        assertEquals(testPatient.getLastName(),returnedPatient.getLastName());
        assertEquals(testPatient.getPatronymic(),returnedPatient.getPatronymic());
        assertEquals(testPatient.getBirthDate(),returnedPatient.getBirthDate());
        assertEquals(testPatient.getPhoneNumber(),returnedPatient.getPhoneNumber());
        assertEquals(testPatient.getDoctor().getId(),returnedPatient.getDoctor());
    }

}