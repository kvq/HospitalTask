package me.kvq.HospitalTask.controller;

import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.exception.InvalidPhoneNumberException;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.service.PatientService;
import me.kvq.HospitalTask.testData.TestDataGenerator;
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

import java.util.List;
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
        TestDataGenerator.TestData<?, PatientDto> testData = TestDataGenerator.getValidPatientData();
        String patientJson = testData.getJson();
        PatientDto expectedDto = testData.getDto();
        long id = testData.getId();
        when(patientService.add(any(PatientDto.class))).thenReturn(expectedDto);

        ResultActions actions = mockMvc.perform(post("/patient/add")
                        .content(patientJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstName").value(expectedDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expectedDto.getLastName()))
                .andExpect(jsonPath("$.patronymic").value(expectedDto.getPatronymic()))
                .andExpect(jsonPath("$.phoneNumber").value(expectedDto.getPhoneNumber()))
                .andExpect(jsonPath("$.birthDate[0]").value(expectedDto.getBirthDate().getYear()))
                .andExpect(jsonPath("$.birthDate[1]").value(expectedDto.getBirthDate().getMonthValue()))
                .andExpect(jsonPath("$.birthDate[2]").value(expectedDto.getBirthDate().getDayOfYear()));
        for (int doctorIndex = 0; doctorIndex < expectedDto.getDoctors().length; doctorIndex++) {
            actions.andExpect(jsonPath("$.doctors[" + doctorIndex + "]")
                    .value(expectedDto.getDoctors()[doctorIndex]));
        }
        verify(patientService, times(1)).add(any(PatientDto.class));
    }

    @Test
    @DisplayName("Valid Json PATCH /patient/edit. Expects HTTP OK, checks if returned Json values are correct")
    void patchPatientJsonRequestResponseCheckTest() throws Exception {
        TestDataGenerator.TestData<?, PatientDto> testData = TestDataGenerator.getValidPatientData();
        String patientJson = testData.getJson();
        PatientDto expectedDto = testData.getDto();
        long id = testData.getId();
        when(patientService.update(eq(id), any(PatientDto.class))).thenReturn(expectedDto);

        ResultActions actions = mockMvc.perform(patch("/patient/edit/" + id)
                        .content(patientJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstName").value(expectedDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expectedDto.getLastName()))
                .andExpect(jsonPath("$.patronymic").value(expectedDto.getPatronymic()))
                .andExpect(jsonPath("$.phoneNumber").value(expectedDto.getPhoneNumber()))
                .andExpect(jsonPath("$.birthDate[0]").value(expectedDto.getBirthDate().getYear()))
                .andExpect(jsonPath("$.birthDate[1]").value(expectedDto.getBirthDate().getMonthValue()))
                .andExpect(jsonPath("$.birthDate[2]").value(expectedDto.getBirthDate().getDayOfYear()));
        for (int doctorIndex = 0; doctorIndex < expectedDto.getDoctors().length; doctorIndex++) {
            actions.andExpect(jsonPath("$.doctors[" + doctorIndex + "]")
                    .value(expectedDto.getDoctors()[doctorIndex]));
        }
        verify(patientService, times(1)).update(eq(id), any(PatientDto.class));
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
        List<PatientDto> expectedDtoList = TestDataGenerator.validPatientDtoList();
        when(patientService.getList()).thenReturn(expectedDtoList);

        ResultActions actions = mockMvc.perform(get("/patient/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        for (int index = 0; index < expectedDtoList.size(); index++) {
            PatientDto expectedDto = expectedDtoList.get(index);
            actions.andExpect(jsonPath("$[" + index + "].id").value(expectedDto.getId()))
                    .andExpect(jsonPath("$[" + index + "].firstName").value(expectedDto.getFirstName()))
                    .andExpect(jsonPath("$[" + index + "].lastName").value(expectedDto.getLastName()))
                    .andExpect(jsonPath("$[" + index + "].patronymic").value(expectedDto.getPatronymic()))
                    .andExpect(jsonPath("$[" + index + "].phoneNumber").value(expectedDto.getPhoneNumber()))
                    .andExpect(jsonPath("$[" + index + "].birthDate[0]").value(expectedDto.getBirthDate().getYear()))
                    .andExpect(jsonPath("$[" + index + "].birthDate[1]").value(expectedDto.getBirthDate().getMonthValue()))
                    .andExpect(jsonPath("$[" + index + "].birthDate[2]").value(expectedDto.getBirthDate().getDayOfMonth()));
            for (int doctorIndex = 0; doctorIndex < expectedDto.getDoctors().length; doctorIndex++) {
                actions.andExpect(jsonPath("$[" + index + "].doctors[" + doctorIndex + "]")
                        .value(expectedDto.getDoctors()[doctorIndex]));
            }
        }
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
