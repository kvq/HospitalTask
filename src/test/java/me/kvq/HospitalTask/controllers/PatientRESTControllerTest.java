package me.kvq.HospitalTask.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import me.kvq.HospitalTask.person.doctor.Doctor;
import me.kvq.HospitalTask.person.doctor.DoctorDAO;
import me.kvq.HospitalTask.person.patient.PatientDAO;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PatientRESTControllerTest {

  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  private PatientDAO dao;
  
  @Autowired
  private DoctorDAO doctorDao;
  
  @BeforeAll
  void createDoctor() {
    doctorDao.save(new Doctor(1,"DoctorName", "DoctorLastName", "DoctorF",
        LocalDate.of(2000, 3, 3), 
        "381234567890", "Postion"));
  } 
  
  @Test
  @Order(1)
  @DisplayName("POST /patient/add")
  void testAdd() throws Exception {
    long docid = doctorDao.getList().get(0).getId();
    String patientJson = "{\"firstName\":\"First_Name\","
        + "\"lastName\":\"Second_name\","
        + "\"fathersName\":\"Fathers_Name\","
        + "\"birthDate\":[2000,1,1],"
        + "\"phoneNumber\":\"381234567890\","
        + "\"doctor\":"+docid+"}";
    mockMvc.perform(post("/patient/add")
                    .content(patientJson)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
  }
  
  @Test
  @Order(2)
  @DisplayName("PATCH /patient/edit")
  void testPatch() throws Exception {
    long id = dao.getList().get(0).getId();
    long docid = doctorDao.getList().get(0).getId();
    String patientJson = "{\"firstName\":\"First_Name\","
        + "\"lastName\":\"Second_name\","
        + "\"fathersName\":\"Fathers_Name\","
        + "\"birthDate\":[2000,1,1],"
        + "\"phoneNumber\":\"381234567890\","
        + "\"doctor\":"+docid+"}";
    
    mockMvc.perform(patch("/patient/edit/" + id)
                    .content(patientJson)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
  }
  
  @Test
  @Order(4)
  @DisplayName("DELETE /patient/delete")
  void testDelete() throws Exception {
    long id = dao.getList().get(0).getId();
    mockMvc.perform(delete("/patient/delete/" + id))
                    .andExpect(status().isOk());
  }
    
  @Test
  @Order(3)
  @DisplayName("GET /patient/list")
  void testList() throws Exception {
    mockMvc.perform(get("/patient/list"))
                    .andExpect(status().isOk());
  }
  
}