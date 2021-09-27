package me.kvq.HospitalTask.controller;

import me.kvq.HospitalTask.dto.AppointmentDto;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.service.AppointmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static me.kvq.HospitalTask.testData.TestDataGenerator.*;
import static me.kvq.HospitalTask.testData.TestMatchers.matchAppointmentDto;
import static org.mockito.ArgumentMatchers.any;
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
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    @DisplayName("Create valid appointment, compare json fields")
    void createAppointmentAndCheckResponseTest() throws Exception {
        String json = validAppointmentJson();
        AppointmentDto expectedDto = validAppointmentDto();
        when(service.add(any(AppointmentDto.class))).thenReturn(expectedDto);

        mockMvc.perform(post("/appointment/add")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(matchAppointmentDto("$", expectedDto));
        verify(service, times(1)).add(any(AppointmentDto.class));
    }

    @Test
    @DisplayName("Update valid appointment, compare json fields")
    void updateDoctorAndCheckResponseTest() throws Exception {
        String json = validAppointmentJson();
        AppointmentDto expectedDto = validAppointmentDto();
        when(service.update(any(AppointmentDto.class))).thenReturn(expectedDto);
        mockMvc.perform(post("/appointment/edit")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(matchAppointmentDto("$", expectedDto));
        verify(service, times(1)).update(any(AppointmentDto.class));
    }

    @Test
    @DisplayName("Delete appointment. Expects HTTP OK")
    void deleteAppointmentByIdTest() throws Exception {
        long id = 1;
        mockMvc.perform(delete("/appointment/delete/" + id))
                .andExpect(status().isOk());
        verify(service, times(1)).delete(id);
    }

    @Test
    @DisplayName("Get List of Appointments for Doctor, check json fields")
    void getListOfDoctorsResponseCheckTest() throws Exception {
        long id = 2L;
        List<AppointmentDto> expectedDtoList = getAppointmentsDtoList();
        when(service.findForDoctor(id)).thenReturn(expectedDtoList);

        ResultActions actions = mockMvc.perform(get("/appointment/doctor/" + id))
                .andExpect(status().isOk())
                .andExpect(matchAppointmentDto("$[0]", expectedDtoList.get(0)))
                .andExpect(matchAppointmentDto("$[1]", expectedDtoList.get(1)));
        verify(service, times(1)).findForDoctor(id);
    }

    @Test
    @DisplayName("Get List of appointments for Patient, check json fields")
    void getListOfPatientsResponseCheckTest() throws Exception {
        long id = 2L;
        List<AppointmentDto> expectedDtoList = getAppointmentsDtoList();
        when(service.findForPatient(id)).thenReturn(expectedDtoList);

        ResultActions actions = mockMvc.perform(get("/appointment/patient/" + id))
                .andExpect(status().isOk())
                .andExpect(matchAppointmentDto("$[0]", expectedDtoList.get(0)))
                .andExpect(matchAppointmentDto("$[1]", expectedDtoList.get(1)));
        verify(service, times(1)).findForPatient(id);
    }

    @Test
    @DisplayName("Invalid appointment creation, Expects Bad Request and valid error message")
    void createInvalidAppointmentExceptionTest() throws Exception {
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
    @DisplayName("Update appointment with invalid data, expects Bad Request and valid error message")
    void updateExceptionTest() throws Exception {
        NotFoundException exception = new NotFoundException("Appointment not found");
        String emptyJson = "{}";
        when(service.update(any(AppointmentDto.class))).thenThrow(exception);
        mockMvc.perform(post("/appointment/edit")
                        .content(emptyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(exception.getMessage()));
        verify(service, times(1)).update(any(AppointmentDto.class));
    }

    @Test
    @DisplayName("Delete non existing appointment, expects Bad Request and valid error message")
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
