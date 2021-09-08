package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dao.PatientDao;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.mapper.PatientMapper;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class PatientServiceTest {

    PatientDao patientDao;
    DoctorDao doctorDao;
    HashMap<Long, Patient> storage;
    Patient testPatientA, testPatientB;
    PatientService service;
    Doctor testDoctor;

    PatientMapper patientMapper;

    @BeforeEach
    void prepareData(){
        mockDoctor();
        patientDao = mock(PatientDao.class);
        storage = new HashMap<>();
        testPatientA = new Patient(1,"PatientA_Name","PatientA_LastName", "PatientA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789",testDoctor);
        testPatientB = new Patient(1,"PatientB_Name","PatientB_LastName", "PatientB_Patronymic",
                LocalDate.of(1990,2,15),
                "380123856789",testDoctor);
        storage.put(1L, testPatientA);
        storage.put(2L, testPatientB);
        when(patientDao.findAll()).thenAnswer(invocation -> getPatientList());
        when(patientDao.getById(anyLong()))
                .thenAnswer(invocation -> storage.get(invocation.getArgument(0,Long.class)));

        when(patientDao.existsById(anyLong()))
                .thenAnswer(invocation -> storage.containsKey(invocation.getArgument(0,Long.class)));

        doAnswer(invocation
                -> deletePatientById(invocation.getArgument(0,Long.class)))
                .when(patientDao).deleteById(anyLong());

        doAnswer(invocation
                -> savePatient(invocation.getArgument(0, Patient.class)))
                .when(patientDao).save(any(Patient.class));

        service = new PatientService(patientDao,patientMapper);
    }

    void mockDoctor(){
        testDoctor = new Doctor(1,"DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789","DoctorA_Position");
        DoctorDao mockDao = mock(DoctorDao.class);
        when(mockDao.getById(any())).thenReturn(testDoctor);

        patientMapper = new PatientMapper(mockDao);
    }


    boolean deletePatientById(long id) {
        return storage.remove(id) != null;
    }

    Patient savePatient(Patient patient){
        storage.put(patient.getId(),patient);
        return patient;
    }

    public List<Patient> getPatientList(){
        return new ArrayList<Patient>(storage.values());
    }

    @Test
    @Order(1)
    @DisplayName("Adding new Patient, expecting returned value not null and looking patient id in mocked dao")
    void serviceAddNewValidPatientTest(){
        PatientDto testingPatient = new PatientDto(0,"Name", "LastName", "Patronymic",
                LocalDate.of(1999,10,2),
                "380123456789", testDoctor.getId());
        PatientDto returnedDto = service.add(testingPatient);
        assertNotNull(returnedDto,"Dto returned by service is null");
        assertTrue(patientDao.existsById(returnedDto.getId()), "Patient not exists in mocked dao");
    }

    @Test
    @Order(2)
    @DisplayName("Updating existing patient with valid data, expecting valid return and looking patient changes in mocked dao")
    void serviceUpdateExistingPatientByIdWithValidDataTest(){
        PatientDto patient = patientMapper.entityToDto(testPatientA);
        patient.setFirstName("DifferentName");
        PatientDto returnedPatient = service.update(patient.getId(),patient);
        assertNotNull(returnedPatient, "Dto returned by service is null");
        assertEquals("DifferentName", patient.getFirstName());
        assertNotNull(patientDao.getById(patient.getId()), "Patient not exists in mocked dao");
        assertEquals("DifferentName", patientDao.getById(patient.getId()).getFirstName(), "Patient hasn't changed in mocked dao");
    }

    @Test
    @Order(3)
    @DisplayName("Deleting existing Patient, expecting ture to be returned, checking dao for size change")
    void serviceDeleteExistingPatientByIdTest(){
        Patient patient = testPatientA;
        assertTrue(service.delete(patient.getId()), "Patient wasn't deleted");
        assertEquals(1, patientDao.findAll().size(), "There should be only one patient in mocked dao after deletion");

    }

    @Test
    @Order(4)
    @DisplayName("Getting patient list, expecting 2 users in the list")
    void serviceGetPatientListTest(){
        List<PatientDto> patientDtoList = service.getList();
        assertEquals(2, patientDtoList.size(),"Expected 2 patients to be returned");
    }

    @Test
    @Order(5)
    @DisplayName("Getting patient by id. Comparing returned dto id to origin entity id")
    void serviceGetPatientByIdTest(){
        PatientDto patient = service.get(testPatientA.getId());
        assertNotNull(patient);
        assertEquals(testPatientA.getId(), patient.getId());
    }

}