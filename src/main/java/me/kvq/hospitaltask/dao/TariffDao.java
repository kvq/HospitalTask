package me.kvq.hospitaltask.dao;

import me.kvq.hospitaltask.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffDao extends JpaRepository<Tariff, String> {


}
