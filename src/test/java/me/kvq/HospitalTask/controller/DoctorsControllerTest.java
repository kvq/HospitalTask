package me.kvq.HospitalTask.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.service.DoctorService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
class DoctorsControllerTest {
    @Mock
    DoctorService doctorService;
    private MockMvc mockMvc;

    @BeforeEach
    void prepareController(){
        mockMvc = MockMvcBuilders.standaloneSetup(new DoctorController(doctorService)).build();
    }

    @Test
    @DisplayName("Valid Json POST /doctor/add. Expects HTTP OK, checks if returned Json values are correct")
    void addDoctorJsonRequestResponseCheckTest() throws Exception {
        String doctorJson = "{\"firstName\":\"First_Name\","
                + "\"lastName\":\"Second_name\","
                + "\"patronymic\":\"Patronymic\","
                + "\"birthDate\":[2000,1,2],"
                + "\"phoneNumber\":\"381234567890\","
                + "\"position\":\"Position\"}";

        when(doctorService.add(any(DoctorDto.class))).thenAnswer(invocation -> {
            DoctorDto dto = invocation.getArgument(0,DoctorDto.class);
            dto.setId(1);
            return dto;
        });

        mockMvc.perform(post("/doctor/add")
                        .content(doctorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("id", not(0)))
                        .andExpect(jsonPath("firstName", is("First_Name")))
                        .andExpect(jsonPath("lastName", is("Second_name")))
                        .andExpect(jsonPath("patronymic", is("Patronymic")))
                        .andExpect(jsonPath("phoneNumber", is("381234567890")))
                        .andExpect(jsonPath("birthDate[0]",is(2000)))
                        .andExpect(jsonPath("birthDate[1]",is(1)))
                        .andExpect(jsonPath("birthDate[2]",is(2)))
                        .andExpect(jsonPath("position", is("Position")));
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

        when(doctorService.update(eq(1L),any(DoctorDto.class))).thenAnswer(invocation -> {
                    long dtoid = invocation.getArgument(0,Long.class);
                    DoctorDto dto = invocation.getArgument(1,DoctorDto.class);
                    dto.setId(dtoid);
                    return dto;
        });

        mockMvc.perform(patch("/doctor/edit/" + id)
                        .content(doctorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                       .andExpect(jsonPath("id", is(1)))
                        .andExpect(jsonPath("firstName", is("First_NewName")))
                        .andExpect(jsonPath("lastName", is("Second_NewName")))
                        .andExpect(jsonPath("patronymic", is("Patronymic")))
                        .andExpect(jsonPath("phoneNumber", is("381234567891")))
                        .andExpect(jsonPath("birthDate[0]",is(2001)))
                        .andExpect(jsonPath("birthDate[1]",is(2)))
                        .andExpect(jsonPath("birthDate[2]",is(3)))
                        .andExpect(jsonPath("position", is("Position2")));
    }

    @Test
    @DisplayName("Request DELETE /doctor/delete. Expects HTTP OK")
    void deleteDoctorByIdResponseCheckTest() throws Exception {
        long id = 1;
        when(doctorService.delete(id)).thenReturn(true);
        mockMvc.perform(delete("/doctor/delete/" + id))
                        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Request GET /doctor/list. Expects HTTP OK and checking Json list values")
    void getListOfDoctorsResponseCheckTest() throws Exception {
        DoctorDto testDoctorDto = new DoctorDto(1,"DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789", "DoctorA_Position");

        when(doctorService.getList()).thenReturn(Arrays.asList(testDoctorDto));

        mockMvc.perform(get("/doctor/list"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(1)))
                        .andExpect(jsonPath("$[0].id", is(1)))
                        .andExpect(jsonPath("$[0].firstName", is("DoctorA_Name")))
                        .andExpect(jsonPath("$[0].lastName", is("DoctorA_LastName")))
                        .andExpect(jsonPath("$[0].patronymic", is("DoctorA_Patronymic")))
                        .andExpect(jsonPath("$[0].phoneNumber", is("380123455789")))
                        .andExpect(jsonPath("$[0].birthDate[0]",is(1991)))
                        .andExpect(jsonPath("$[0].birthDate[1]",is(5)))
                        .andExpect(jsonPath("$[0].birthDate[2]",is(4)))
                        .andExpect(jsonPath("$[0].position", is("DoctorA_Position")));
    }

}
