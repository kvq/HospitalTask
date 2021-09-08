package me.kvq.HospitalTask.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.service.DoctorService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DoctorsControllerTest {

    private MockMvc mockMvc;

    DoctorService doctorService;

    DoctorDto testDoctor;

    List<DoctorDto> list;
    HashMap<Long, DoctorDto> storage;

    @BeforeAll
    void mockOverridesPrepare () {
        doctorService = mock(DoctorService.class);
        when(doctorService.get(anyLong())).thenAnswer(invocation -> serviceGet(invocation.getArgument(0,Long.class)));
        when(doctorService.add(any(DoctorDto.class))).thenAnswer(invocation -> serviceAdd(invocation.getArgument(0,DoctorDto.class)));
        when(doctorService.delete(anyLong())).thenAnswer(invocation -> serviceDelete(invocation.getArgument(0,Long.class)));
        when(doctorService.getList()).thenAnswer(invocation -> serviceGetList());
        when(doctorService.update(anyLong(),any(DoctorDto.class))).thenAnswer(
                invocation -> serviceUpdate(invocation.getArgument(0,Long.class),
                        invocation.getArgument(1,DoctorDto.class)));

        mockMvc = MockMvcBuilders.standaloneSetup(new DoctorController(doctorService)).build();
    }

    @BeforeEach
    void setupService(){
        storage = new HashMap<>();

        testDoctor = new DoctorDto(1,"DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789", "DoctorA_Position");
        storage.put(1L, testDoctor);


    }

    DoctorDto serviceAdd(DoctorDto dto){
        storage.put(dto.getId(),dto);
        return dto;
    }

    DoctorDto serviceGet(long id){
        return storage.get(id);
    }

    List<DoctorDto> serviceGetList(){
        return new ArrayList<>(storage.values());
    }

    boolean serviceDelete(long id){
        return storage.remove(id) != null;
    }

    DoctorDto serviceUpdate(long id,DoctorDto dto){
        storage.put(id,dto);
        return dto;
    }

    @Test
    @Order(1)
    @DisplayName("Valid Json DoctorDto POST /doctor/add. Expects HTTP OK, checks service list size")
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

        assertEquals(2, serviceGetList().size());
    }

    @Test
    @Order(2)
    @DisplayName("Valid Json DoctorDto PATCH /doctor/edit. Expects HTTP OK, checks service data change")
    void patchDoctorJsonRequestResponseCheckTest() throws Exception {

        long id = testDoctor.getId();

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

       assertEquals("Position2",serviceGet(testDoctor.getId()).getPosition());
    }

    @Test
    @Order(4)
    @DisplayName("Request DELETE /doctor/delete. Expects HTTP OK, checks if user still exists")
    void deleteDoctorByIdResponseCheckTest() throws Exception {
        long id = testDoctor.getId();
        mockMvc.perform(delete("/doctor/delete/" + id))
                .andExpect(status().isOk());

        assertTrue(serviceGet(id) == null);
    }

    @Test
    @Order(3)
    @DisplayName("Request GET /doctor/list. Expects HTTP OK")
    void getListOfDoctorsResponseCheckTest() throws Exception {
        mockMvc.perform(get("/doctor/list"))
                .andExpect(status().isOk());
    }

}