package br.com.mrmaia.superpeope.storage.repositories.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISuperPowerRepository extends JpaRepository<SuperPower, Long> {

    @Query(value = "select d from SuperPower d where d.name = :name")
    List<SuperPower> findSuperPowerByName(String name);
}