package me.kvq.HospitalTask.mapper;

import me.kvq.HospitalTask.model.Doctor;
import me.kvq.HospitalTask.model.Patient;
import me.kvq.HospitalTask.dto.PatientDto;
import me.kvq.HospitalTask.dao.DoctorDao;
import org.springframework.stereotype.Service;

@Service
public class PatientMapper extends PersonMapper<Patient, PatientDto> {

    private DoctorDao dao;

    public PatientMapper(DoctorDao dao){
        this.dao = dao;
    }

    @Override
    public PatientDto entityToDto(Patient p) {
        long doctorId = p.getDoctor() == null ? 0 : p.getDoctor().getId();
        return new PatientDto(p.getId(), p.getFirstName(),
                p.getLastName(),
                p.getPatronymic(),
                p.getBirthDate(),
                p.getPhoneNumber(),
                p.getDoctor().getId());
    }

    @Override
    public Patient dtoToEntity(PatientDto d) {
        Doctor doctor = dao.getById(d.getDoctor());
        return new Patient(d.getId(),
                d.getFirstName(),
                d.getLastName(),
                d.getPatronymic(),
                d.getBirthDate(),
                d.getPhoneNumber(),
                doctor);
    }

}
