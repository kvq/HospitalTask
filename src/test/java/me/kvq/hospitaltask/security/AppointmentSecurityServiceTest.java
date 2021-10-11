package me.kvq.hospitaltask.security;

import me.kvq.hospitaltask.dao.AppointmentDao;
import me.kvq.hospitaltask.model.Appointment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

import static me.kvq.hospitaltask.testData.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class AppointmentSecurityServiceTest {
    @MockBean
    AppointmentDao appointmentDao;
    @MockBean
    SecurityUserService securityUserService;
    @Autowired
    AppointmentSecurityService service;

    @Test
    @DisplayName("User owns appointment. Expects true from service")
    void doesOwnAppointmentTest() {
        User user = validUserDetails();
        SecurityUser securityUser = validSecurityUser();
        Appointment appointment = validAppointment();
        when(appointmentDao.findById(appointment.getId())).thenReturn(Optional.of(appointment));
        when(securityUserService.getSecurityUser(user)).thenReturn(securityUser);
        assertTrue(service.ownsAppointment(user, appointment.getId()));
    }

    @Test
    @DisplayName("User does not own appointment. Expects false from service")
    void doesNotOwnAppointmentTest() {
        User user = validUserDetails();
        SecurityUser securityUser = validSecurityUser();
        securityUser.setId(1L);
        Appointment appointment = validAppointment();
        when(appointmentDao.findById(appointment.getId())).thenReturn(Optional.of(appointment));
        when(securityUserService.getSecurityUser(user)).thenReturn(securityUser);
        assertFalse(service.ownsAppointment(user, appointment.getId()));
    }

    @Test
    @DisplayName("User can create appointment for himself. Expects true from service")
    void canCreateAppointmentTest() {
        User user = validUserDetails();
        SecurityUser securityUser = validSecurityUser();
        when(securityUserService.getSecurityUser(user)).thenReturn(securityUser);
        assertTrue(service.canCreateAppointment(user, securityUser.getId()));
    }

    @Test
    @DisplayName("User tries to create appointment for someone else. Expects false from service")
    void canNotCreateAppointmentTest() {
        User user = validUserDetails();
        SecurityUser securityUser = validSecurityUser();
        when(securityUserService.getSecurityUser(user)).thenReturn(securityUser);
        assertFalse(service.canCreateAppointment(user, 0L));
    }

}
