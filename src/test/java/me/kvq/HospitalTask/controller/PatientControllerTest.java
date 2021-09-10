package me.kvq.HospitalTask.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.service.PatientService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {
    private MockMvc mockMvc;
    @Mock
    PatientService patientService;

    @BeforeEach
    void setupService(){
        mockMvc = MockMvcBuilders.standaloneSetup(new PatientController(patientService)).build();
    }

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

        when(patientService.add(any(PatientDto.class))).thenAnswer(invocation -> {
                    PatientDto dto = invocation.getArgument(0,PatientDto.class);
                    dto.setId(1);
                    return dto;
        });

        mockMvc.perform(post("/patient/add")
                        .content(patientJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", not(0)))
                .andExpect(jsonPath("firstName", is("First_Name")))
                .andExpect(jsonPath("lastName", is("Second_name")))
                .andExpect(jsonPath("patronymic", is("Patronymic")))
                .andExpect(jsonPath("phoneNumber", is("381234567890")))
                .andExpect(jsonPath("birthDate[0]",is(2001)))
                .andExpect(jsonPath("birthDate[1]",is(2)))
                .andExpect(jsonPath("birthDate[2]",is(3)))
                .andExpect(jsonPath("doctor", is(3)));;
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

        when(patientService.update(eq(1L),any(PatientDto.class))).thenAnswer(invocation -> {
                    long dtoid = invocation.getArgument(0,Long.class);
                    PatientDto dto = invocation.getArgument(1,PatientDto.class);
                    dto.setId(dtoid);
                    return dto;
        });

        mockMvc.perform(patch("/patient/edit/" + testPatientId)
                        .content(patientJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("firstName", is("Different_Name")))
                .andExpect(jsonPath("lastName", is("Second_name")))
                .andExpect(jsonPath("patronymic", is("Patronymic")))
                .andExpect(jsonPath("phoneNumber", is("381234567890")))
                .andExpect(jsonPath("birthDate[0]",is(2000)))
                .andExpect(jsonPath("birthDate[1]",is(1)))
                .andExpect(jsonPath("birthDate[2]",is(2)))
                .andExpect(jsonPath("doctor", is(3)));;
    }

    @Test
    @DisplayName("Request DELETE /patient/delete. Expects HTTP OK")
    void deletePatientByIdResponseCheckTest() throws Exception {
        long testPatientId = 1;
        when(patientService.delete(1L)).thenReturn(true);
        mockMvc.perform(delete("/patient/delete/" + testPatientId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Request GET /patient/list. Expects HTTP OK and checking Json list values")
    void getListOfPatientsResponseCheckTest() throws Exception {
        PatientDto testPatientDto = new PatientDto(1,"PatientA_Name","PatientA_LastName", "PatientA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789", 3);

        when(patientService.getList()).thenReturn(Arrays.asList(testPatientDto));

        mockMvc.perform(get("/patient/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("PatientA_Name")))
                .andExpect(jsonPath("$[0].lastName", is("PatientA_LastName")))
                .andExpect(jsonPath("$[0].patronymic", is("PatientA_Patronymic")))
                .andExpect(jsonPath("$[0].phoneNumber", is("380123455789")))
                .andExpect(jsonPath("$[0].birthDate[0]",is(1991)))
                .andExpect(jsonPath("$[0].birthDate[1]",is(5)))
                .andExpect(jsonPath("$[0].birthDate[2]",is(4)))
                .andExpect(jsonPath("$[0].doctor", is(3)));;
    }

}
