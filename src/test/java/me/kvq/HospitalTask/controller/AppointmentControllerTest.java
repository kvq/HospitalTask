package me.kvq.HospitalTask.controller;

import me.kvq.HospitalTask.dto.AppointmentDto;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.security.AppointmentSecurityService;
import me.kvq.HospitalTask.security.SecurityUserService;
import me.kvq.HospitalTask.service.AppointmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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
    @MockBean(name = "securityService")
    SecurityUserService securityUserService;
    @MockBean(name = "appointmentSecurityService")
    AppointmentSecurityService appointmentSecurity;
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Create valid appointment, compare json fields")
    @WithMockUser(authorities = {"CREATE_APPOINTMENT"})
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
    @DisplayName("Create valid appointment as authorized user, expected ok status")
    @WithMockUser(authorities = {"CREATE_OWN_APPOINTMENT"})
    void makeNewAppointmentForAuthorizedUserTest() throws Exception {
        String json = validAppointmentJson();
        AppointmentDto dto = validAppointmentDto();
        when(service.add(any(AppointmentDto.class))).thenReturn(dto);
        when(appointmentSecurity.canCreateAppointment(any(User.class), eq(dto.getPatient().getId()))).thenReturn(true);
        mockMvc.perform(post("/appointment/add")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service, times(1)).add(any(AppointmentDto.class));
        verify(appointmentSecurity, times(1))
                .canCreateAppointment(any(User.class), eq(dto.getPatient().getId()));
    }

    @Test
    @DisplayName("Create valid appointment as unauthorized, expected forbidden status")
    @WithMockUser()
    void makeNewAppointmentAsUnauthorizedTest() throws Exception {
        String json = validAppointmentJson();
        mockMvc.perform(post("/appointment/add")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Update valid appointment, compare json fields")
    @WithMockUser(authorities = {"UPDATE_APPOINTMENT"})
    void updateAppointmentAndCheckResponseTest() throws Exception {
        String json = validAppointmentJson();
        AppointmentDto expectedDto = validAppointmentDto();
        when(service.update(any(AppointmentDto.class))).thenReturn(expectedDto);
        mockMvc.perform(patch("/appointment/edit")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(matchAppointmentDto("$", expectedDto));
        verify(service, times(1)).update(any(AppointmentDto.class));
    }

    @Test
    @DisplayName("Update valid appointment as authorized user, expected ok status")
    @WithMockUser(authorities = {"UPDATE_OWN_APPOINTMENT"})
    void updateAppointmentAsAuthorizedUserTest() throws Exception {
        String json = validAppointmentJson();
        AppointmentDto dto = validAppointmentDto();
        when(service.update(any(AppointmentDto.class))).thenReturn(dto);
        when(appointmentSecurity.ownsAppointment(any(User.class), eq(dto.getId()))).thenReturn(true);
        mockMvc.perform(patch("/appointment/edit")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service, times(1)).update(any(AppointmentDto.class));
        verify(appointmentSecurity, times(1)).ownsAppointment(any(User.class), eq(dto.getId()));
    }

    @Test
    @DisplayName("Update valid appointment as unauthorized, expected forbidden status")
    @WithMockUser()
    void updateAppointmentAsUnauthorizedTest() throws Exception {
        String json = validAppointmentJson();
        mockMvc.perform(patch("/appointment/edit")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Delete appointment. Expects HTTP OK")
    @WithMockUser(authorities = {"DELETE_APPOINTMENT"})
    void deleteAppointmentByIdTest() throws Exception {
        long id = 1;
        mockMvc.perform(delete("/appointment/delete/" + id))
                .andExpect(status().isOk());
        verify(service, times(1)).delete(id);
    }

    @Test
    @DisplayName("Delete appointment as authorized user, expected ok status")
    @WithMockUser(authorities = {"DELETE_OWN_APPOINTMENT"})
    void deleteAppointmentAsAuthorizedUserTest() throws Exception {
        long id = 1;
        when(appointmentSecurity.ownsAppointment(any(User.class), eq(id))).thenReturn(true);
        mockMvc.perform(delete("/appointment/delete/" + id))
                .andExpect(status().isOk());
        verify(service, times(1)).delete(id);
        verify(appointmentSecurity, times(1)).ownsAppointment(any(User.class), eq(id));
    }

    @Test
    @DisplayName("Delete appointment as unauthorized, expected forbidden status")
    @WithMockUser()
    void deleteAppointmentAsUnauthorizedTest() throws Exception {
        long id = 1;
        mockMvc.perform(delete("/appointment/delete/" + id))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Get List of Appointments for Doctor, check json fields")
    @WithMockUser(authorities = {"SEE_ALL_APPOINTMENTS"})
    void getAllAppointmentForDoctor() throws Exception {
        long id = 2L;
        List<AppointmentDto> expectedDtoList = getAppointmentsDtoList();
        when(service.findByDoctor(id)).thenReturn(expectedDtoList);
        mockMvc.perform(get("/appointment/doctor/" + id))
                .andExpect(status().isOk())
                .andExpect(matchAppointmentDto("$[0]", expectedDtoList.get(0)))
                .andExpect(matchAppointmentDto("$[1]", expectedDtoList.get(1)));
        verify(service, times(1)).findByDoctor(id);
    }

    @Test
    @DisplayName("Get List of Appointments for Doctor as authorized user, expected ok status")
    @WithMockUser(authorities = {"SEE_OWN_APPOINTMENTS"})
    void getAllAppointmentForDoctorAsAuthorized() throws Exception {
        long id = 2L;
        List<AppointmentDto> expectedDtoList = getAppointmentsDtoList();
        when(service.findByDoctor(id)).thenReturn(expectedDtoList);
        when(securityUserService.ownsAccount(any(User.class), eq(id))).thenReturn(true);
        mockMvc.perform(get("/appointment/doctor/" + id))
                .andExpect(status().isOk());
        verify(service, times(1)).findByDoctor(id);
        verify(securityUserService, times(1)).ownsAccount(any(User.class), eq(id));
    }

    @Test
    @DisplayName("Get List of Appointments for Doctor as unauthorized, expected forbidden status")
    @WithMockUser()
    void getAllAppointmentForDoctorAsUnauthorized() throws Exception {
        long id = 2L;
        List<AppointmentDto> expectedDtoList = getAppointmentsDtoList();
        when(service.findByDoctor(id)).thenReturn(expectedDtoList);
        mockMvc.perform(get("/appointment/doctor/" + id))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Get List of appointments for Patient, check json fields")
    @WithMockUser(authorities = {"SEE_ALL_APPOINTMENTS"})
    void getAllAppointmentForPatient() throws Exception {
        long id = 2L;
        List<AppointmentDto> expectedDtoList = getAppointmentsDtoList();
        when(service.findByPatient(id)).thenReturn(expectedDtoList);
        mockMvc.perform(get("/appointment/patient/" + id))
                .andExpect(status().isOk())
                .andExpect(matchAppointmentDto("$[0]", expectedDtoList.get(0)))
                .andExpect(matchAppointmentDto("$[1]", expectedDtoList.get(1)));
        verify(service, times(1)).findByPatient(id);
    }

    @Test
    @DisplayName("Get List of appointments for Patient as authorized, expected ok status")
    @WithMockUser(authorities = {"SEE_OWN_APPOINTMENTS"})
    void getAllAppointmentForPatientAsAuthorized() throws Exception {
        long id = 2L;
        List<AppointmentDto> expectedDtoList = getAppointmentsDtoList();
        when(service.findByPatient(id)).thenReturn(expectedDtoList);
        when(securityUserService.ownsAccount(any(User.class), eq(id))).thenReturn(true);
        mockMvc.perform(get("/appointment/patient/" + id))
                .andExpect(status().isOk());
        verify(service, times(1)).findByPatient(id);
        verify(securityUserService, times(1)).ownsAccount(any(User.class), eq(id));
    }

    @Test
    @DisplayName("Get List of appointments for Patient as unauthorized, expected forbidden")
    @WithMockUser()
    void getAllAppointmentForPatientAsUnauthorized() throws Exception {
        long id = 2L;
        List<AppointmentDto> expectedDtoList = getAppointmentsDtoList();
        when(service.findByPatient(id)).thenReturn(expectedDtoList);
        mockMvc.perform(get("/appointment/patient/" + id))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Invalid appointment creation, Expects Bad Request and valid error message")
    @WithMockUser(authorities = {"CREATE_APPOINTMENT"})
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
    @WithMockUser(authorities = {"UPDATE_APPOINTMENT"})
    void updateExceptionTest() throws Exception {
        NotFoundException exception = new NotFoundException("Appointment not found");
        String emptyJson = "{}";
        when(service.update(any(AppointmentDto.class))).thenThrow(exception);
        mockMvc.perform(patch("/appointment/edit")
                        .content(emptyJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(exception.getMessage()));
        verify(service, times(1)).update(any(AppointmentDto.class));
    }

    @Test
    @DisplayName("Delete non existing appointment, expects Bad Request and valid error message")
    @WithMockUser(authorities = {"DELETE_APPOINTMENT"})
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
