package me.kvq.HospitalTask.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.kvq.HospitalTask.dao.DoctorDao;
import me.kvq.HospitalTask.dao.PatientDao;
import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.model.Patient;
import me.kvq.HospitalTask.service.PatientService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import me.kvq.HospitalTask.model.Doctor;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PatientControllerTest {

    private MockMvc mockMvc;

    PatientService patientService;

    PatientDto testPatient;
    DoctorDto testDoctor;
    List<PatientDto> list;
    HashMap<Long, PatientDto> storage;

    @BeforeEach
    void setupService(){
        storage = new HashMap<>();
        patientService = mock(PatientService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new PatientController(patientService)).build();
        testDoctor = mock(DoctorDto.class);
        when(testDoctor.getId()).thenReturn(1L);
        testPatient = new PatientDto(1,"PatientA_Name","PatientA_LastName", "PatientA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789", 1);
        storage.put(1L,testPatient);
        when(patientService.get(anyLong())).thenAnswer(invocation -> serviceGet(invocation.getArgument(0,Long.class)));
        when(patientService.add(any(PatientDto.class))).thenAnswer(invocation -> serviceAdd(invocation.getArgument(0,PatientDto.class)));
        when(patientService.delete(anyLong())).thenAnswer(invocation -> serviceDelete(invocation.getArgument(0,Long.class)));
        when(patientService.getList()).thenAnswer(invocation -> serviceGetList());
        when(patientService.update(anyLong(),any(PatientDto.class))).thenAnswer(
                invocation -> serviceUpdate(invocation.getArgument(0,Long.class),
                                            invocation.getArgument(1,PatientDto.class)));

    }

    @BeforeAll
    void preCreateDoctor() {


    }

    PatientDto serviceAdd(PatientDto dto){
        storage.put(dto.getId(),dto);
        return dto;
    }

    PatientDto serviceGet(long id){
        return storage.get(id);
    }

    List<PatientDto> serviceGetList(){
        return new ArrayList<>(storage.values());
    }

    boolean serviceDelete(long id){
        return storage.remove(id) != null;
    }

    PatientDto serviceUpdate(long id,PatientDto dto){
        storage.put(id,dto);
        return dto;
    }

    @Test
    @Order(1)
    @DisplayName("Valid json request POST to /patient/add (Creating new valid patient), and expecting Ok status response, checking size change in mocked service")
    void addPatientJsonRequestResponseCheckTest() throws Exception {
        long doctorId = testDoctor.getId();
        String patientJson = "{\"firstName\":\"First_Name\","
                + "\"lastName\":\"Second_name\","
                + "\"fathersName\":\"Fathers_Name\","
                + "\"birthDate\":[2000,1,1],"
                + "\"phoneNumber\":\"381234567890\","
                + "\"doctor\":" + doctorId + "}";
        mockMvc.perform(post("/patient/add")
                        .content(patientJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(2, serviceGetList().size());
    }

    @Test
    @Order(2)
    @DisplayName("Valid json request PATCH to /patient/edit/id (Updating patient by valid id) and expecting Ok status response, checking change in mocked service")
    void patchPatientJsonRequestResponseCheckTest() throws Exception {
        long id = testPatient.getId();
        long doctorId = testDoctor.getId();
        String patientJson = "{\"firstName\":\"Different_Name\","
                + "\"lastName\":\"Second_name\","
                + "\"fathersName\":\"Fathers_Name\","
                + "\"birthDate\":[2000,1,1],"
                + "\"phoneNumber\":\"381234567890\","
                + "\"doctor\":" + doctorId + "}";

        mockMvc.perform(patch("/patient/edit/" + id)
                        .content(patientJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals("Different_Name",serviceGet(testPatient.getId()).getFirstName());
    }

    @Test
    @Order(4)
    @DisplayName("Valid requset DELETE /patient/delete/id/ (Deleting patient by valid id) and expecting Ok status response, checking if patient disappeared from mocked service")
    void deletePatientByIdResponseCheckTest() throws Exception {
        long id = testPatient.getId();
        mockMvc.perform(delete("/patient/delete/" + id))
                .andExpect(status().isOk());

        assertTrue(serviceGet(id) == null);
    }

    @Test
    @Order(3)
    @DisplayName("GET to /patient/list (Getting list of all patients) and expecting Ok status response")
    void getListOfPatientsResponseCheckTest() throws Exception {
        mockMvc.perform(get("/patient/list"))
                .andExpect(status().isOk());
    }

}