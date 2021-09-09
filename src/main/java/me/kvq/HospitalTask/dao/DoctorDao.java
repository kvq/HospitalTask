package me.kvq.HospitalTask.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import me.kvq.HospitalTask.model.Doctor;

@Repository
public interface DoctorDao extends JpaRepository<Doctor, Long>{

}
