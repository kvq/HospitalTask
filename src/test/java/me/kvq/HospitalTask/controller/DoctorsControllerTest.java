package me.kvq.HospitalTask.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import me.kvq.HospitalTask.dao.DoctorDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DoctorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DoctorDao dao;

    @Test
    @Order(1)
    @DisplayName("Valid json request POST to /doctors/add (Creating new doctor), and expecting Ok status response")
    void addDoctorJsonRequestResponseCheckTest() throws Exception {

        String doctorJson = "{\"firstName\":\"First_Name\","
                + "\"lastName\":\"Second_name\","
                + "\"fathersName\":\"Fathers_Name\","
                + "\"birthDate\":[2000,1,1],"
                + "\"phoneNumber\":\"381234567890\","
                + "\"position\":\"Position\"}";

        mockMvc.perform(post("/doctor/add")
                        .content(doctorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    @DisplayName("Valid json request PATCH to /doctors/patch (Updating valid doctor by id) and expecting Ok status response")
    void patchDoctorJsonRequestResponseCheckTest() throws Exception {

        long id = dao.findAll().get(0).getId();

        String doctorJson = "{\"firstName\":\"First_NewName\","
                + "\"lastName\":\"Second_NewName\","
                + "\"fathersName\":\"Fathers_NewName\","
                + "\"birthDate\":[2001,2,2],"
                + "\"phoneNumber\":\"381234567891\","
                + "\"position\":\"Position2\"}";

        mockMvc.perform(patch("/doctor/edit/" + id)
                        .content(doctorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    @DisplayName("Valid request DELETE to /doctors/delete/id (Deleting doctor by valid id) and expecting Ok status response")
    void deleteDoctorByIdResponseCheckTest() throws Exception {
        long id = dao.findAll().get(0).getId();
        mockMvc.perform(delete("/doctor/delete/" + id))
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    @DisplayName("GET to /doctors/list (Getting list of all doctors) and expecting Ok status response")
    void getListOfDoctorsResponseCheckTest() throws Exception {
        mockMvc.perform(get("/doctor/list"))
                .andExpect(status().isOk());

    }

}