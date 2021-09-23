package me.kvq.HospitalTask.mapper;

import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dao.PatientDao;
import me.kvq.HospitalTask.dto.AppointmentDto;
import me.kvq.HospitalTask.model.Appointment;
import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.model.Patient;
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
class AppointmentMapperTest {
    @MockBean
    DoctorDao doctorDao;
    @MockBean
    PatientDao patientDao;
    @Autowired
    AppointmentMapper mapper;

    @Test
    @DisplayName("(dtoToEntity) passes AppointmentDto, compare returned Dto fields")
    void dtoToEntityTest() {
        AppointmentDto expected = TestDataGenerator.validAppointmentDto();
        Patient patient = TestDataGenerator.validPatient();
        Doctor doctor = TestDataGenerator.validDoctor();
        when(doctorDao.getById(expected.getDoctorId())).thenReturn(doctor);
        when(patientDao.getById(expected.getPatientId())).thenReturn(patient);

        Appointment returned = mapper.dtoToEntity(expected.getId(), expected);
        assertEquals(expected.getId(), returned.getId());
        assertEquals(expected.getDoctorId(), returned.getDoctor().getId());
        assertEquals(expected.getPatientId(), returned.getPatient().getId());
        assertEquals(expected.getTime(), returned.getTime());
        verify(doctorDao, times(1)).getById(doctor.getId());
        verify(patientDao, times(1)).getById(patient.getId());
    }

    @Test
    @DisplayName("(entityToDto) passes Appointment, compare returned Dto fields")
    void entityToDtoTest() {
        Appointment expected = TestDataGenerator.validAppointment();
        AppointmentDto returned = mapper.entityToDto(expected);
        assertEquals(expected.getId(), returned.getId());
        assertEquals(expected.getDoctor().getId(), returned.getDoctorId());
        assertEquals(expected.getPatient().getId(), returned.getPatientId());
        assertEquals(expected.getTime(), returned.getTime());
    }

    @Test
    @DisplayName("(entityListToDtoList) passes Appointment list, checks returned list size & compare Dtos")
    void entityListToDtoTest() {
        List<Appointment> list = TestDataGenerator.getAppointmentsList();
        List<AppointmentDto> returnedList = mapper.entityListToDtoList(list);
        for (int index = 0; index < list.size(); index++) {
            Appointment expected = list.get(index);
            AppointmentDto returned = returnedList.get(index);
            assertEquals(expected.getId(), returned.getId());
            assertEquals(expected.getPatient().getId(), returned.getPatientId());
            assertEquals(expected.getDoctor().getId(), returned.getDoctorId());
            assertEquals(expected.getTime(), returned.getTime());
        }
    }

}
