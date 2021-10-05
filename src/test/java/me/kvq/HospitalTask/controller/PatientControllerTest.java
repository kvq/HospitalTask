package me.kvq.HospitalTask.controller;

import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.exception.InvalidPhoneNumberException;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.security.SecurityUserService;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static me.kvq.HospitalTask.testData.TestDataGenerator.*;
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
    MockMvc mockMvc;
    @MockBean(name = "securityService")
    SecurityUserService securityUserService;

    @Test
    @WithMockUser(authorities = "CREATE_PATIENT")
    @DisplayName("Add new valid Patient, then compare json fields")
    void addPatientAndCheckResponseTest() throws Exception {
        String patientJson = validPatientJson();
        PatientDto expectedDto = validPatientDto();
        long id = expectedDto.getId();
        when(patientService.add(any(PatientDto.class))).thenReturn(expectedDto);

        mockMvc.perform(post("/patient/add")
                        .content(patientJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(matchPatientDto("$", expectedDto));
        verify(patientService, times(1)).add(any(PatientDto.class));
    }

    @Test
    @DisplayName("Add new valid Patient as unauthorized, expected forbidden status")
    @WithMockUser()
    void addPatientAsUnauthorizedTest() throws Exception {
        String patientJson = validPatientJson();
        mockMvc.perform(post("/patient/add")
                        .content(patientJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Update Patient with valid data, then compare json fields")
    @WithMockUser(authorities = "UPDATE_PATIENT")
    void updatePatientAndCheckResponseTest() throws Exception {
        String patientJson = validPatientJson();
        PatientDto expectedDto = validPatientDto();
        when(patientService.update(any(PatientDto.class))).thenReturn(expectedDto);

        mockMvc.perform(patch("/patient/edit")
                        .content(patientJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(matchPatientDto("$", expectedDto));
        verify(patientService, times(1)).update(any(PatientDto.class));
    }

    @Test
    @DisplayName("Update Patient with valid data as authorized user, expected ok status")
    @WithMockUser(authorities = "UPDATE_SELF")
    void updatePatientAsAuthorizedTest() throws Exception {
        String patientJson = validPatientJson();
        PatientDto dto = validPatientDto();
        when(patientService.update(any(PatientDto.class))).thenReturn(dto);
        when(securityUserService.ownsAccount(any(User.class), eq(dto.getId()))).thenReturn(true);
        mockMvc.perform(patch("/patient/edit")
                        .content(patientJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(patientService, times(1)).update(any(PatientDto.class));
        verify(securityUserService, times(1)).ownsAccount(any(User.class), eq(dto.getId()));
    }

    @Test
    @DisplayName("Update Patient with valid data as unauthorized, expected forbidden status")
    @WithMockUser(authorities = "UPDATE_SELF")
    void updatePatientAsUnauthorizedTest() throws Exception {
        String patientJson = validPatientJson();
        mockMvc.perform(patch("/patient/edit")
                        .content(patientJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Delete existing Patient, expected HTTP OK")
    @WithMockUser(authorities = "DELETE_PATIENT")
    void deletePatientByIdResponseCheckTest() throws Exception {
        long testPatientId = 1;
        mockMvc.perform(delete("/patient/delete/" + testPatientId))
                .andExpect(status().isOk());
        verify(patientService, times(1)).delete(testPatientId);
    }

    @Test
    @DisplayName("Delete existing Patient as authorized user, expected ok status")
    @WithMockUser(authorities = "DELETE_SELF")
    void deletePatientByIdAsAuthorizedTest() throws Exception {
        long testPatientId = 1;
        when(securityUserService.ownsAccount(any(User.class), eq(testPatientId))).thenReturn(true);
        mockMvc.perform(delete("/patient/delete/" + testPatientId))
                .andExpect(status().isOk());
        verify(patientService, times(1)).delete(testPatientId);
        verify(securityUserService, times(1)).ownsAccount(any(User.class), eq(testPatientId));
    }

    @Test
    @DisplayName("Delete existing Patient as unauthorized, expected forbidden status")
    @WithMockUser()
    void deletePatientByIdAsUnauthorizedTest() throws Exception {
        long testPatientId = 1;
        mockMvc.perform(delete("/patient/delete/" + testPatientId))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Get list of all patients and compare json fields")
    @WithMockUser(authorities = "SEE_ALL_PATIENTS")
    void getListOfPatientsResponseCheckTest() throws Exception {
        List<PatientDto> expectedDtoList = validPatientDtoList();
        when(patientService.getList()).thenReturn(expectedDtoList);

        mockMvc.perform(get("/patient/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(matchPatientDto("$[0]", expectedDtoList.get(0)))
                .andExpect(matchPatientDto("$[1]", expectedDtoList.get(1)));
        verify(patientService, times(1)).getList();
    }

    @Test
    @DisplayName("Get list of all patients as unauthorized, expected forbidden status")
    @WithMockUser()
    void getListOfPatientsAsUnauthorizedTest() throws Exception {
        mockMvc.perform(get("/patient/list"))
                .andExpect(status().isForbidden());
    }

    public static Stream<Arguments> getExceptions() {
        return Stream.of(
                Arguments.of(new InvalidPhoneNumberException("12345")),
                Arguments.of(new NotFoundException("No patient found by that id")));
    }

    @ParameterizedTest
    @DisplayName("Deletion of invalid Patient, bad request expected")
    @MethodSource("getExceptions")
    @WithMockUser(authorities = "DELETE_PATIENT")
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
    @WithMockUser(authorities = "UPDATE_PATIENT")
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
    @WithMockUser(authorities = "CREATE_PATIENT")
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
