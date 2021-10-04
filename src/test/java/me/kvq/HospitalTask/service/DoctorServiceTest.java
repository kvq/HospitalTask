package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.mapper.DoctorMapper;
import me.kvq.HospitalTask.model.Doctor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static me.kvq.HospitalTask.testData.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @DisplayName("Add valid doctor, expected to return Dto with same fields")
    void addNewValidDoctorTest() {
        DoctorDto expectedDoctorDto = validDoctorDto();
        expectedDoctorDto.setId(0);
        Doctor doctor = validDoctor();
        when(mapper.dtoToEntity(expectedDoctorDto)).thenReturn(doctor);
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
        verify(doctorDao, times(1)).save(doctor);
        verify(mapper, times(1)).dtoToEntity(expectedDoctorDto);
        verify(mapper, times(1)).entityToDto(doctor);
    }

    @Test
    @DisplayName("Update doctor with valid data, expected to return Dto with same fields")
    void updateExistingDoctorByIdWithValidDataTest() {
        DoctorDto expectedDoctorDto = validDoctorDto();
        Doctor doctor = validDoctor();
        long id = expectedDoctorDto.getId();
        when(mapper.dtoToEntity(expectedDoctorDto)).thenReturn(doctor);
        when(mapper.entityToDto(doctor)).thenReturn(expectedDoctorDto);
        when(doctorDao.save(doctor)).thenReturn(doctor);
        when(doctorDao.existsById(id)).thenReturn(true);

        DoctorDto returnedDoctorDto = service.update(expectedDoctorDto);
        assertEquals(expectedDoctorDto.getId(), returnedDoctorDto.getId());
        assertEquals(expectedDoctorDto.getFirstName(), returnedDoctorDto.getFirstName());
        assertEquals(expectedDoctorDto.getLastName(), returnedDoctorDto.getLastName());
        assertEquals(expectedDoctorDto.getPatronymic(), returnedDoctorDto.getPatronymic());
        assertEquals(expectedDoctorDto.getBirthDate(), returnedDoctorDto.getBirthDate());
        assertEquals(expectedDoctorDto.getPhoneNumber(), returnedDoctorDto.getPhoneNumber());
        assertEquals(expectedDoctorDto.getPosition(), returnedDoctorDto.getPosition());
        verify(doctorDao, times(1)).existsById(expectedDoctorDto.getId());
        verify(doctorDao, times(1)).save(doctor);
        verify(mapper, times(1)).dtoToEntity(expectedDoctorDto);
        verify(mapper, times(1)).entityToDto(doctor);
    }

    @Test
    @DisplayName("Delete existing doctor, no exception expected")
    void deleteExistingDoctorByIdTest() {
        long testId = 1;
        when(doctorDao.existsById(1L)).thenReturn(true);
        service.delete(testId);
        verify(doctorDao, times(1)).existsById(testId);
        verify(doctorDao, times(1)).deleteById(testId);
    }

    @Test
    @DisplayName("Find list of all doctors, compare Dto fields")
    void getDoctorListTest() {
        List<Doctor> testDoctorList = validDoctorList();
        List<DoctorDto> expectedDoctorDtoList = validDoctorDtoList();
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
        }
        verify(doctorDao, times(1)).findAll();
        verify(mapper, times(1)).entityListToDtoList(testDoctorList);
    }

    @Test
    @DisplayName("Delete doctor by invalid id, exception expected")
    void deleteNonExistingDoctorByIdTest() {
        long nonExistingId = 1L;
        when(doctorDao.existsById(nonExistingId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
        verify(doctorDao, times(1)).existsById(nonExistingId);
    }

    @Test
    @DisplayName("Update doctor by invalid id, exception expected")
    void updateNonExistingDoctorTest() {
        DoctorDto dto = validDoctorDto();
        long nonExistingId = dto.getId();
        when(doctorDao.existsById(nonExistingId)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> {
            service.update(dto);
        });
        verify(doctorDao, times(1)).existsById(nonExistingId);
    }

}
