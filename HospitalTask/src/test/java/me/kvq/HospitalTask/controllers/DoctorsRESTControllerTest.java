package me.kvq.HospitalTask.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import me.kvq.HospitalTask.person.doctor.DoctorDAO;

@SpringBootTest
@AutoConfigureMockMvc

class DoctorsRESTControllerTest {

  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  private DoctorDAO dao;
  
  @Test
  @Order(1)
  @DisplayName("POST /doctors/add")
  void testAdd() throws Exception {
    
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
  @DisplayName("PATCH /doctors/add")
  void testPatch() throws Exception {
    
    long id = dao.getList().get(0).getId();
    
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
  @DisplayName("DELETE /doctors/add")
  void testDelete() throws Exception {
    long id = dao.getList().get(0).getId();
    mockMvc.perform(delete("/doctor/delete/" + id))
                    .andExpect(status().isOk());
  }
    
  @Test
  @Order(3)
  @DisplayName("GET /doctors/list")
  void testList() throws Exception {
    mockMvc.perform(get("/doctor/list"))
                    .andExpect(status().isOk());
  }

}
