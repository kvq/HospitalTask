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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Stream;

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

    @Test
    @DisplayName("Valid Json POST /patient/add. Expects HTTP OK, checks if returned Json values are correct")
    void addPatientJsonRequestResponseCheckTest() throws Exception {
        long testDoctorId = 3;
        String patientJson = "{\"firstName\":\"First_Name\","
                + "\"lastName\":\"Second_name\","
                + "\"patronymic\":\"Patronymic\","
                + "\"birthDate\":[2001,2,3],"
                + "\"phoneNumber\":\"381234567890\","
                + "\"doctor\":" + testDoctorId + "}";
        PatientDto dto = new PatientDto(1,
                "First_Name", "Second_name", "Patronymic", LocalDate.of(2001, 2, 3),
                "381234567890", testDoctorId);

        when(patientService.add(any(PatientDto.class))).thenReturn(dto);
        mockMvc.perform(post("/patient/add")
                        .content(patientJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("First_Name"))
                .andExpect(jsonPath("$.lastName").value("Second_name"))
                .andExpect(jsonPath("$.patronymic").value("Patronymic"))
                .andExpect(jsonPath("$.phoneNumber").value("381234567890"))
                .andExpect(jsonPath("$.birthDate[0]").value(2001))
                .andExpect(jsonPath("$.birthDate[1]").value(2))
                .andExpect(jsonPath("$.birthDate[2]").value(3))
                .andExpect(jsonPath("$.doctor").value(testDoctorId));
        verify(patientService, times(1)).add(any(PatientDto.class));
    }

    @Test
    @DisplayName("Valid Json PATCH /patient/edit. Expects HTTP OK, checks if returned Json values are correct")
    void patchPatientJsonRequestResponseCheckTest() throws Exception {
        long testDoctorId = 3;
        long testPatientId = 1;
        String patientJson = "{\"firstName\":\"Different_Name\","
                + "\"lastName\":\"Second_name\","
                + "\"patronymic\":\"Patronymic\","
                + "\"birthDate\":[2000,1,2],"
                + "\"phoneNumber\":\"381234567890\","
                + "\"doctor\":" + testDoctorId + "}";
        PatientDto dto = new PatientDto(testPatientId,
                "Different_Name", "Second_name", "Patronymic", LocalDate.of(2000, 1, 2),
                "381234567890", testDoctorId);

        when(patientService.update(eq(testPatientId), any(PatientDto.class))).thenReturn(dto);
        mockMvc.perform(patch("/patient/edit/" + testPatientId)
                        .content(patientJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testPatientId))
                .andExpect(jsonPath("$.firstName").value("Different_Name"))
                .andExpect(jsonPath("$.lastName").value("Second_name"))
                .andExpect(jsonPath("$.patronymic").value("Patronymic"))
                .andExpect(jsonPath("$.phoneNumber").value("381234567890"))
                .andExpect(jsonPath("$.birthDate[0]").value(2000))
                .andExpect(jsonPath("$.birthDate[1]").value(1))
                .andExpect(jsonPath("$.birthDate[2]").value(2))
                .andExpect(jsonPath("$.doctor").value(3));
        verify(patientService, times(1)).update(eq(testPatientId), any(PatientDto.class));
    }

    @Test
    @DisplayName("Request DELETE /patient/delete. Expects HTTP OK")
    void deletePatientByIdResponseCheckTest() throws Exception {
        long testPatientId = 1;
        when(patientService.delete(testPatientId)).thenReturn(true);
        mockMvc.perform(delete("/patient/delete/" + testPatientId))
                .andExpect(status().isOk());
        verify(patientService, times(1)).delete(testPatientId);
    }

    @Test
    @DisplayName("Request GET /patient/list. Expects HTTP OK and checking Json list values")
    void getListOfPatientsResponseCheckTest() throws Exception {
        long testDoctorId = 3;
        PatientDto testPatientDto = new PatientDto(1, "PatientA_Name", "PatientA_LastName", "PatientA_Patronymic",
                LocalDate.of(1991, 5, 4),
                "380123455789", testDoctorId);

        when(patientService.getList()).thenReturn(Arrays.asList(testPatientDto));
        mockMvc.perform(get("/patient/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("PatientA_Name"))
                .andExpect(jsonPath("$[0].lastName").value("PatientA_LastName"))
                .andExpect(jsonPath("$[0].patronymic").value("PatientA_Patronymic"))
                .andExpect(jsonPath("$[0].phoneNumber").value("380123455789"))
                .andExpect(jsonPath("$[0].birthDate[0]").value(1991))
                .andExpect(jsonPath("$[0].birthDate[1]").value(5))
                .andExpect(jsonPath("$[0].birthDate[2]").value(4))
                .andExpect(jsonPath("$[0].doctor").value(testDoctorId));
        verify(patientService, times(1)).getList();
    }

    public static Stream<Arguments> getExceptions() {
        return Stream.of(
                Arguments.of(new InvalidPhoneNumberException("12345")),
                Arguments.of(new NotFoundException("No patient found by that id")));
    }

    @ParameterizedTest
    @DisplayName("Invalid DELETE /patient/delete. Expects HTTP 400, and valid error message")
    @MethodSource("getExceptions")
    void deletePatientExceptionTest(RuntimeException exception) throws Exception {
        long invalidId = 1L;
        when(patientService.delete(invalidId)).thenThrow(exception);
        mockMvc.perform(delete("/patient/delete/" + invalidId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(exception.getMessage()));
        verify(patientService, times(1)).delete(invalidId);
    }

    @ParameterizedTest
    @DisplayName("Invalid PATCH /patient/edit. Expects HTTP 400, and valid error message")
    @MethodSource("getExceptions")
    void updatePatientExceptionTest(RuntimeException exception) throws Exception {
        long invalidId = 1L;
        String emptyJson = "{}";
        when(patientService.update(eq(invalidId), any(PatientDto.class))).thenThrow(exception);
        mockMvc.perform(patch("/patient/edit/" + invalidId)
                        .content(emptyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(exception.getMessage()));
        verify(patientService, times(1)).update(eq(invalidId), any(PatientDto.class));
    }

    @Test
    @DisplayName("Invalid POST /patient/add. Expects HTTP 400, and valid error message")
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
