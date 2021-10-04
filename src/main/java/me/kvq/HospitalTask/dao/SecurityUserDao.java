package me.kvq.HospitalTask.dao;

import me.kvq.HospitalTask.security.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityUserDao extends JpaRepository<SecurityUser, String> {

}
