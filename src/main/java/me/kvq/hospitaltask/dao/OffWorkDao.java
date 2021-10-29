package me.kvq.hospitaltask.dao;

import me.kvq.hospitaltask.model.OffWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OffWorkDao extends JpaRepository<OffWork, Long> {

    @Query(value = "SELECT * FROM off_work WHERE ?1 between date_from and date_until and doctor_id = ?2", nativeQuery = true)
    OffWork findForSpecificDate(LocalDate date, long doctorId);

    @Query(value = "SELECT * FROM off_work WHERE date_until > ?1 and doctor_id = ?2", nativeQuery = true)
    List<OffWork> finaAllAfterDate(LocalDate date, long doctorId);
    
    @Query(value = "SELECT CASE WHEN EXISTS " +
            "(SELECT * FROM off_work WHERE ?1 between date_from and date_until and doctor_id = ?2) " +
            "THEN 'FALSE' ELSE 'TRUE' END", nativeQuery = true)
    boolean isAvailableAtDate(LocalDate date, long doctorId);

}
