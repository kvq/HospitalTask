package me.kvq.HospitalTask.controller;

import me.kvq.HospitalTask.dto.DoctorDto;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DoctorController.class)
class DoctorsControllerTest {
    @MockBean
    DoctorService doctorService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Valid Json POST /doctor/add. Expects HTTP OK, checks if returned Json values are correct")
    void addDoctorJsonRequestResponseCheckTest() throws Exception {
        String doctorJson = "{\"firstName\":\"First_Name\","
                + "\"lastName\":\"Second_name\","
                + "\"patronymic\":\"Patronymic\","
                + "\"birthDate\":[2000,1,2],"
                + "\"phoneNumber\":\"381234567890\","
                + "\"position\":\"Position\"}";
        DoctorDto dto = new DoctorDto(1,
                "First_Name", "Second_name", "Patronymic",
                LocalDate.of(2000, 1, 2),
                "381234567890", "Position");

        when(doctorService.add(any(DoctorDto.class))).thenReturn(dto);
        mockMvc.perform(post("/doctor/add")
                        .content(doctorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("$.firstName").value("First_Name"))
                .andExpect(jsonPath("$.lastName").value("Second_name"))
                .andExpect(jsonPath("$.patronymic").value("Patronymic"))
                .andExpect(jsonPath("$.phoneNumber").value("381234567890"))
                .andExpect(jsonPath("$.birthDate[0]").value(2000))
                .andExpect(jsonPath("$.birthDate[1]").value(1))
                .andExpect(jsonPath("$.birthDate[2]").value(2))
                .andExpect(jsonPath("$.position").value("Position"));
        verify(doctorService, times(1)).add(any(DoctorDto.class));
    }

    @Test
    @DisplayName("Valid Json PATCH /doctor/edit. Expects HTTP OK, checks if returned Json values are correct")
    void patchDoctorJsonRequestResponseCheckTest() throws Exception {
        long id = 1;
        String doctorJson = "{\"firstName\":\"First_NewName\","
                + "\"lastName\":\"Second_NewName\","
                + "\"patronymic\":\"Patronymic\","
                + "\"birthDate\":[2001,2,3],"
                + "\"phoneNumber\":\"381234567891\","
                + "\"position\":\"Position2\"}";
        DoctorDto dto = new DoctorDto(id,
                "First_NewName", "Second_NewName", "Patronymic",
                LocalDate.of(2001, 2, 3),
                "381234567891", "Position2");

        when(doctorService.update(eq(id), any(DoctorDto.class))).thenReturn(dto);
        mockMvc.perform(patch("/doctor/edit/" + id)
                        .content(doctorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstName").value("First_NewName"))
                .andExpect(jsonPath("$.lastName").value("Second_NewName"))
                .andExpect(jsonPath("$.patronymic").value("Patronymic"))
                .andExpect(jsonPath("$.phoneNumber").value("381234567891"))
                .andExpect(jsonPath("$.birthDate[0]").value(2001))
                .andExpect(jsonPath("$.birthDate[1]").value(2))
                .andExpect(jsonPath("$.birthDate[2]").value(3))
                .andExpect(jsonPath("$.position").value("Position2"));
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
        DoctorDto testDoctorDto = new DoctorDto(1, "DoctorA_Name", "DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991, 5, 4),
                "380123455789", "DoctorA_Position");

        when(doctorService.getList()).thenReturn(Arrays.asList(testDoctorDto));
        mockMvc.perform(get("/doctor/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("DoctorA_Name"))
                .andExpect(jsonPath("$[0].lastName").value("DoctorA_LastName"))
                .andExpect(jsonPath("$[0].patronymic").value("DoctorA_Patronymic"))
                .andExpect(jsonPath("$[0].phoneNumber").value("380123455789"))
                .andExpect(jsonPath("$[0].birthDate[0]").value(1991))
                .andExpect(jsonPath("$[0].birthDate[1]").value(5))
                .andExpect(jsonPath("$[0].birthDate[2]").value(4))
                .andExpect(jsonPath("$[0].position").value("DoctorA_Position"));
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
