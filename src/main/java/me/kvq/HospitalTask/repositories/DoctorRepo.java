package me.kvq.HospitalTask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import me.kvq.HospitalTask.person.doctor.Doctor;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long>{

}
