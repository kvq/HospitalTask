package me.kvq.HospitalTask.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import me.kvq.HospitalTask.dto.DoctorDto;
import me.kvq.HospitalTask.service.DoctorService;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DoctorsControllerTest {

    private MockMvc mockMvc;
    DoctorService doctorService;

    List<DoctorDto> list;
    HashMap<Long, DoctorDto> storage;

    @BeforeAll
    void mockOverridesPrepare () {
        doctorService = mock(DoctorService.class);
        when(doctorService.get(anyLong())).thenAnswer(invocation -> serviceDoctorGet(invocation.getArgument(0,Long.class)));
        when(doctorService.add(any(DoctorDto.class))).thenAnswer(invocation -> serviceDoctorAdd(invocation.getArgument(0,DoctorDto.class)));
        when(doctorService.delete(anyLong())).thenAnswer(invocation -> serviceDoctorDelete(invocation.getArgument(0,Long.class)));
        when(doctorService.getList()).thenAnswer(invocation -> serviceDoctorGetList());
        when(doctorService.update(anyLong(),any(DoctorDto.class))).thenAnswer(
                invocation -> serviceDoctorUpdate(invocation.getArgument(0,Long.class),
                        invocation.getArgument(1,DoctorDto.class)));

        mockMvc = MockMvcBuilders.standaloneSetup(new DoctorController(doctorService)).build();
    }

    @BeforeEach
    void setupService(){
        storage = new HashMap<>();
    }

    DoctorDto serviceDoctorAdd(DoctorDto dto){
        storage.put(dto.getId(),dto);
        return dto;
    }

    boolean serviceDoctorExistsById(long l){
        return storage.containsKey(l);
    }

    DoctorDto serviceDoctorGet(long id){
        return storage.get(id);
    }

    List<DoctorDto> serviceDoctorGetList(){
        return new ArrayList<>(storage.values());
    }

    boolean serviceDoctorDelete(long id){
        boolean exists = storage.remove(id) != null;
        if (exists) return true;

        throw new NoSuchElementException("User does not exists");
    }

    DoctorDto serviceDoctorUpdate(long id, DoctorDto dto){
        storage.put(id,dto);
        return dto;
    }

    @Test
    @DisplayName("Valid Json DoctorDto POST /doctor/add. Expects HTTP OK, checks service list size")
    void addDoctorJsonRequestResponseCheckTest() throws Exception {

        String doctorJson = "{\"firstName\":\"First_Name\","
                + "\"lastName\":\"Second_name\","
                + "\"fathersName\":\"Fathers_Name\","
                + "\"birthDate\":[2000,1,1],"
                + "\"phoneNumber\":\"381234567890\","
                + "\"position\":\"Position\"}";


        assertEquals(0, serviceDoctorGetList().size(), "Service supposed to be empty when test starts");
        mockMvc.perform(post("/doctor/add")
                        .content(doctorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(1, serviceDoctorGetList().size(), "Doctor wasn't add to service");
    }

    @Test
    @DisplayName("Valid Json DoctorDto PATCH /doctor/edit. Expects HTTP OK, checks service data change")
    void patchDoctorJsonRequestResponseCheckTest() throws Exception {

        DoctorDto testDoctorDto = new DoctorDto(1,"DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789", "DoctorA_Position");
        serviceDoctorAdd(testDoctorDto);
        long id = testDoctorDto.getId();

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

       assertEquals("Position2", serviceDoctorGet(testDoctorDto.getId()).getPosition());
    }

    @Test
    @DisplayName("Request DELETE /doctor/delete. Expects HTTP OK, checks if user still exists")
    void deleteDoctorByIdResponseCheckTest() throws Exception {
        DoctorDto testDoctorDto = new DoctorDto(1,"DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789", "DoctorA_Position");
        serviceDoctorAdd(testDoctorDto);
        assertTrue(serviceDoctorExistsById(testDoctorDto.getId()), "Test doctor was not created");

        long id = testDoctorDto.getId();
        mockMvc.perform(delete("/doctor/delete/" + id))
                .andExpect(status().isOk());

        assertTrue(!serviceDoctorExistsById(testDoctorDto.getId()), "Doctor was not deleted from service");
    }

    @Test
    @DisplayName("Request GET /doctor/list. Expects HTTP OK")
    void getListOfDoctorsResponseCheckTest() throws Exception {
        DoctorDto testDoctorDto = new DoctorDto(1,"DoctorA_Name","DoctorA_LastName", "DoctorA_Patronymic",
                LocalDate.of(1991,5,4),
                "380123455789", "DoctorA_Position");
        serviceDoctorAdd(testDoctorDto);

        mockMvc.perform(get("/doctor/list"))
                .andExpect(status().isOk());
    }

}