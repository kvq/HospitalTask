package me.kvq.HospitalTask.dao;

import me.kvq.HospitalTask.security.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityUserDao extends JpaRepository<SecurityUser, String> {

    Optional<SecurityUser> findByUsername(String username);

}
