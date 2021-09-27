package me.kvq.HospitalTask.controller;

import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.exception.InvalidPhoneNumberException;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.service.PatientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import static me.kvq.HospitalTask.testData.TestDataGenerator.*;
import static me.kvq.HospitalTask.testData.TestMatchers.matchDoctorDto;
import static me.kvq.HospitalTask.testData.TestMatchers.matchPatientDto;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
class PatientControllerTest {
    @MockBean
    PatientService patientService;
    @Autowired
    private MockMvc mockMvc;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    @DisplayName("Add new valid Patient, then compare json fields")
    void addPatientJsonRequestResponseCheckTest() throws Exception {
        String patientJson = validPatientJson();
        PatientDto expectedDto = validPatientDto();
        long id = expectedDto.getId();
        when(patientService.add(any(PatientDto.class))).thenReturn(expectedDto);

        ResultActions actions = mockMvc.perform(post("/patient/add")
                        .content(patientJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(matchPatientDto("$", expectedDto))
                .andExpect(matchDoctorDto("$.doctors[0]", expectedDto.getDoctors()[0]))
                .andExpect(matchDoctorDto("$.doctors[1]", expectedDto.getDoctors()[1]));
        verify(patientService, times(1)).add(any(PatientDto.class));
    }

    @Test
    @DisplayName("Update Patient with valid data, then compare json fields")
    void patchPatientJsonRequestResponseCheckTest() throws Exception {
        String patientJson = validPatientJson();
        PatientDto expectedDto = validPatientDto();
        when(patientService.update(any(PatientDto.class))).thenReturn(expectedDto);

        ResultActions actions = mockMvc.perform(patch("/patient/edit")
                        .content(patientJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(matchPatientDto("$", expectedDto))
                .andExpect(matchDoctorDto("$.doctors[0]", expectedDto.getDoctors()[0]))
                .andExpect(matchDoctorDto("$.doctors[1]", expectedDto.getDoctors()[1]));
        verify(patientService, times(1)).update(any(PatientDto.class));
    }

    @Test
    @DisplayName("Delete existing Patient, expected HTTP OK")
    void deletePatientByIdResponseCheckTest() throws Exception {
        long testPatientId = 1;
        mockMvc.perform(delete("/patient/delete/" + testPatientId))
                .andExpect(status().isOk());
        verify(patientService, times(1)).delete(testPatientId);
    }

    @Test
    @DisplayName("Get list of all patients and compare json fields")
    void getListOfPatientsResponseCheckTest() throws Exception {
        List<PatientDto> expectedDtoList = validPatientDtoList();
        when(patientService.getList()).thenReturn(expectedDtoList);

        ResultActions actions = mockMvc.perform(get("/patient/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(matchPatientDto("$[0]", expectedDtoList.get(0)))
                .andExpect(matchPatientDto("$[1]", expectedDtoList.get(1)));
        verify(patientService, times(1)).getList();
    }

    public static Stream<Arguments> getExceptions() {
        return Stream.of(
                Arguments.of(new InvalidPhoneNumberException("12345")),
                Arguments.of(new NotFoundException("No patient found by that id")));
    }

    @ParameterizedTest
    @DisplayName("Deletion of invalid Patient, bad request expected")
    @MethodSource("getExceptions")
    void deletePatientExceptionTest(RuntimeException exception) throws Exception {
        long invalidId = 1L;
        doThrow(exception).when(patientService).delete(invalidId);
        mockMvc.perform(delete("/patient/delete/" + invalidId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(exception.getMessage()));
        verify(patientService, times(1)).delete(invalidId);
    }

    @ParameterizedTest
    @DisplayName("Doctor updated with invalid data, bad request expected")
    @MethodSource("getExceptions")
    void updatePatientExceptionTest(RuntimeException exception) throws Exception {
        String emptyJson = "{}";
        when(patientService.update(any(PatientDto.class))).thenThrow(exception);
        mockMvc.perform(patch("/patient/edit")
                        .content(emptyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(exception.getMessage()));
        verify(patientService, times(1)).update(any(PatientDto.class));
    }

    @Test
    @DisplayName("Adding invalid Patient, bad request expected")
    void addPatientExceptionTest() throws Exception {
        InvalidPhoneNumberException exception = new InvalidPhoneNumberException("12345");
        String emptyJson = "{}";
        when(patientService.add(any(PatientDto.class))).thenThrow(exception);
        mockMvc.perform(post("/patient/add/")
                        .content(emptyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(exception.getMessage()));
        verify(patientService, times(1)).add(any(PatientDto.class));
    }

}
