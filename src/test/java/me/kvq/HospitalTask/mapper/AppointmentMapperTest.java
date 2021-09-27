package me.kvq.HospitalTask.mapper;

import me.kvq.HospitalTask.dto.AppointmentDto;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.dto.PatientDto;
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
    DoctorMapper doctorMapper;
    @MockBean
    PatientMapper patientMapper;
    @Autowired
    AppointmentMapper mapper;

    @Test
    @DisplayName("Mapping dto to entity, compare fields")
    void dtoToEntityTest() {
        AppointmentDto expected = TestDataGenerator.validAppointmentDto();
        Patient expectedPatient = TestDataGenerator.validPatient();
        Doctor expectedDoctor = TestDataGenerator.validDoctor();
        when(doctorMapper.dtoToEntity(expected.getDoctor())).thenReturn(expectedDoctor);
        when(patientMapper.dtoToEntity(expected.getPatient())).thenReturn(expectedPatient);

        Appointment actual = mapper.dtoToEntity(expected);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expectedDoctor, actual.getDoctor());
        assertEquals(expectedPatient, actual.getPatient());
        assertEquals(expected.getDateTime(), actual.getDateTime());
        verify(doctorMapper, times(1)).dtoToEntity(expected.getDoctor());
        verify(patientMapper, times(1)).dtoToEntity(expected.getPatient());
    }

    @Test
    @DisplayName("Mapping entity to dto, compare fields")
    void entityToDtoTest() {
        Appointment expected = TestDataGenerator.validAppointment();
        PatientDto expectedPatient = TestDataGenerator.validPatientDto();
        DoctorDto expectedDoctor = TestDataGenerator.validDoctorDto();
        when(doctorMapper.entityToDto(expected.getDoctor())).thenReturn(expectedDoctor);
        when(patientMapper.entityToDto(expected.getPatient())).thenReturn(expectedPatient);

        AppointmentDto actual = mapper.entityToDto(expected);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expectedDoctor, actual.getDoctor());
        assertEquals(expectedPatient, actual.getPatient());
        assertEquals(expected.getDateTime(), actual.getDateTime());
    }

    @Test
    @DisplayName("Mapping entity list to dto list, compare fields")
    void entityListToDtoTest() {
        List<Appointment> list = TestDataGenerator.getAppointmentsList();
        DoctorDto expectedDoctor = TestDataGenerator.validDoctorDto();
        PatientDto expectedPatient = TestDataGenerator.validPatientDto();
        when(doctorMapper.entityToDto(any(Doctor.class))).thenReturn(expectedDoctor);
        when(patientMapper.entityToDto(any(Patient.class))).thenReturn(expectedPatient);

        List<AppointmentDto> returnedList = mapper.entityListToDtoList(list);
        for (int index = 0; index < list.size(); index++) {
            Appointment expected = list.get(index);
            AppointmentDto actual = returnedList.get(index);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getPatient().getId(), actual.getPatient().getId());
            assertEquals(expected.getDoctor().getId(), actual.getDoctor().getId());
            assertEquals(expected.getDateTime(), actual.getDateTime());
        }
    }

}
