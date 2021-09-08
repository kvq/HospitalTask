package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.mapper.DoctorMapper;
import me.kvq.HospitalTask.model.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
class DoctorServiceTest {

    DoctorDao doctorDao;
    HashMap<Long,Doctor> storage;
    Doctor testDoctorA, testDoctorB;
    DoctorService service;

    @Autowired
    DoctorMapper doctorMapper;

    @BeforeEach
    void prepareData(){
        doctorDao = mock(DoctorDao.class);
        storage = new HashMap<>();
        testDoctorA = new Doctor(1,"DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                                    LocalDate.of(1991,5,4),
                                    "380123455789","DoctorA_Position");
        testDoctorB = new Doctor(1,"DoctorB_Name","DoctorB_LastName", "DoctorB_Patronymic",
                                    LocalDate.of(1990,2,15),
                        "380123856789","DoctorB_Position");
        storage.put(1L, testDoctorA);
        storage.put(2L, testDoctorB);
        when(doctorDao.findAll()).thenAnswer(invocation -> getDoctorList());
        when(doctorDao.getById(anyLong()))
                .thenAnswer(invocation -> storage.get(invocation.getArgument(0,Long.class)));

        when(doctorDao.existsById(anyLong()))
                .thenAnswer(invocation -> storage.containsKey(invocation.getArgument(0,Long.class)));

        doAnswer(invocation
                -> deleteDoctorById(invocation.getArgument(0,Long.class)))
                .when(doctorDao).deleteById(anyLong());

        doAnswer(invocation
                -> saveDoctor(invocation.getArgument(0, Doctor.class)))
                .when(doctorDao).save(any(Doctor.class));

        service = new DoctorService(doctorDao,doctorMapper);
    }

    boolean deleteDoctorById(long id) {
       return storage.remove(id) != null;
    }

    Doctor saveDoctor(Doctor doctor){
        storage.put(doctor.getId(),doctor);
        return doctor;
    }

    public List<Doctor> getDoctorList(){
        return new ArrayList<Doctor>(storage.values());
    }

    @Test
    @Order(1)
    @DisplayName("Adding new Doctor, expecting returned value not null and looking doctor id in mocked dao")
    void serviceAddNewValidDoctorTest(){
        DoctorDto testingDoctor = new DoctorDto(0,"Name", "LastName", "Patronymic",
                LocalDate.of(1999,10,2),
                "380123456789", "Position");
        DoctorDto returnedDto = service.add(testingDoctor);
        assertNotNull(returnedDto,"Dto returned by service is null");
        assertTrue(doctorDao.existsById(returnedDto.getId()), "Doctor not exists in mocked dao");
    }

    @Test
    @Order(2)
    @DisplayName("Updating existing doctor with valid data, expecting valid return and checking updates in mocked dao")
    void serviceUpdateExistingDoctorByIdWithValidDataTest(){
        DoctorDto doctor = doctorMapper.entityToDto(testDoctorA);
        doctor.setFirstName("DifferentName");
        DoctorDto returnedDoctor = service.update(doctor.getId(),doctor);
        assertNotNull(returnedDoctor, "Dto returned by service is null");
        assertEquals("DifferentName", doctor.getFirstName());
        assertNotNull(this.doctorDao.getById(doctor.getId()), "Doctor not exists in mocked dao");
        assertEquals("DifferentName", this.doctorDao.getById(doctor.getId()).getFirstName(), "Doctor hasn't changed in mocked dao");
    }

    @Test
    @Order(3)
    @DisplayName("Deleting existing doctor, expecting ture to be returned, checking dao for size change")
    void serviceDeleteExistingDoctorByIdTest(){
        Doctor doctor = testDoctorA;
        assertTrue(service.delete(doctor.getId()), "Doctor wasn't deleted");
        assertEquals(1, this.doctorDao.findAll().size(), "There should be only one doctor in mocked dao after deletion");

    }

    @Test
    @Order(4)
    @DisplayName("Getting doctor list, expecting 2 users in the list")
    void serviceGetDoctorListTest(){
        List<DoctorDto> doctorsDtoList = service.getList();
        assertEquals(2, doctorsDtoList.size(),"Expected 2 doctors to be returned");
    }

    @Test
    @Order(5)
    @DisplayName("Getting doctor by id. Comparing returned dto id to origin entity id")
    void serviceGetDoctorByIdTest(){
        DoctorDto doctor = service.get(testDoctorA.getId());
        assertNotNull(doctor);
        assertEquals(testDoctorA.getId(), doctor.getId());
    }

}