package me.kvq.hospitaltask.dao;

import me.kvq.hospitaltask.security.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityUserDao extends JpaRepository<SecurityUser, String> {

    Optional<SecurityUser> findByUsername(String username);

}
