package me.kvq.HospitalTask.controller;

import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.exception.InvalidDtoException;
import me.kvq.HospitalTask.exception.InvalidPhoneNumberException;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.service.DoctorService;
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
import static org.mockito.ArgumentMatchers.any;
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
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    @DisplayName("Add new valid doctor, then compare json fields")
    void addDoctorJsonRequestResponseCheckTest() throws Exception {
        String doctorJson = validDoctorJson();
        DoctorDto expectedDto = validDoctorDto();
        long id = expectedDto.getId();
        when(doctorService.add(any(DoctorDto.class))).thenReturn(expectedDto);

        mockMvc.perform(post("/doctor/add")
                        .content(doctorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(matchDoctorDto("$", expectedDto))
                .andExpect(matchPatientDto("$.patients[0]", expectedDto.getPatients()[0]))
                .andExpect(matchPatientDto("$.patients[1]", expectedDto.getPatients()[1]));

        verify(doctorService, times(1)).add(any(DoctorDto.class));
    }

    @Test
    @DisplayName("Update doctor with valid data, then compare json fields")
    void patchDoctorJsonRequestResponseCheckTest() throws Exception {
        String doctorJson = validDoctorJson();
        DoctorDto expectedDto = validDoctorDto();
        when(doctorService.update(any(DoctorDto.class))).thenReturn(expectedDto);

        ResultActions actions = mockMvc.perform(patch("/doctor/edit")
                        .content(doctorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(matchDoctorDto("$", expectedDto))
                .andExpect(matchPatientDto("$.patients[0]", expectedDto.getPatients()[0]))
                .andExpect(matchPatientDto("$.patients[1]", expectedDto.getPatients()[1]));
        verify(doctorService, times(1)).update(any(DoctorDto.class));
    }

    @Test
    @DisplayName("Delete existing doctor, expected HTTP OK")
    void deleteDoctorByIdResponseCheckTest() throws Exception {
        long id = 1;
        mockMvc.perform(delete("/doctor/delete/" + id))
                .andExpect(status().isOk());
        verify(doctorService, times(1)).delete(id);
    }

    @Test
    @DisplayName("Get list of all doctors and compare json fields")
    void getListOfDoctorsResponseCheckTest() throws Exception {
        List<DoctorDto> expectedDtoList = validDoctorDtoList();
        when(doctorService.getList()).thenReturn(expectedDtoList);

        ResultActions actions = mockMvc.perform(get("/doctor/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(matchDoctorDto("$[0]", expectedDtoList.get(0)))
                .andExpect(matchDoctorDto("$[1]", expectedDtoList.get(1)));
        verify(doctorService, times(1)).getList();
    }

    public static Stream<Arguments> getExceptions() {
        return Stream.of(
                Arguments.of(new InvalidDtoException("Dto is invalid")),
                Arguments.of(new NotFoundException("No doctor found by that id")));
    }

    @ParameterizedTest
    @DisplayName("Deletion of invalid doctor, bad request expected")
    @MethodSource("getExceptions")
    void deleteDoctorExceptionTest(RuntimeException exception) throws Exception {
        long invalidId = 1L;
        doThrow(exception).when(doctorService).delete(invalidId);
        mockMvc.perform(delete("/doctor/delete/" + invalidId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(exception.getMessage()));
        verify(doctorService, times(1)).delete(invalidId);
    }

    @ParameterizedTest
    @DisplayName("Doctor updated with invalid data, bad request expected")
    @MethodSource("getExceptions")
    void updateDoctorExceptionTest(RuntimeException exception) throws Exception {
        String emptyJson = "{}";
        when(doctorService.update(any(DoctorDto.class))).thenThrow(exception);
        mockMvc.perform(patch("/doctor/edit")
                        .content(emptyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(exception.getMessage()));
        verify(doctorService, times(1)).update(any(DoctorDto.class));
    }

    @Test
    @DisplayName("Adding invalid doctor, bad request expected")
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
