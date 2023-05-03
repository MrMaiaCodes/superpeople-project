package br.com.mrmaia.superpeope.storage.repositories;

import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISuperPeopleRepository extends JpaRepository<SuperPeople, Long> {

    @Query(value = "select d from SuperPeople d where d.name = name")
    List<SuperPeople> findSuperPeopleByName(String name);
}
