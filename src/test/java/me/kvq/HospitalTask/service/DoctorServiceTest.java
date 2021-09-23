package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.mapper.DoctorMapper;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.testData.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class DoctorServiceTest {
    @MockBean
    DoctorMapper mapper;
    @MockBean
    DoctorDao doctorDao;
    @Autowired
    DoctorService service;

    @Test
    @DisplayName("(add) Pass Valid Dto, then compares return Dto to passed")
    void addNewValidDoctorTest() {
        TestDataGenerator.TestData<Doctor, DoctorDto> testData = TestDataGenerator.getValidDoctorData();
        DoctorDto expectedDoctorDto = testData.getDto();
        Doctor doctor = testData.getEntity();
        when(mapper.dtoToEntity(0, expectedDoctorDto)).thenReturn(doctor);
        when(mapper.entityToDto(doctor)).thenReturn(expectedDoctorDto);
        when(doctorDao.save(doctor)).thenReturn(doctor);

        DoctorDto returnedDoctorDto = service.add(expectedDoctorDto);
        assertEquals(expectedDoctorDto.getId(), returnedDoctorDto.getId());
        assertEquals(expectedDoctorDto.getFirstName(), returnedDoctorDto.getFirstName());
        assertEquals(expectedDoctorDto.getLastName(), returnedDoctorDto.getLastName());
        assertEquals(expectedDoctorDto.getPatronymic(), returnedDoctorDto.getPatronymic());
        assertEquals(expectedDoctorDto.getBirthDate(), returnedDoctorDto.getBirthDate());
        assertEquals(expectedDoctorDto.getPhoneNumber(), returnedDoctorDto.getPhoneNumber());
        assertEquals(expectedDoctorDto.getPosition(), returnedDoctorDto.getPosition());
        assertArrayEquals(expectedDoctorDto.getPatients(), returnedDoctorDto.getPatients());
        verify(doctorDao, times(1)).save(doctor);
        verify(mapper, times(1)).dtoToEntity(0, expectedDoctorDto);
        verify(mapper, times(1)).entityToDto(doctor);
    }

    @Test
    @DisplayName("(update) Pass existing Id & Valid Dto, then compares return Dto to passed")
    void updateExistingDoctorByIdWithValidDataTest() {
        TestDataGenerator.TestData<Doctor, DoctorDto> testData = TestDataGenerator.getValidDoctorData();
        DoctorDto expectedDoctorDto = testData.getDto();
        Doctor doctor = testData.getEntity();
        long id = testData.getId();
        when(mapper.dtoToEntity(id, expectedDoctorDto)).thenReturn(doctor);
        when(mapper.entityToDto(doctor)).thenReturn(expectedDoctorDto);
        when(doctorDao.save(doctor)).thenReturn(doctor);
        when(doctorDao.existsById(id)).thenReturn(true);

        DoctorDto returnedDoctorDto = service.update(id, expectedDoctorDto);
        assertEquals(expectedDoctorDto.getId(), returnedDoctorDto.getId());
        assertEquals(expectedDoctorDto.getFirstName(), returnedDoctorDto.getFirstName());
        assertEquals(expectedDoctorDto.getLastName(), returnedDoctorDto.getLastName());
        assertEquals(expectedDoctorDto.getPatronymic(), returnedDoctorDto.getPatronymic());
        assertEquals(expectedDoctorDto.getBirthDate(), returnedDoctorDto.getBirthDate());
        assertEquals(expectedDoctorDto.getPhoneNumber(), returnedDoctorDto.getPhoneNumber());
        assertEquals(expectedDoctorDto.getPosition(), returnedDoctorDto.getPosition());
        assertArrayEquals(expectedDoctorDto.getPatients(), returnedDoctorDto.getPatients());
        verify(doctorDao, times(1)).existsById(expectedDoctorDto.getId());
        verify(doctorDao, times(1)).save(doctor);
        verify(mapper, times(1)).dtoToEntity(id, expectedDoctorDto);
        verify(mapper, times(1)).entityToDto(doctor);
    }

    @Test
    @DisplayName("(delete) Pass existing Id, expected to return true")
    void deleteExistingDoctorByIdTest() {
        long testId = 1;
        when(doctorDao.existsById(1L)).thenReturn(true);
        assertTrue(service.delete(testId));
        verify(doctorDao, times(1)).existsById(testId);
        verify(doctorDao, times(1)).deleteById(testId);
    }

    @Test
    @DisplayName("(getList) expected to return 2 Dtos, then compare fields to origin entities")
    void getDoctorListTest() {
        List<Doctor> testDoctorList = TestDataGenerator.validDoctorList();
        List<DoctorDto> expectedDoctorDtoList = TestDataGenerator.validDoctorDtoList();
        when(doctorDao.findAll()).thenReturn(testDoctorList);
        when(mapper.entityListToDtoList(testDoctorList)).thenReturn(expectedDoctorDtoList);

        List<DoctorDto> doctorReturnDtoList = service.getList();
        assertEquals(expectedDoctorDtoList.size(), doctorReturnDtoList.size());
        for (int index = 0; index < expectedDoctorDtoList.size(); index++) {
            DoctorDto returnedDoctorDto = doctorReturnDtoList.get(index);
            DoctorDto expectedDoctorDto = expectedDoctorDtoList.get(index);
            assertEquals(expectedDoctorDto.getId(), returnedDoctorDto.getId());
            assertEquals(expectedDoctorDto.getFirstName(), returnedDoctorDto.getFirstName());
            assertEquals(expectedDoctorDto.getLastName(), returnedDoctorDto.getLastName());
            assertEquals(expectedDoctorDto.getPatronymic(), returnedDoctorDto.getPatronymic());
            assertEquals(expectedDoctorDto.getBirthDate(), returnedDoctorDto.getBirthDate());
            assertEquals(expectedDoctorDto.getPhoneNumber(), returnedDoctorDto.getPhoneNumber());
            assertEquals(expectedDoctorDto.getPosition(), returnedDoctorDto.getPosition());
            assertArrayEquals(expectedDoctorDto.getPatients(), returnedDoctorDto.getPatients());
        }
        verify(doctorDao, times(1)).findAll();
        verify(mapper, times(1)).entityListToDtoList(testDoctorList);
    }

    @Test
    @DisplayName("(get) Pass Id, expected to return Dto, fields compared to origin entity")
    void getDoctorByIdTest() {
        TestDataGenerator.TestData<Doctor, DoctorDto> testData = TestDataGenerator.getValidDoctorData();
        Doctor daoDoctor = testData.getEntity();
        DoctorDto expectedDoctorDto = testData.getDto();
        long id = testData.getId();
        when(doctorDao.getById(id)).thenReturn(daoDoctor);
        when(mapper.entityToDto(daoDoctor)).thenReturn(expectedDoctorDto);

        DoctorDto returnedDoctorDto = service.get(expectedDoctorDto.getId());
        assertEquals(expectedDoctorDto.getId(), returnedDoctorDto.getId());
        assertEquals(expectedDoctorDto.getFirstName(), returnedDoctorDto.getFirstName());
        assertEquals(expectedDoctorDto.getLastName(), returnedDoctorDto.getLastName());
        assertEquals(expectedDoctorDto.getPatronymic(), returnedDoctorDto.getPatronymic());
        assertEquals(expectedDoctorDto.getBirthDate(), returnedDoctorDto.getBirthDate());
        assertEquals(expectedDoctorDto.getPhoneNumber(), returnedDoctorDto.getPhoneNumber());
        assertEquals(expectedDoctorDto.getPosition(), returnedDoctorDto.getPosition());
        assertArrayEquals(expectedDoctorDto.getPatients(), returnedDoctorDto.getPatients());
        verify(doctorDao, times(1)).getById(id);
        verify(mapper, times(1)).entityToDto(daoDoctor);
    }

    @Test
    @DisplayName("(get) Pass non existing Id, expected exception")
    void getNonExistingDoctorByIdTest() {
        long nonExistingId = 1L;
        when(doctorDao.getById(nonExistingId)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> {
            service.get(nonExistingId);
        });
        verify(doctorDao, times(1)).getById(nonExistingId);
    }

    @Test
    @DisplayName("(delete) Pass non existing Id, expected exception")
    void deleteNonExistingDoctorByIdTest() {
        long nonExistingId = 1L;
        when(doctorDao.existsById(nonExistingId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
        verify(doctorDao, times(1)).existsById(nonExistingId);
    }

    @Test
    @DisplayName("(update) Pass non existing Id, expected exception")
    void updateNonExistingDoctorByIdTest() {
        long nonExistingId = 1L;
        when(doctorDao.existsById(nonExistingId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> {
            service.update(nonExistingId, null);
        });
        verify(doctorDao, times(1)).existsById(nonExistingId);
    }

}
