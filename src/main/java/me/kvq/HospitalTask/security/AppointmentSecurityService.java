package me.kvq.HospitalTask.security;

import lombok.RequiredArgsConstructor;
import me.kvq.HospitalTask.dao.AppointmentDao;
import me.kvq.HospitalTask.exception.NotFoundException;
import me.kvq.HospitalTask.model.Appointment;
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
        if (appointmentPatientId == securityUser.getId()) {
            return true;
        }
        return false;
    }

    public boolean canCreateAppointment(User principal, long patientId) {
        SecurityUser securityUser = securityUserService.getSecurityUser(principal);
        if (securityUser.getId() == patientId) {
            return true;
        }
        return false;
    }

}
