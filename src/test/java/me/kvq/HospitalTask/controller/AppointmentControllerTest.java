package me.kvq.HospitalTask.controller;

import me.kvq.HospitalTask.dto.AppointmentDto;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.service.AppointmentService;
import me.kvq.HospitalTask.testData.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppointmentController.class)
class AppointmentControllerTest {
    @MockBean
    AppointmentService service;
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Valid Json POST /appointment/add. Expects HTTP OK, checks if returned Json values are correct")
    void addJsonRequestResponseCheckTest() throws Exception {
        TestDataGenerator.TestData<?, AppointmentDto> testData = TestDataGenerator.getValidAppointmentData();
        String json = testData.getJson();
        AppointmentDto expectedDto = testData.getDto();
        when(service.add(any(AppointmentDto.class))).thenReturn(expectedDto);

        mockMvc.perform(post("/appointment/add")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedDto.getId()))
                .andExpect(jsonPath("$.doctorId").value(expectedDto.getDoctorId()))
                .andExpect(jsonPath("$.patientId").value(expectedDto.getPatientId()))
                .andExpect(jsonPath("$.time[0]").value(expectedDto.getTime().getYear()))
                .andExpect(jsonPath("$.time[1]").value(expectedDto.getTime().getMonthValue()))
                .andExpect(jsonPath("$.time[2]").value(expectedDto.getTime().getDayOfMonth()))
                .andExpect(jsonPath("$.time[3]").value(expectedDto.getTime().getHour()))
                .andExpect(jsonPath("$.time[4]").value(expectedDto.getTime().getMinute()));
        verify(service, times(1)).add(any(AppointmentDto.class));
    }

    @Test
    @DisplayName("Valid Json PATCH /appointment/edit. Expects HTTP OK, checks if returned Json values are correct")
    void patchJsonRequestResponseCheckTest() throws Exception {
        TestDataGenerator.TestData<?, AppointmentDto> testData = TestDataGenerator.getValidAppointmentData();
        String json = testData.getJson();
        AppointmentDto expectedDto = testData.getDto();
        long id = testData.getId();
        when(service.update(eq(id), any(AppointmentDto.class))).thenReturn(expectedDto);

        mockMvc.perform(post("/appointment/edit/" + id)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedDto.getId()))
                .andExpect(jsonPath("$.doctorId").value(expectedDto.getDoctorId()))
                .andExpect(jsonPath("$.patientId").value(expectedDto.getPatientId()))
                .andExpect(jsonPath("$.time[0]").value(expectedDto.getTime().getYear()))
                .andExpect(jsonPath("$.time[1]").value(expectedDto.getTime().getMonthValue()))
                .andExpect(jsonPath("$.time[2]").value(expectedDto.getTime().getDayOfMonth()))
                .andExpect(jsonPath("$.time[3]").value(expectedDto.getTime().getHour()))
                .andExpect(jsonPath("$.time[4]").value(expectedDto.getTime().getMinute()));
        verify(service, times(1)).update(eq(id), any(AppointmentDto.class));
    }

    @Test
    @DisplayName("Request DELETE /appointment/delete. Expects HTTP OK")
    void deleteByIdResponseCheckTest() throws Exception {
        long id = 1;
        mockMvc.perform(delete("/appointment/delete/" + id))
                .andExpect(status().isOk());
        verify(service, times(1)).delete(id);
    }

    @Test
    @DisplayName("Request GET /appointment/doctor. Expects HTTP OK and checking Json list values")
    void getListOfDoctorsResponseCheckTest() throws Exception {
        long id = 2L;
        List<AppointmentDto> expectedDtoList = TestDataGenerator.getAppointmentsDtoList();
        when(service.getAllForDoctor(id)).thenReturn(expectedDtoList);

        ResultActions actions = mockMvc.perform(get("/appointment/doctor/" + id))
                .andExpect(status().isOk());
        for (int index = 0; index < expectedDtoList.size(); index++) {
            AppointmentDto expectedDto = expectedDtoList.get(index);
            actions.andExpect(jsonPath("$[" + index + "].id").value(expectedDto.getId()))
                    .andExpect(jsonPath("$[" + index + "].doctorId").value(expectedDto.getDoctorId()))
                    .andExpect(jsonPath("$[" + index + "].patientId").value(expectedDto.getPatientId()))
                    .andExpect(jsonPath("$[" + index + "].time[0]").value(expectedDto.getTime().getYear()))
                    .andExpect(jsonPath("$[" + index + "].time[1]").value(expectedDto.getTime().getMonthValue()))
                    .andExpect(jsonPath("$[" + index + "].time[2]").value(expectedDto.getTime().getDayOfMonth()))
                    .andExpect(jsonPath("$[" + index + "].time[3]").value(expectedDto.getTime().getHour()))
                    .andExpect(jsonPath("$[" + index + "].time[4]").value(expectedDto.getTime().getMinute()));
        }
        verify(service, times(1)).getAllForDoctor(id);
    }

    @Test
    @DisplayName("Request GET /appointment/patient. Expects HTTP OK and checking Json list values")
    void getListOfPatientsResponseCheckTest() throws Exception {
        long id = 2L;
        List<AppointmentDto> expectedDtoList = TestDataGenerator.getAppointmentsDtoList();
        when(service.getAllForPatient(id)).thenReturn(expectedDtoList);

        ResultActions actions = mockMvc.perform(get("/appointment/patient/" + id))
                .andExpect(status().isOk());
        for (int index = 0; index < expectedDtoList.size(); index++) {
            AppointmentDto expectedDto = expectedDtoList.get(index);
            actions.andExpect(jsonPath("$[" + index + "].id").value(expectedDto.getId()))
                    .andExpect(jsonPath("$[" + index + "].doctorId").value(expectedDto.getDoctorId()))
                    .andExpect(jsonPath("$[" + index + "].patientId").value(expectedDto.getPatientId()))
                    .andExpect(jsonPath("$[" + index + "].time[0]").value(expectedDto.getTime().getYear()))
                    .andExpect(jsonPath("$[" + index + "].time[1]").value(expectedDto.getTime().getMonthValue()))
                    .andExpect(jsonPath("$[" + index + "].time[2]").value(expectedDto.getTime().getDayOfMonth()))
                    .andExpect(jsonPath("$[" + index + "].time[3]").value(expectedDto.getTime().getHour()))
                    .andExpect(jsonPath("$[" + index + "].time[4]").value(expectedDto.getTime().getMinute()));
        }
        verify(service, times(1)).getAllForPatient(id);
    }

    @Test
    @DisplayName("Invalid POST /appointment/add. Expects HTTP 400, and valid error message")
    void addExceptionTest() throws Exception {
        NotFoundException exception = new NotFoundException("Appointment not found");
        String emptyJson = "{}";
        when(service.add(any(AppointmentDto.class))).thenThrow(exception);
        mockMvc.perform(post("/appointment/add/")
                        .content(emptyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(exception.getMessage()));
        verify(service, times(1)).add(any(AppointmentDto.class));
    }

    @Test
    @DisplayName("Invalid POST /appointment/edit. Expects HTTP 400, and valid error message")
    void updateExceptionTest() throws Exception {
        NotFoundException exception = new NotFoundException("Appointment not found");
        String emptyJson = "{}";
        when(service.update(eq(1L), any(AppointmentDto.class))).thenThrow(exception);
        mockMvc.perform(post("/appointment/edit/1")
                        .content(emptyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(exception.getMessage()));
        verify(service, times(1)).update(eq(1L), any(AppointmentDto.class));
    }

    @Test
    @DisplayName("Invalid POST /appointment/delete. Expects HTTP 400, and valid error message")
    void deleteExceptionTest() throws Exception {
        NotFoundException exception = new NotFoundException("Appointment not found");
        String emptyJson = "{}";
        doThrow(exception).when(service).delete(1L);
        mockMvc.perform(delete("/appointment/delete/1")
                        .content(emptyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(exception.getMessage()));
        verify(service, times(1)).delete(1L);
    }

}
