package me.kvq.hospitaltask.dao;

import me.kvq.hospitaltask.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorDao extends JpaRepository<Doctor, Long> {

}
