package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.dto.DoctorDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

class DoctorServiceTest {

    @Autowired
    public DoctorService doctor;

    @Test
    @Order(1)
    @DisplayName("Adding user via service")
    void testAdd() {
        DoctorDto dto = new DoctorDto
                        (0, "TestName", "TestLastName", "TestFName",
                        LocalDate.of(2005, 1, 1),
                        "380123456789", "None");

    }

    @Test
    @Order(2)
    @DisplayName("Finding all users via service")
    void testGetList() {

    }

    @Test
    @Order(3)
    @DisplayName("Getting specific user")
    void testGet() {

    }

}