package me.kvq.HospitalTask.controller;

import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.exception.InvalidPhoneNumberException;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.service.DoctorService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DoctorController.class)
class DoctorControllerTest {
    @MockBean
    DoctorService doctorService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Valid Json POST /doctor/add. Expects HTTP OK, checks if returned Json values are correct")
    void addDoctorJsonRequestResponseCheckTest() throws Exception {
        TestDataGenerator.TestData<?, DoctorDto> testData = TestDataGenerator.getValidDoctorData();
        String doctorJson = testData.getJson();
        DoctorDto expectedDto = testData.getDto();
        long id = testData.getId();
        when(doctorService.add(any(DoctorDto.class))).thenReturn(expectedDto);

        ResultActions actions = mockMvc.perform(post("/doctor/add")
                        .content(doctorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstName").value(expectedDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expectedDto.getLastName()))
                .andExpect(jsonPath("$.patronymic").value(expectedDto.getPatronymic()))
                .andExpect(jsonPath("$.phoneNumber").value(expectedDto.getPhoneNumber()))
                .andExpect(jsonPath("$.birthDate[0]").value(expectedDto.getBirthDate().getYear()))
                .andExpect(jsonPath("$.birthDate[1]").value(expectedDto.getBirthDate().getMonthValue()))
                .andExpect(jsonPath("$.birthDate[2]").value(expectedDto.getBirthDate().getDayOfMonth()))
                .andExpect(jsonPath("$.position").value(expectedDto.getPosition()));
        for (int patientIndex = 0; patientIndex < expectedDto.getPatients().length; patientIndex++) {
            actions.andExpect(jsonPath("$.patients[" + patientIndex + "]")
                    .value(expectedDto.getPatients()[patientIndex]));
        }
        verify(doctorService, times(1)).add(any(DoctorDto.class));
    }

    @Test
    @DisplayName("Valid Json PATCH /doctor/edit. Expects HTTP OK, checks if returned Json values are correct")
    void patchDoctorJsonRequestResponseCheckTest() throws Exception {
        TestDataGenerator.TestData<?, DoctorDto> testData = TestDataGenerator.getValidDoctorData();
        String doctorJson = testData.getJson();
        DoctorDto expectedDto = testData.getDto();
        long id = testData.getId();
        when(doctorService.update(eq(id), any(DoctorDto.class))).thenReturn(expectedDto);

        ResultActions actions = mockMvc.perform(patch("/doctor/edit/" + id)
                        .content(doctorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstName").value(expectedDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expectedDto.getLastName()))
                .andExpect(jsonPath("$.patronymic").value(expectedDto.getPatronymic()))
                .andExpect(jsonPath("$.phoneNumber").value(expectedDto.getPhoneNumber()))
                .andExpect(jsonPath("$.birthDate[0]").value(expectedDto.getBirthDate().getYear()))
                .andExpect(jsonPath("$.birthDate[1]").value(expectedDto.getBirthDate().getMonthValue()))
                .andExpect(jsonPath("$.birthDate[2]").value(expectedDto.getBirthDate().getDayOfMonth()))
                .andExpect(jsonPath("$.position").value(expectedDto.getPosition()));
        for (int patientIndex = 0; patientIndex < expectedDto.getPatients().length; patientIndex++) {
            actions.andExpect(jsonPath("$.patients[" + patientIndex + "]")
                    .value(expectedDto.getPatients()[patientIndex]));
        }
        verify(doctorService, times(1)).update(eq(id), any(DoctorDto.class));
    }

    @Test
    @DisplayName("Request DELETE /doctor/delete. Expects HTTP OK")
    void deleteDoctorByIdResponseCheckTest() throws Exception {
        long id = 1;
        when(doctorService.delete(id)).thenReturn(true);
        mockMvc.perform(delete("/doctor/delete/" + id))
                .andExpect(status().isOk());
        verify(doctorService, times(1)).delete(id);
    }

    @Test
    @DisplayName("Request GET /doctor/list. Expects HTTP OK and checking Json list values")
    void getListOfDoctorsResponseCheckTest() throws Exception {
        List<DoctorDto> expectedDtoList = TestDataGenerator.validDoctorDtoList();
        when(doctorService.getList()).thenReturn(expectedDtoList);

        ResultActions actions = mockMvc.perform(get("/doctor/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        for (int index = 0; index < expectedDtoList.size(); index++) {
            DoctorDto expectedDto = expectedDtoList.get(index);
            actions.andExpect(jsonPath("$[" + index + "].id").value(expectedDto.getId()))
                    .andExpect(jsonPath("$[" + index + "].firstName").value(expectedDto.getFirstName()))
                    .andExpect(jsonPath("$[" + index + "].lastName").value(expectedDto.getLastName()))
                    .andExpect(jsonPath("$[" + index + "].patronymic").value(expectedDto.getPatronymic()))
                    .andExpect(jsonPath("$[" + index + "].phoneNumber").value(expectedDto.getPhoneNumber()))
                    .andExpect(jsonPath("$[" + index + "].birthDate[0]").value(expectedDto.getBirthDate().getYear()))
                    .andExpect(jsonPath("$[" + index + "].birthDate[1]").value(expectedDto.getBirthDate().getMonthValue()))
                    .andExpect(jsonPath("$[" + index + "].birthDate[2]").value(expectedDto.getBirthDate().getDayOfMonth()))
                    .andExpect(jsonPath("$[" + index + "].position").value(expectedDto.getPosition()));
            for (int patientIndex = 0; patientIndex < expectedDto.getPatients().length; patientIndex++) {
                actions.andExpect(jsonPath("$[" + index + "].patients[" + patientIndex + "]")
                        .value(expectedDto.getPatients()[patientIndex]));
            }
        }
        verify(doctorService, times(1)).getList();
    }

    public static Stream<Arguments> getExceptions() {
        return Stream.of(
                Arguments.of(new InvalidPhoneNumberException("12345")),
                Arguments.of(new NotFoundException("No doctor found by that id")));
    }

    @ParameterizedTest
    @DisplayName("Invalid DELETE /doctor/delete. Expects HTTP 400, and valid error message")
    @MethodSource("getExceptions")
    void deleteDoctorExceptionTest(RuntimeException exception) throws Exception {
        long invalidId = 1L;
        when(doctorService.delete(invalidId)).thenThrow(exception);
        mockMvc.perform(delete("/doctor/delete/" + invalidId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(exception.getMessage()));
        verify(doctorService, times(1)).delete(invalidId);
    }

    @ParameterizedTest
    @DisplayName("Invalid PATCH /doctor/edit. Expects HTTP 400, and valid error message")
    @MethodSource("getExceptions")
    void updateDoctorExceptionTest(RuntimeException exception) throws Exception {
        long invalidId = 1L;
        String emptyJson = "{}";
        when(doctorService.update(eq(invalidId), any(DoctorDto.class))).thenThrow(exception);
        mockMvc.perform(patch("/doctor/edit/" + invalidId)
                        .content(emptyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(exception.getMessage()));
        verify(doctorService, times(1)).update(eq(invalidId), any(DoctorDto.class));
    }

    @Test
    @DisplayName("Invalid POST /doctor/edit. Expects HTTP 400, and valid error message")
    void addDoctorExceptionTest() throws Exception {
        InvalidPhoneNumberException exception = new InvalidPhoneNumberException("12345");
        String emptyJson = "{}";
        when(doctorService.add(any(DoctorDto.class))).thenThrow(exception);
        mockMvc.perform(post("/doctor/add/")
                        .content(emptyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(exception.getMessage()));
        verify(doctorService, times(1)).add(any(DoctorDto.class));
    }

}
