package me.kvq.hospitaltask.security;

import lombok.RequiredArgsConstructor;
import me.kvq.hospitaltask.dao.AppointmentDao;
import me.kvq.hospitaltask.exception.NotFoundException;
import me.kvq.hospitaltask.model.Appointment;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("appointmentSecurityService")
public class AppointmentSecurityService {
    private final AppointmentDao appointmentDao;
    private final SecurityUserService securityUserService;

    public boolean ownsAppointment(User principal, long appointmentId) {
        Appointment appointment = appointmentDao.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("No appointment found by that id"));
        SecurityUser securityUser = securityUserService.getSecurityUser(principal);
        long appointmentPatientId = appointment.getPatient().getId();
        return appointmentPatientId == securityUser.getId();
    }

    public boolean canCreateAppointment(User principal, long patientId) {
        SecurityUser securityUser = securityUserService.getSecurityUser(principal);
        return securityUser.getId() == patientId;
    }

}
