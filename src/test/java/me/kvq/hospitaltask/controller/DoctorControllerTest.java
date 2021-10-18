package me.kvq.hospitaltask.controller;

import me.kvq.hospitaltask.dto.DoctorDto;
import me.kvq.hospitaltask.dto.OffWorkDto;
import me.kvq.hospitaltask.exception.InvalidDtoException;
import me.kvq.hospitaltask.exception.InvalidPhoneNumberException;
import me.kvq.hospitaltask.exception.NotFoundException;
import me.kvq.hospitaltask.security.SecurityUserService;
import me.kvq.hospitaltask.service.DoctorService;
import me.kvq.hospitaltask.service.OffWorkService;
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

import static me.kvq.hospitaltask.testData.TestDataGenerator.*;
import static me.kvq.hospitaltask.testData.TestMatchers.matchDoctorDto;
import static me.kvq.hospitaltask.testData.TestMatchers.matchOffWorkDto;
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
    MockMvc mockMvc;
    @MockBean(name = "securityService")
    SecurityUserService securityUserService;
    @MockBean
    OffWorkService offWorkService;

    @Test
    @DisplayName("Add new valid doctor, then compare json fields")
    @WithMockUser(authorities = "CREATE_DOCTOR")
    void addDoctorAndCheckResponseTest() throws Exception {
        String doctorJson = validDoctorJson();
        DoctorDto expectedDto = validDoctorDto();
        long id = expectedDto.getId();
        when(doctorService.add(any(DoctorDto.class))).thenReturn(expectedDto);

        mockMvc.perform(post("/doctor/add")
                        .content(doctorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(matchDoctorDto("$", expectedDto));
        verify(doctorService, times(1)).add(any(DoctorDto.class));
    }

    @Test
    @DisplayName("Add new valid doctor as unauthorized, expected forbidden status")
    @WithMockUser()
    void addDoctorAsUnauthorizedTest() throws Exception {
        String doctorJson = validDoctorJson();
        mockMvc.perform(post("/doctor/add")
                        .content(doctorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Update doctor with valid data, then compare json fields")
    @WithMockUser(authorities = "UPDATE_DOCTOR")
    void updateDoctorAndCheckResponseTest() throws Exception {
        String doctorJson = validDoctorJson();
        DoctorDto expectedDto = validDoctorDto();
        when(doctorService.update(any(DoctorDto.class))).thenReturn(expectedDto);

        mockMvc.perform(patch("/doctor/edit")
                        .content(doctorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(matchDoctorDto("$", expectedDto));
        verify(doctorService, times(1)).update(any(DoctorDto.class));
    }

    @Test
    @DisplayName("Update doctor with valid data as authorized user, expected ok status")
    @WithMockUser(authorities = {"UPDATE_SELF"})
    void updateDoctorAsAuthorizedTest() throws Exception {
        String doctorJson = validDoctorJson();
        DoctorDto dto = validDoctorDto();
        when(securityUserService.ownsAccount(any(User.class), eq(dto.getId()))).thenReturn(true);
        mockMvc.perform(patch("/doctor/edit")
                        .content(doctorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(securityUserService, times(1)).ownsAccount(any(User.class), eq(dto.getId()));
    }

    @Test
    @DisplayName("Update doctor with valid data as unauthorized, expected forbidden status")
    @WithMockUser()
    void updateDoctorAsUnauthorizedTest() throws Exception {
        String doctorJson = validDoctorJson();
        mockMvc.perform(patch("/doctor/edit")
                        .content(doctorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Delete existing doctor, expected HTTP OK")
    @WithMockUser(authorities = "DELETE_DOCTOR")
    void deleteDoctorByIdResponseCheckTest() throws Exception {
        long id = 1;
        mockMvc.perform(delete("/doctor/delete/" + id))
                .andExpect(status().isOk());
        verify(doctorService, times(1)).delete(id);
    }

    @Test
    @DisplayName("Delete existing doctor as authorized user, expected ok status")
    @WithMockUser(authorities = "DELETE_SELF")
    void deleteDoctorByIdAsAuthorizedTest() throws Exception {
        long id = 1;
        when(securityUserService.ownsAccount(any(User.class), eq(id))).thenReturn(true);
        mockMvc.perform(delete("/doctor/delete/" + id))
                .andExpect(status().isOk());
        verify(doctorService, times(1)).delete(id);
    }

    @Test
    @DisplayName("Delete existing doctor as unauthorized, expected forbidden status")
    @WithMockUser()
    void deleteDoctorByIdUnauthorizedTest() throws Exception {
        long id = 1;
        mockMvc.perform(delete("/doctor/delete/" + id))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Get list of all doctors and compare json fields")
    @WithMockUser(authorities = "SEE_ALL_DOCTORS")
    void getListOfDoctorsResponseCheckTest() throws Exception {
        List<DoctorDto> expectedDtoList = validDoctorDtoList();
        when(doctorService.getList()).thenReturn(expectedDtoList);
        mockMvc.perform(get("/doctor/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        verify(doctorService, times(1)).getList();
    }

    @Test
    @DisplayName("Get list of all doctors as unauthorized, expected forbidden status")
    @WithMockUser()
    void getListOfDoctorsAsUnauthorizedTest() throws Exception {
        List<DoctorDto> expectedDtoList = validDoctorDtoList();
        mockMvc.perform(get("/doctor/list"))
                .andExpect(status().isForbidden());
    }

    public static Stream<Arguments> getExceptions() {
        return Stream.of(
                Arguments.of(new InvalidDtoException("Dto is invalid")),
                Arguments.of(new NotFoundException("No doctor found by that id")));
    }

    @ParameterizedTest
    @DisplayName("Deletion of invalid doctor, bad request expected")
    @MethodSource("getExceptions")
    @WithMockUser(authorities = "DELETE_DOCTOR")
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
    @WithMockUser(authorities = "UPDATE_DOCTOR")
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
    @WithMockUser(authorities = "CREATE_DOCTOR")
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

    @Test
    @DisplayName("Adding invalid doctor, bad request expected")
    @WithMockUser(authorities = "SEE_DOCTOR_UNAVAILABILITY")
    void seeWhenDoctorIsUnavailableTest() throws Exception {
        long doctorId = 1;
        List<OffWorkDto> offWorkDtoList = validOffWorkDtoList();
        when(offWorkService.getAllActiveOffWorks(doctorId)).thenReturn(offWorkDtoList);
        mockMvc.perform(get("/doctor/unavailability/" + doctorId))
                .andExpect(status().isOk())
                .andExpect(matchOffWorkDto("$[0]", offWorkDtoList.get(0)))
                .andExpect(matchOffWorkDto("$[1]", offWorkDtoList.get(1)));
    }

    @Test
    @DisplayName("Update/Create OffWork, checks returned fields")
    @WithMockUser(authorities = "UPDATE_OFFWORK")
    void addDoctorOffWorkTest() throws Exception {
        OffWorkDto offWorkDto = validOffWorkDtoCurrent();
        String json = validOffWorkJson();
        when(offWorkService.updateOffWork(any(OffWorkDto.class))).thenReturn(offWorkDto);
        mockMvc.perform(post("/doctor/updateOffWork"))
                .andExpect(status().isOk())
                .andExpect(matchOffWorkDto("$", offWorkDto));
    }

}
